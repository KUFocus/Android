package com.example.logmeet.ui.home

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.logmeet.R
import com.example.logmeet.domain.entity.ScheduleData
import com.example.logmeet.databinding.ActivityHomeFullCalendarBinding
import com.example.logmeet.ui.component.MonthlyCalendar
import formatDate

class HomeFullCalendarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeFullCalendarBinding
    private lateinit var scheduleAdapter: HomeScheduleAdapter
    private var scheduleList: ArrayList<ScheduleData> = arrayListOf()
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

        binding.compHomeFullCalendar.setContent {
            MonthlyCalendar {
                //setScheduleDate(it)
                binding.tvHomeFullDate.text = formatDate(it.toString())
            }
        }

        init()
    }

    private fun init() {
        setScheduleListData()
        val isScheduleEmpty = scheduleList.isEmpty()
        binding.clHomeFullNonschedule.visibility = if (isScheduleEmpty) View.VISIBLE else View.GONE
        binding.rvHomeFullScheduleList.visibility = if (isScheduleEmpty) View.GONE else View.VISIBLE
        if (!isScheduleEmpty) setScheduleRV()
    }

    private fun setScheduleListData() {

    }

    private fun setScheduleRV() {
        scheduleAdapter = HomeScheduleAdapter(scheduleList)
        binding.rvHomeFullScheduleList.adapter = scheduleAdapter
        binding.rvHomeFullScheduleList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}