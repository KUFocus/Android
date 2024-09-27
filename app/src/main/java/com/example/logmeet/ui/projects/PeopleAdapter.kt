package com.example.logmeet.ui.projects

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.logmeet.domain.entity.PeopleData
import com.example.logmeet.databinding.ItemPeopleBinding

class PeopleAdapter(private val data: ArrayList<PeopleData>) : RecyclerView.Adapter<PeopleAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemPeopleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PeopleData) {
            binding.tvPeopleName.text = item.name
            binding.ivPeopleLeader.visibility = if (item.leader) View.VISIBLE else View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPeopleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
}