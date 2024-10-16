package com.example.logmeet.ui.home

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
import com.example.logmeet.data.dto.project.ProjectListResult
import com.example.logmeet.data.dto.project.api_response.BaseResponseListProjectListResult
import com.example.logmeet.data.dto.schedule.ScheduleListResult
import com.example.logmeet.domain.entity.MinutesData
import com.example.logmeet.domain.entity.ProjectData
import com.example.logmeet.domain.entity.ScheduleData
import com.example.logmeet.databinding.FragmentHomeBinding
import com.example.logmeet.network.RetrofitClient
import com.example.logmeet.ui.application.LogmeetApplication
import com.example.logmeet.ui.component.WeeklyCalendar
import com.example.logmeet.ui.minutes.MinutesAdapter
import com.example.logmeet.ui.projects.MakeProjectActivity
import formatDate
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import reformatDate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var scheduleAdapter: HomeScheduleAdapter
    private var scheduleList: ArrayList<ScheduleListResult> = arrayListOf()
    private lateinit var projectAdapter: HomeProjectAdapter
    private var projectList: ArrayList<ProjectListResult> = arrayListOf()
    private lateinit var minutesAdapter: MinutesAdapter
    private var minutesList: ArrayList<MinutesData> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        init()

        binding.compHomeCalendar.setContent {
            WeeklyCalendar( selectedDate = {
                binding.tvHomeDate.text = formatDate(it)
                lifecycleScope.launch {
                    setScheduleListData()
                }
            })
        }

        return binding.root
    }

    private fun init() {
        binding.tvHomeDate.text = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")).toString()
        binding.clHomeAddProject.setOnClickListener {
            val intent = Intent(context, MakeProjectActivity::class.java)
            startActivity(intent)
        }
        binding.clHomeMoreProject.setOnClickListener {
            (activity as? MainHomeActivity)?.let {
                it.binding.vpAcMainFragPager.currentItem = 1
            }
        }
        binding.clHomeMoreMom.setOnClickListener {
            (activity as? MainHomeActivity)?.let {
                it.binding.vpAcMainFragPager.currentItem = 3
            }
        }

        lifecycleScope.launch {
            setScheduleListData()
        }

        lifecycleScope.launch {
            setProjectListData()
//            val isProjectEmpty = projectList.isEmpty()
//            binding.clHomeAddProject.visibility = if (isProjectEmpty) View.VISIBLE else View.GONE
//            binding.rvHomeProjectList.visibility = if (isProjectEmpty) View.GONE else View.VISIBLE
//            if (!isProjectEmpty) setProjectRV()
        }

        setMinutesListData()
        val isMinutesEmpty = minutesList.isEmpty()
        binding.tvHomeNoneMinutes.visibility = if (isMinutesEmpty) View.VISIBLE else View.GONE
        binding.rvHomeMinutesList.visibility = if (isMinutesEmpty) View.GONE else View.VISIBLE
        if (!isMinutesEmpty) setMinutesRV()
    }

    private fun setMinutesRV() {
        minutesAdapter = MinutesAdapter(minutesList)
        binding.rvHomeMinutesList.adapter = minutesAdapter
        binding.rvHomeMinutesList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun setMinutesListData() {
        //4개까지만 넣기
        minutesList.addAll(
            arrayListOf(
                MinutesData(0, "회의 예시 1","2024.03.04", "1", 0, false),
                MinutesData(1, "회의 예시 2","2024.03.05", "2", 2, true),
                MinutesData(4, "회의 예시 3","2024.03.06", "7", 1, false),
                MinutesData(5, "회의 예시 4","2024.03.07", "11", 0, false),
            )
        )
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
        val dayOfMonth = reformatDate(binding.tvHomeDate.text.toString())
        val bearerAccessToken =
            LogmeetApplication.getInstance().getDataStore().bearerAccessToken.first()

        if (dayOfMonth != null) {
            RetrofitClient.schedule_instance.getUsersDaySchedule(
                authorization = bearerAccessToken,
                date = dayOfMonth
            ).enqueue(object : Callback<List<ScheduleListResult>>{
                override fun onResponse(
                    p0: Call<List<ScheduleListResult>>,
                    p1: Response<List<ScheduleListResult>>
                ) {
                    when (p1.code()) {
                        200 -> {
                            val resp = p1.body()
                            Log.d(NETWORK, "HomeFragment - setScheduleListData() : 성공\n")
                            scheduleList = resp as ArrayList<ScheduleListResult>
                            val isScheduleEmpty = scheduleList.isEmpty()
                            binding.clHomeNonschedule.visibility = if (isScheduleEmpty) View.VISIBLE else View.GONE
                            binding.rvHomeScheduleList.visibility = if (isScheduleEmpty) View.GONE else View.VISIBLE
                            if (!isScheduleEmpty) setScheduleRV()
                        }
                        else -> Log.d(NETWORK, "HomeFragment - setScheduleListData() : 실패")
                    }
                }

                override fun onFailure(p0: Call<List<ScheduleListResult>>, p1: Throwable) {
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