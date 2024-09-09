package com.example.logmeet.ui.projects

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.logmeet.R
import com.example.logmeet.data.ScheduleData
import com.example.logmeet.databinding.ActivityProjectHomeBinding
import com.example.logmeet.ui.home.HomeScheduleAdapter
import formatDate
import java.time.LocalDate

class ProjectHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProjectHomeBinding
    private lateinit var scheduleAdapter: HomeScheduleAdapter
    private var scheduleList: ArrayList<ScheduleData> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityProjectHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.ivPrjHomeBack.setOnClickListener { finish() }
        init()
    }

    private fun init() {
        binding.tvPrjHomeDate.text = formatDate(LocalDate.now().toString())

        setScheduleListData()
        val isScheduleEmpty = scheduleList.isEmpty()
        binding.clPrjHomeNonschedule.visibility = if (isScheduleEmpty) View.VISIBLE else View.GONE
        binding.rvPrjHomeScheduleList.visibility = if (isScheduleEmpty) View.GONE else View.VISIBLE
        if (!isScheduleEmpty) setScheduleRV()

    }

    private fun setScheduleListData() {
        scheduleList.addAll(
            arrayListOf(
                ScheduleData("1", "12:00", "디자인 회의", "졸업프로젝트"),
                ScheduleData("1", "16:00", "디자인 회의2", "졸업프로젝트"),
            )
        )
    }

    private fun setScheduleRV() {
        scheduleAdapter = HomeScheduleAdapter(scheduleList)
        binding.rvPrjHomeScheduleList.adapter = scheduleAdapter
        binding.rvPrjHomeScheduleList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}