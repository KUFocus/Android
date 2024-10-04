package com.example.logmeet.ui.projects

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.logmeet.data.dto.project.UserProjectDto
import com.example.logmeet.domain.entity.PeopleData
import com.example.logmeet.databinding.ItemPeopleBinding
import com.example.logmeet.domain.entity.ProjectRole

class PeopleAdapter(private val data: ArrayList<UserProjectDto>) : RecyclerView.Adapter<PeopleAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemPeopleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserProjectDto) {
            binding.tvPeopleName.text = item.userName
            var isLeader = item.role == ProjectRole.LEADER.name
            binding.ivPeopleLeader.visibility = if (isLeader) View.VISIBLE else View.GONE
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