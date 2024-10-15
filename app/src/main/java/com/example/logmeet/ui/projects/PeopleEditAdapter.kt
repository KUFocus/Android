package com.example.logmeet.ui.projects

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.logmeet.NETWORK
import com.example.logmeet.R
import com.example.logmeet.data.dto.project.UserProjectDto
import com.example.logmeet.data.dto.project.api_reqeust.ProjectLeaderDelegationRequest
import com.example.logmeet.data.dto.project.api_response.ProjectLeaderDelegationResponse
import com.example.logmeet.databinding.ItemPeopleEditBinding
import com.example.logmeet.domain.entity.ProjectRole
import com.example.logmeet.network.RetrofitClient
import com.example.logmeet.ui.CheckDialog
import com.example.logmeet.ui.application.LogmeetApplication
import com.example.logmeet.ui.showMinutesToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PeopleEditAdapter(
    private val data: ArrayList<UserProjectDto>,
    val projectId: Int,
    val editProjectActivity: EditProjectActivity
) :
    RecyclerView.Adapter<PeopleEditAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemPeopleEditBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserProjectDto) {
            binding.tvPeopleName.text = item.userName
            val isLeader = item.role == ProjectRole.LEADER.name
            binding.ivPeopleLeaderBlue.visibility = if (isLeader) View.VISIBLE else View.GONE
            binding.ivPeopleLeaderGray.visibility = if (isLeader) View.GONE else View.VISIBLE

            binding.cvPeopleLead.setOnClickListener {
                checkDialog(binding, adapterPosition)
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

    private fun checkDialog(binding: ItemPeopleEditBinding, adapterPosition: Int) {
        val dialog = CheckDialog(
            binding.root.context,
            "${data[adapterPosition].userName}님을\n리더로 설정합니다."
        )

        dialog.setItemClickListener(object : CheckDialog.ItemClickListener {
            override suspend fun onClick() {
//                projectLeaderDelegation(data[adapterPosition].id, data[adapterPosition].userId)
//
//                val isLeadChange = binding.ivPeopleLeaderBlue.visibility == View.VISIBLE
//                binding.ivPeopleLeaderBlue.visibility = if (isLeadChange) View.GONE else View.VISIBLE
//                binding.ivPeopleLeaderGray.visibility = if (isLeadChange) View.VISIBLE else View.GONE
//                updateLeaderStatus(adapterPosition)
                CoroutineScope(Dispatchers.IO).launch {
                    val result = projectLeaderDelegation(
                        projectId,
                        data[adapterPosition].userId
                    )

                    CoroutineScope(Dispatchers.Main).launch {
                        val isLeadChange = binding.ivPeopleLeaderBlue.visibility == View.VISIBLE
                        binding.ivPeopleLeaderBlue.visibility =
                            if (isLeadChange) View.GONE else View.VISIBLE
                        binding.ivPeopleLeaderGray.visibility =
                            if (isLeadChange) View.VISIBLE else View.GONE

                        updateLeaderStatus(adapterPosition)
                        dialog.dismiss()
                    }
                }
            }
        })

        dialog.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateLeaderStatus(selectedPosition: Int) {
        data.forEachIndexed { index, it ->
            if (index == selectedPosition) it.role = ProjectRole.LEADER.name
            else it.role = ProjectRole.MEMBER.name
        }
        notifyDataSetChanged()
    }

    private suspend fun projectLeaderDelegation(projectId: Int, userId: Int) {
        val bearerAccessToken =
            LogmeetApplication.getInstance().getDataStore().bearerAccessToken.first()
        RetrofitClient.project_instance.projectLeaderDelegation(
            authorization = bearerAccessToken,
            projectId = projectId,
            projectLeaderDelegationRequest = ProjectLeaderDelegationRequest(newLeaderId = userId)
        ).enqueue(object : Callback<ProjectLeaderDelegationResponse> {
            override fun onResponse(
                p0: Call<ProjectLeaderDelegationResponse>,
                p1: Response<ProjectLeaderDelegationResponse>
            ) {
                when (p1.code()) {
                    200 -> {
                        Log.d(NETWORK, "PeopleEditAdapter - projectLeaderDelegation() : 성공")
                        showMinutesToast(editProjectActivity, R.drawable.ic_check_circle, "리더가 변경되었습니다.")
                    }
                    else -> {
                        Log.d(NETWORK, "PeopleEditAdapter - projectLeaderDelegation() : 실패")
                    }
                }
            }

            override fun onFailure(p0: Call<ProjectLeaderDelegationResponse>, p1: Throwable) {
                Log.d(NETWORK, "PeopleEditAdapter - projectLeaderDelegation()실패\nbecause : $p1")
            }
        })
    }
}