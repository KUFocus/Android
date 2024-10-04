package com.example.logmeet.ui.projects

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.logmeet.NETWORK
import com.example.logmeet.domain.entity.ProjectDrawableResources
import com.example.logmeet.data.dto.project.ProjectListResult
import com.example.logmeet.data.dto.project.api_response.BaseResponseProjectBookmarkResult
import com.example.logmeet.databinding.ItemProjectProjectBinding
import com.example.logmeet.network.RetrofitClient
import com.example.logmeet.ui.application.LogmeetApplication
import kotlinx.coroutines.flow.first
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjectPrjAdapter(private val data: List<ProjectListResult>) : RecyclerView.Adapter<ProjectPrjAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemProjectProjectBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProjectListResult) {
            val number = item.projectColor.split("_")[1].toInt()
            val color = ProjectDrawableResources.colorList[number-1]
            binding.vPrjCircle.setBackgroundResource(color)
            binding.vPrjCircle2.setBackgroundResource(color)
            binding.tvPrjName.text = item.projectName
            val dateTime = item.createdAt.substring(0, 10)
            binding.tvPrjDate.text = dateTime
            binding.tvPrjPeople.text = item.numOfMember.toString()
            binding.apply {
                ivPrjEmptyStar.visibility = if (item.bookmark) View.GONE else View.VISIBLE
                ivPrjYellowStar.visibility = if (item.bookmark) View.VISIBLE else View.GONE
            }

            setClickListener(item.projectId)
        }

        private fun setClickListener(projectId: Int) {
            binding.clBookmark.setOnClickListener {
                changeBookmark(projectId)
                val isStarEmptyVisible = binding.ivPrjEmptyStar.visibility == View.VISIBLE
                binding.ivPrjEmptyStar.visibility = if (isStarEmptyVisible) View.GONE else View.VISIBLE
                binding.ivPrjYellowStar.visibility = if (isStarEmptyVisible) View.VISIBLE else View.GONE
            }

            binding.root.setOnClickListener {
                val context = it.context
                val intent = Intent(context, ProjectHomeActivity::class.java)
                intent.putExtra("projectId", projectId)
                context.startActivity(intent)
            }
        }

        private fun changeBookmark(projectId: Int) {
            val bearerAccessToken =
                LogmeetApplication.getInstance().getDataStore().bearerAccessToken
            RetrofitClient.project_instance.changeBookmark(
                authorization = bearerAccessToken.toString(),
                projectId = projectId
            ).enqueue(object : Callback<BaseResponseProjectBookmarkResult> {
                override fun onResponse(
                    p0: Call<BaseResponseProjectBookmarkResult>,
                    p1: Response<BaseResponseProjectBookmarkResult>
                ) {
                    when (p1.code()) {
                        200 -> {
                            val resp = p1.body()?.result
                            Log.d(NETWORK, "ProjectPrjAdapter - changeBookmark() : 성공\nprojectId : $projectId => $resp")
                        }
                        else -> Log.d(NETWORK, "ProjectPrjAdapter - changeBookmark() : 실패")
                    }
                }

                override fun onFailure(p0: Call<BaseResponseProjectBookmarkResult>, p1: Throwable) {
                    Log.d(NETWORK, "ProjectPrjAdapter - changeBookmark() : 실패\nbecause $p1")
                }

            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProjectProjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
        holder.binding.ivPrjMore.setOnClickListener {
            startPage(ProjectDetailActivity::class.java, holder, position)
        }
    }

    private fun startPage(activity: Class<ProjectDetailActivity>, holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val intent = Intent(context, activity)
        intent.putExtra("projectId", data[position].projectId)
        context.startActivity(intent)
    }

}