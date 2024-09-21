package com.example.logmeet.ui.minutes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.logmeet.entity.RecentData
import com.example.logmeet.databinding.ItemSearchRecentBinding

class RecentAdapter(private val data: ArrayList<RecentData>): RecyclerView.Adapter<RecentAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemSearchRecentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RecentData) {
            binding.tvRecentWord.text = item.word
            binding.ivRecentDelete.setOnClickListener {
                //삭제 api
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSearchRecentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
}