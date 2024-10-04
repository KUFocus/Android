package com.example.logmeet.ui.projects

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.logmeet.data.dto.project.UserProjectDto
import com.example.logmeet.databinding.ItemPeopleEditBinding
import com.example.logmeet.domain.entity.ProjectRole

class PeopleEditAdapter(private val data: ArrayList<UserProjectDto>) : RecyclerView.Adapter<PeopleEditAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemPeopleEditBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserProjectDto) {
            binding.tvPeopleName.text = item.userName
            var isLeader = item.role == ProjectRole.LEADER.name
            binding.ivPeopleLeaderBlue.visibility = if (isLeader) View.VISIBLE else View.GONE
            binding.ivPeopleLeaderGray.visibility = if (isLeader) View.GONE else View.VISIBLE
            binding.cvPeopleLead.setOnClickListener {
                //즐겨찾기 변경 api
                val isLeadChange = binding.ivPeopleLeaderBlue.visibility == View.VISIBLE
                binding.ivPeopleLeaderBlue.visibility = if (isLeadChange) View.GONE else View.VISIBLE
                binding.ivPeopleLeaderGray.visibility = if (isLeadChange) View.VISIBLE else View.GONE
//                item.leader = isLeadChange =>  leader 변한 거 저장
                updateLeaderStatus(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPeopleEditBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateLeaderStatus(selectedPosition: Int) {
        data.forEachIndexed { index, it ->
            if (index == selectedPosition) it.role = ProjectRole.LEADER.name
            else it.role = ProjectRole.MEMBER.name
        }
        notifyDataSetChanged()
    }
}