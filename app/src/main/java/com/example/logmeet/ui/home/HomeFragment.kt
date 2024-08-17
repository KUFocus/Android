package com.example.logmeet.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.logmeet.data.ProjectData
import com.example.logmeet.data.ScheduleData
import com.example.logmeet.databinding.FragmentHomeBinding
import com.example.logmeet.ui.component.WeeklyCalendar
import com.example.logmeet.ui.projects.MakeProjectActivity
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var scheduleAdapter: HomeScheduleAdapter
    private var scheduleList: ArrayList<ScheduleData> = arrayListOf()
    private lateinit var projectAdapter: HomeProjectAdapter
    private var projectList: ArrayList<ProjectData> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        init()

        binding.compHomeCalendar.setContent {
            WeeklyCalendar( selectedDate = {
                setScheduleDate(it)
                //일정 불러오는 api 연결
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

        setScheduleListData()
        if (scheduleList.isEmpty()) {
            binding.clHomeNonschedule.visibility = View.VISIBLE
            binding.rvHomeScheduleList.visibility = View.GONE
        } else {
            binding.clHomeNonschedule.visibility = View.GONE
            binding.rvHomeScheduleList.visibility = View.VISIBLE
            setScheduleRV()
        }

        setProjectListData()
        if (projectList.isEmpty()) {
            binding.clHomeAddProject.visibility = View.VISIBLE
            binding.rvHomeProjectList.visibility = View.GONE
        } else {
            binding.clHomeAddProject.visibility = View.GONE
            binding.rvHomeProjectList.visibility = View.VISIBLE
            setProjectRV()
        }
    }

    private fun setProjectRV() {
        projectAdapter = HomeProjectAdapter(projectList)
        binding.rvHomeProjectList.adapter = projectAdapter
        binding.rvHomeProjectList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setProjectListData() {
        projectList.addAll(
            arrayListOf(
                ProjectData("1", 1,"졸업프로젝트", "2024.03.05", "3", false),
                ProjectData("4", 2,"IT동아리", "2023.03.20", "20", true),
                ProjectData("7", 3,"산업협력프로젝트", "2023.04.05", "6",false),
                ProjectData("4", 4,"로그밋프로젝트", "2024.03.20", "4",false),
            )
        )
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
        binding.rvHomeScheduleList.adapter = scheduleAdapter
        binding.rvHomeScheduleList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    @SuppressLint("SetTextI18n")
    private fun setScheduleDate(dayOfMonth: String) {
        val today: LocalDate = LocalDate.now()
        val beforeFormat = LocalDate.of(today.year, today.month, dayOfMonth.toInt())
        val formattedDate = beforeFormat.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))

        binding.tvHomeDate.text = formattedDate
    }
}