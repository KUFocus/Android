package com.example.logmeet.ui.schedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.logmeet.data.dto.project.ProjectListResult
import com.example.logmeet.databinding.ItemProjectForSelectionBinding
import com.example.logmeet.domain.entity.ProjectDrawableResources

class ProjectSelectionAdapter(
    var data: List<ProjectListResult>,
    private val listener: OnProjectClickListener
) : RecyclerView.Adapter<ProjectSelectionAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemProjectForSelectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProjectListResult) {
            val number = item.projectColor.split("_")[1].toInt()
            val color = ProjectDrawableResources.colorList[number - 1]
            binding.vProjectSelectionColor.setBackgroundResource(color)
            binding.tvProjectSelectionName.text = item.projectName

            binding.root.setOnClickListener {
                listener.onProjectClick(item.projectId, item.projectName, item.projectColor)
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProjectForSelectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])

    }

    interface OnProjectClickListener {
        fun onProjectClick(projectId: Int, projectName: String, projectColor: String)
    }
}