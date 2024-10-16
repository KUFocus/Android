package com.example.logmeet.ui.projects

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.logmeet.NETWORK
import com.example.logmeet.R
import com.example.logmeet.data.dto.schedule.ScheduleListResult
import com.example.logmeet.databinding.ActivityProjectCalendarBinding
import com.example.logmeet.network.RetrofitClient
import com.example.logmeet.ui.application.LogmeetApplication
import com.example.logmeet.ui.component.MonthlyCalendar
import com.example.logmeet.ui.home.HomeScheduleAdapter
import formatDate
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import reformatDate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class ProjectCalendarActivity : AppCompatActivity() {
    lateinit var binding: ActivityProjectCalendarBinding
    private lateinit var scheduleAdapter: HomeScheduleAdapter
    private var scheduleList: ArrayList<ScheduleListResult> = arrayListOf()
    private var projectId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityProjectCalendarBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        projectId = intent.getIntExtra("projectId", -1)

        binding.compProjectCalendarCalendar.setContent {
            MonthlyCalendar {
                binding.tvProjectCalendarDate.text = formatDate(it.toString())
                lifecycleScope.launch {
                    setProjectDayScheduleListData()
                }
            }
        }

        init()
    }

    private fun init() {
        lifecycleScope.launch {
            setProjectDayScheduleListData()
//            val isScheduleEmpty = scheduleList.isEmpty()
//            binding.clProjectCalendarNonschedule.visibility = if (isScheduleEmpty) View.VISIBLE else View.GONE
//            binding.rvProjectCalendarScheduleList.visibility = if (isScheduleEmpty) View.GONE else View.VISIBLE
//            if (!isScheduleEmpty) setScheduleRV()
        }
    }

    private suspend fun setProjectDayScheduleListData() {
        val dayOfMonth = reformatDate(binding.tvProjectCalendarDate.text.toString())
        val bearerAccessToken =
            LogmeetApplication.getInstance().getDataStore().bearerAccessToken.first()

        if (dayOfMonth != null) {
            RetrofitClient.schedule_instance.getProjectDaySchedule(
                authorization = bearerAccessToken,
                projectId = projectId,
                date = dayOfMonth
            ).enqueue(object : Callback<List<ScheduleListResult>> {
                override fun onResponse(
                    p0: Call<List<ScheduleListResult>>,
                    p1: Response<List<ScheduleListResult>>
                ) {
                    when (p1.code()) {
                        200 -> {
                            val resp = p1.body()
                            Log.d(NETWORK, "ProjectCalendar - setProjectDayScheduleListData() : 성공\n")
                            scheduleList = resp as ArrayList<ScheduleListResult>
                            val isScheduleEmpty = scheduleList.isEmpty()
                            binding.clProjectCalendarNonschedule.visibility = if (isScheduleEmpty) View.VISIBLE else View.GONE
                            binding.rvProjectCalendarScheduleList.visibility = if (isScheduleEmpty) View.GONE else View.VISIBLE
                            if (!isScheduleEmpty) setScheduleRV()
                        }
                        else -> Log.d(NETWORK, "ProjectCalendar - setProjectDayScheduleListData() : 실패")
                    }
                }

                override fun onFailure(p0: Call<List<ScheduleListResult>>, p1: Throwable) {
                    Log.d(NETWORK, "ProjectCalendar - setProjectDayScheduleListData() : 실패\nbecause $p1")
                }

            })
        }
    }

    private fun setScheduleRV() {
        scheduleAdapter = HomeScheduleAdapter(scheduleList)
        binding.rvProjectCalendarScheduleList.adapter = scheduleAdapter
        binding.rvProjectCalendarScheduleList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}