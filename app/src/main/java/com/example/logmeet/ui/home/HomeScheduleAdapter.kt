package com.example.logmeet.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.logmeet.R
import com.example.logmeet.domain.entity.ScheduleData
import com.example.logmeet.databinding.ItemScheduleBinding

class HomeScheduleAdapter(private val data: ArrayList<ScheduleData>) :
    RecyclerView.Adapter<HomeScheduleAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ScheduleData) {
            val colorList = arrayOf(
                R.drawable.color_prj1,
                R.drawable.color_prj2,
                R.drawable.color_prj3,
                R.drawable.color_prj4,
                R.drawable.color_prj5,
                R.drawable.color_prj6,
                R.drawable.color_prj7,
                R.drawable.color_prj8,
                R.drawable.color_prj9,
                R.drawable.color_prj10,
                R.drawable.color_prj11,
                R.drawable.color_prj12,
            )
            val color = colorList[item.prjColor.toInt()-1]
            binding.vScheduleColor.setBackgroundResource(color)
            binding.tvScheduleTime.text = item.time
            binding.tvScheduleTitle.text = item.title
            binding.tvScheduleName.text = item.prjName
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