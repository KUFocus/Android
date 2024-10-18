package com.example.logmeet.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.logmeet.R
import com.example.logmeet.data.dto.schedule.ScheduleListResult
import com.example.logmeet.domain.entity.ScheduleData
import com.example.logmeet.databinding.ItemScheduleBinding
import com.example.logmeet.domain.entity.ProjectDrawableResources
import splitDateTime

class HomeScheduleAdapter(private val data: ArrayList<ScheduleListResult>) :
    RecyclerView.Adapter<HomeScheduleAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ScheduleListResult) {
            val number = item.color.split("_")[1]
            val color = ProjectDrawableResources.colorList[number.toInt()-1]
            binding.vScheduleColor.setBackgroundResource(color)
            val time = splitDateTime(item.scheduleDate).second
            binding.tvScheduleTime.text = time
            binding.tvScheduleTitle.text = item.scheduleContent
            binding.tvScheduleName.text = item.projectName
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeScheduleAdapter.ViewHolder {
        val binding =
            ItemScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeScheduleAdapter.ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

}