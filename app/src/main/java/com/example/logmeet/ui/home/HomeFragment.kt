package com.example.logmeet.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.logmeet.NETWORK
import com.example.logmeet.R
import com.example.logmeet.data.dto.minutes.BaseResponseListMinutesListResult
import com.example.logmeet.data.dto.minutes.MinutesListResult
import com.example.logmeet.data.dto.project.ProjectListResult
import com.example.logmeet.data.dto.project.api_response.BaseResponseListProjectListResult
import com.example.logmeet.data.dto.schedule.ScheduleListResult
import com.example.logmeet.data.dto.schedule.api_response.ScheduleDayResponse
import com.example.logmeet.domain.entity.MinutesData
import com.example.logmeet.databinding.FragmentHomeBinding
import com.example.logmeet.network.RetrofitClient
import com.example.logmeet.ui.application.LogmeetApplication
import com.example.logmeet.ui.component.WeeklyCalendar
import com.example.logmeet.ui.minutes.MinutesAdapter
import com.example.logmeet.ui.projects.MakeProjectActivity
import com.example.logmeet.ui.showMinutesToast
import formatDate
import formatDateForServer
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter.ofPattern

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var scheduleAdapter: HomeScheduleAdapter
    private var scheduleList: ArrayList<ScheduleListResult> = arrayListOf()
    private lateinit var projectAdapter: HomeProjectAdapter
    private var projectList: ArrayList<ProjectListResult> = arrayListOf()
    private lateinit var minutesAdapter: MinutesAdapter
    private var minutesList: ArrayList<MinutesListResult> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        init()

        binding.compHomeCalendar.setContent {
            WeeklyCalendar(
                selectedDate = {
                    binding.tvHomeDate.text = formatDate(it)
                    lifecycleScope.launch { setScheduleListData() }
                },
                onAddScheduleComplete = { resultCode ->
                    if (resultCode == Activity.RESULT_OK)
                        showMinutesToast(requireContext(), R.drawable.ic_check_circle, "일정이 추가되었습니다.")
                }
            )
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            setScheduleListData()
            setProjectListData()
            setMinutesListData()
        }
    }

    private fun init() {
        binding.tvHomeDate.text = LocalDate.now().format(ofPattern("yyyy.MM.dd")).toString()
        binding.clHomeAddProject.setOnClickListener {
            val intent = Intent(context, MakeProjectActivity::class.java)
            startActivity(intent)
        }
        binding.clHomeMoreProject.setOnClickListener {

        }
        binding.clHomeMoreMom.setOnClickListener {

        }
    }

    private fun setMinutesRV() {
        minutesAdapter = MinutesAdapter(minutesList.take(4))
        binding.rvHomeMinutesList.adapter = minutesAdapter
        binding.rvHomeMinutesList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private suspend fun setMinutesListData() {
        val bearerAccessToken = LogmeetApplication.getInstance().getDataStore().bearerAccessToken.first()

        RetrofitClient.minutes_instance.getUserMinutesList(
            authorization = bearerAccessToken
        ).enqueue(object : Callback<BaseResponseListMinutesListResult> {
            override fun onResponse(
                p0: Call<BaseResponseListMinutesListResult>,
                p1: Response<BaseResponseListMinutesListResult>
            ) {
                when (p1.code()) {
                    200 -> {
                        val resp = p1.body()?.result
                        Log.d(NETWORK, "HomeFragments - setMinutesListData() : 성공\n$resp")
                        if (resp != null) {
                            minutesList.addAll(resp.toList())
                            setMinutesRV()
                            val isMinutesEmpty = minutesList.isEmpty()
                            binding.tvHomeNoneMinutes.visibility = if (isMinutesEmpty) View.VISIBLE else View.GONE
                            binding.rvHomeMinutesList.visibility = if (isMinutesEmpty) View.GONE else View.VISIBLE
                            if (!isMinutesEmpty) setMinutesRV()
                        } else {
                            minutesList = arrayListOf()
                        }
                    }

                    else -> {
                        Log.d(NETWORK, "HomeFragments - setMinutesListData() : 실패")
                    }
                }
            }

            override fun onFailure(p0: Call<BaseResponseListMinutesListResult>, p1: Throwable) {
                Log.d(NETWORK, "HomeFragments - setMinutesListData() : 실패\nbecause $p1")
            }

        })
    }

    private fun setProjectRV() {
        projectAdapter = HomeProjectAdapter(projectList)
        binding.rvHomeProjectList.adapter = projectAdapter
        binding.rvHomeProjectList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private suspend fun setProjectListData() {
        projectList = arrayListOf()
        val bearerAccessToken =
            LogmeetApplication.getInstance().getDataStore().bearerAccessToken.first()
        RetrofitClient.project_instance.getProjectList(
            bearerAccessToken
        ).enqueue(object : Callback<BaseResponseListProjectListResult> {
            override fun onResponse(
                p0: Call<BaseResponseListProjectListResult>,
                p1: Response<BaseResponseListProjectListResult>
            ) {
                when (p1.code()) {
                    200 -> {
                        val resp = p1.body()?.result
                        Log.d(NETWORK, "HomeFragment - setProjectListData() : 성공\n$resp")
                        if (resp != null) {
                            projectList.addAll(resp)
                            val isProjectEmpty = projectList.isEmpty()
                            binding.clHomeAddProject.visibility = if (isProjectEmpty) View.VISIBLE else View.GONE
                            binding.rvHomeProjectList.visibility = if (isProjectEmpty) View.GONE else View.VISIBLE
                            if (!isProjectEmpty) setProjectRV()
                        } else {
                            projectList = arrayListOf()
                        }
                    }
                    else -> {
                        Log.d(NETWORK, "HomeFragment - setProjectListData() : 실패")
                    }
                }
            }

            override fun onFailure(p0: Call<BaseResponseListProjectListResult>, p1: Throwable) {
                Log.d(NETWORK, "HomeFragment - setProjectListData() : 실패\nbecause $p1")
            }

        })
    }

    private suspend fun setScheduleListData() {
        scheduleList = arrayListOf()
        val dayOfMonth = formatDateForServer(binding.tvHomeDate.text.toString())
        val bearerAccessToken =
            LogmeetApplication.getInstance().getDataStore().bearerAccessToken.first()

        if (dayOfMonth != null) {
            RetrofitClient.schedule_instance.getUsersDaySchedule(
                authorization = bearerAccessToken,
                date = dayOfMonth
            ).enqueue(object : Callback<ScheduleDayResponse>{
                override fun onResponse(
                    p0: Call<ScheduleDayResponse>,
                    p1: Response<ScheduleDayResponse>
                ) {
                    when (p1.code()) {
                        200 -> {
                            val resp = p1.body()?.result
                            Log.d(NETWORK, "HomeFragment - setScheduleListData() : 성공\n")
                            scheduleList = resp as ArrayList<ScheduleListResult>

                            val isScheduleEmpty = scheduleList.size == 0
                            binding.clHomeNonschedule.visibility = if (isScheduleEmpty) View.VISIBLE else View.GONE
                            binding.rvHomeScheduleList.visibility = if (isScheduleEmpty) View.GONE else View.VISIBLE
                            if (!isScheduleEmpty) setScheduleRV()
                        }
                        else -> Log.d(NETWORK, "HomeFragment - setScheduleListData() : 실패")
                    }
                }

                override fun onFailure(p0: Call<ScheduleDayResponse>, p1: Throwable) {
                    Log.d(NETWORK, "HomeFragment - setScheduleListData() : 실패\nbecause $p1")
                }

            })
        }
    }

    private fun setScheduleRV() {
        scheduleAdapter = HomeScheduleAdapter(scheduleList)
        binding.rvHomeScheduleList.adapter = scheduleAdapter
        binding.rvHomeScheduleList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

}