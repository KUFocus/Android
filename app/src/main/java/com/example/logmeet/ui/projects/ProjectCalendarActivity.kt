package com.example.logmeet.ui.projects

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide.init
import com.example.logmeet.R
import com.example.logmeet.databinding.ActivityProjectCalendarBinding
import com.example.logmeet.domain.entity.ScheduleData
import com.example.logmeet.ui.component.MonthlyCalendar
import com.example.logmeet.ui.home.HomeScheduleAdapter
import formatDate

class ProjectCalendarActivity : AppCompatActivity() {
    lateinit var binding: ActivityProjectCalendarBinding
    private lateinit var scheduleAdapter: HomeScheduleAdapter
    private var scheduleList: ArrayList<ScheduleData> = arrayListOf()

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

        binding.compProjectCalendarCalendar.setContent {
            MonthlyCalendar {
                binding.tvProjectCalendarDate.text = formatDate(it.toString())
            }
        }

        init()
    }

    private fun init() {
        setProjectScheduleListData()
        val isScheduleEmpty = scheduleList.isEmpty()
        binding.clProjectCalendarNonschedule.visibility = if (isScheduleEmpty) View.VISIBLE else View.GONE
        binding.rvProjectCalendarScheduleList.visibility = if (isScheduleEmpty) View.GONE else View.VISIBLE
        if (!isScheduleEmpty) setScheduleRV()
    }

    private fun setProjectScheduleListData() {

    }

    private fun setScheduleRV() {
        scheduleAdapter = HomeScheduleAdapter(scheduleList)
        binding.rvProjectCalendarScheduleList.adapter = scheduleAdapter
        binding.rvProjectCalendarScheduleList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}