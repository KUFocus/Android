package com.example.logmeet.ui.home

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.logmeet.NETWORK
import com.example.logmeet.R
import com.example.logmeet.data.dto.schedule.ScheduleListResult
import com.example.logmeet.data.dto.schedule.api_response.ScheduleDayResponse
import com.example.logmeet.databinding.ActivityHomeFullCalendarBinding
import com.example.logmeet.network.RetrofitClient
import com.example.logmeet.ui.application.LogmeetApplication
import com.example.logmeet.ui.component.MonthlyCalendar
import com.example.logmeet.ui.showMinutesToast
import formatDate
import formatDateForFront
import formatDateForServer
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFullCalendarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeFullCalendarBinding
    private lateinit var scheduleAdapter: HomeScheduleAdapter
    private var scheduleList: ArrayList<ScheduleListResult> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeFullCalendarBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        init()

        binding.compHomeFullCalendar.setContent {
            MonthlyCalendar(
                isBottomSheet = false,
                selectedDate = {
                    binding.tvHomeFullDate.text = formatDateForFront(it)

                    lifecycleScope.launch { setScheduleListData() }
                },
                onDismiss = { },
                onAddScheduleComplete = { resultCode ->
                    if (resultCode == Activity.RESULT_OK)
                        showMinutesToast(this, R.drawable.ic_check_circle, "일정이 추가되었습니다.")
                }
            )
        }
    }

    private fun init() {
        lifecycleScope.launch { setScheduleListData() }
    }

    private suspend fun setScheduleListData() {
        scheduleList = arrayListOf()
        val dayOfMonth = formatDateForServer(binding.tvHomeFullDate.text.toString())
        val bearerAccessToken =
            LogmeetApplication.getInstance().getDataStore().bearerAccessToken.first()

        if (dayOfMonth != null) {
            RetrofitClient.schedule_instance.getUsersDaySchedule(
                authorization = bearerAccessToken,
                date = dayOfMonth
            ).enqueue(object : Callback<ScheduleDayResponse> {
                override fun onResponse(
                    p0: Call<ScheduleDayResponse>,
                    p1: Response<ScheduleDayResponse>
                ) {
                    when (p1.code()) {
                        200 -> {
                            val resp = p1.body()?.result
                            Log.d(NETWORK, "HomeFullCalendar - setScheduleListData() : 성공\n")
                            scheduleList = resp as ArrayList<ScheduleListResult>

                            val isScheduleEmpty = scheduleList.size == 0
                            binding.clHomeFullNonschedule.visibility = if (isScheduleEmpty) View.VISIBLE else View.GONE
                            binding.rvHomeFullScheduleList.visibility = if (isScheduleEmpty) View.GONE else View.VISIBLE
                            if (!isScheduleEmpty) setScheduleRV()
                        }
                        else -> Log.d(NETWORK, "HomeFullCalendar - setScheduleListData() : 실패")
                    }
                }

                override fun onFailure(p0: Call<ScheduleDayResponse>, p1: Throwable) {
                    Log.d(NETWORK, "HomeFullCalendar - setScheduleListData() : 실패\nbecause $p1")
                }

            })
        }
    }

    private fun setScheduleRV() {
        scheduleAdapter = HomeScheduleAdapter(scheduleList)
        binding.rvHomeFullScheduleList.adapter = scheduleAdapter
        binding.rvHomeFullScheduleList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}