package com.example.logmeet.ui.projects

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.logmeet.ProjectDrawableResources
import com.example.logmeet.domain.entity.ProjectData
import com.example.logmeet.databinding.ItemProjectProjectBinding

class ProjectPrjAdapter(private val data: ArrayList<ProjectData>) : RecyclerView.Adapter<ProjectPrjAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemProjectProjectBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProjectData) {
            val color = ProjectDrawableResources.colorList[item.prjColor.toInt()-1]
            binding.vPrjCircle.setBackgroundResource(color)
            binding.vPrjCircle2.setBackgroundResource(color)
            binding.tvPrjName.text = item.name
            binding.tvPrjDate.text = item.createdAt
            binding.tvPrjPeople.text = item.people
            binding.apply {
                ivPrjEmptyStar.visibility = if (item.bookmark) View.GONE else View.VISIBLE
                ivPrjYellowStar.visibility = if (item.bookmark) View.VISIBLE else View.GONE
            }

            binding.clBookmark.setOnClickListener {
                //즐겨찾기 변경 api
                val isStarEmptyVisible = binding.ivPrjEmptyStar.visibility == View.VISIBLE
                binding.ivPrjEmptyStar.visibility = if (isStarEmptyVisible) View.GONE else View.VISIBLE
                binding.ivPrjYellowStar.visibility = if (isStarEmptyVisible) View.VISIBLE else View.GONE
            }

            binding.root.setOnClickListener {
                val context = it.context
                val intent = Intent(context, ProjectHomeActivity::class.java)
                intent.putExtra("projectId", item.projectId)
                context.startActivity(intent)
            }
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