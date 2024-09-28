package com.example.logmeet.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.logmeet.domain.entity.ScheduleData
import com.example.logmeet.databinding.ItemScheduleBinding

class HomeScheduleAdapter(private val data: ArrayList<ScheduleData>) :
    RecyclerView.Adapter<HomeScheduleAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ScheduleData) {
            //컬러 나중에 지정
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