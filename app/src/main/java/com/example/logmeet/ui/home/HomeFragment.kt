package com.example.logmeet.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.logmeet.entity.MinutesData
import com.example.logmeet.entity.ProjectData
import com.example.logmeet.entity.ScheduleData
import com.example.logmeet.databinding.FragmentHomeBinding
import com.example.logmeet.ui.component.WeeklyCalendar
import com.example.logmeet.ui.minutes.MinutesAdapter
import com.example.logmeet.ui.projects.MakeProjectActivity
import formatDate
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var scheduleAdapter: HomeScheduleAdapter
    private var scheduleList: ArrayList<ScheduleData> = arrayListOf()
    private lateinit var projectAdapter: HomeProjectAdapter
    private var projectList: ArrayList<ProjectData> = arrayListOf()
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
                //일정 불러오는 api 연결
            })
        }
        binding.compHomeCalendar.setOnClickListener {
            val intent = Intent(context, HomeFullCalendarActivity::class.java)
            startActivity(intent)
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

        setScheduleListData()
        val isScheduleEmpty = scheduleList.isEmpty()
        binding.clHomeNonschedule.visibility = if (isScheduleEmpty) View.VISIBLE else View.GONE
        binding.rvHomeScheduleList.visibility = if (isScheduleEmpty) View.GONE else View.VISIBLE
        if (!isScheduleEmpty) setScheduleRV()

        setProjectListData()
        val isProjectEmpty = projectList.isEmpty()
        binding.clHomeAddProject.visibility = if (isProjectEmpty) View.VISIBLE else View.GONE
        binding.rvHomeProjectList.visibility = if (isProjectEmpty) View.GONE else View.VISIBLE
        if (!isProjectEmpty) setProjectRV()

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
                MinutesData(0, "1차 회의록","2024.03.04", "1", 0, false),
                MinutesData(1, "2차 회의록","2024.03.04", "2", 2, true),
                MinutesData(4, "3차 회의록","2024.03.04", "3", 1, false),
                MinutesData(5, "1차 회의록","2024.03.04", "1", 0, false),
            )
        )
    }

    private fun setProjectRV() {
        projectAdapter = HomeProjectAdapter(projectList)
        binding.rvHomeProjectList.adapter = projectAdapter
        binding.rvHomeProjectList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setProjectListData() {
        projectList.addAll(
            arrayListOf(
//                ProjectData("1", 1,"졸업프로젝트", "2024.03.05", "3", false),
//                ProjectData("4", 2,"IT동아리", "2023.03.20", "20", true),
//                ProjectData("7", 3,"산업협력프로젝트", "2023.04.05", "6",false),
//                ProjectData("4", 4,"로그밋프로젝트", "2024.03.20", "4",false),
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

}