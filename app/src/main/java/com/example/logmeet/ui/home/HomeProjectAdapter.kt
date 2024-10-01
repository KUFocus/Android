package com.example.logmeet.ui.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.logmeet.R
import com.example.logmeet.data.dto.project.ProjectListResult
import com.example.logmeet.domain.entity.ProjectData
import com.example.logmeet.databinding.ItemProjectHomeBinding
import com.example.logmeet.domain.entity.ProjectDrawableResources
import com.example.logmeet.ui.projects.ProjectHomeActivity

class HomeProjectAdapter(private val data: ArrayList<ProjectListResult>) :
    RecyclerView.Adapter<HomeProjectAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemProjectHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProjectListResult) {
            val number = item.projectColor.split("_")[1].toInt()
            val color = ProjectDrawableResources.colorList[number-1]
            binding.vPrjCircle.setBackgroundResource(color)
            binding.vPrjCircle2.setBackgroundResource(color)
            binding.tvPrjName.text = item.projectName
            val dateTime = item.createdAt.substring(0, 10)
            binding.tvPrjDate.text = dateTime
            binding.tvPrjPeople.text = item.numOfMember.toString()

            binding.root.setOnClickListener {
                val context = it.context
                val intent = Intent(context, ProjectHomeActivity::class.java)
                intent.putExtra("projectId", item.projectId)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeProjectAdapter.ViewHolder {
        val binding = ItemProjectHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])

        // 첫 번째 아이템에만 마진을 적용
        val layoutParams = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
        if (position == 0) {
            layoutParams.marginStart = 22.dpToPx(holder.itemView.context) // 12dp 마진 적용
        } else {
            layoutParams.marginStart = 0 // 다른 아이템들은 마진 없음
        }
        holder.itemView.layoutParams = layoutParams
    }

    override fun getItemCount(): Int = data.size

    private fun Int.dpToPx(context: Context): Int {
        return (this * context.resources.displayMetrics.density).toInt()
    }

}