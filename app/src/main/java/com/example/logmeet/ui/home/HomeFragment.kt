package com.example.logmeet.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.logmeet.databinding.FragmentHomeBinding
import com.example.logmeet.ui.component.WeeklyCalendar
import com.example.logmeet.ui.projects.MakeProjectActivity
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val today: LocalDate = LocalDate.now()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        binding.compHomeCalendar.setContent {
            WeeklyCalendar( selectedDate = {
                setScheduleDate(it)
                //일정 불러오는 api 연결
            })
        }

        //프로젝트가 아무것도 없을 때
        binding.clHomeAddProject.setOnClickListener {
            val intent = Intent(context, MakeProjectActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun setScheduleDate(dayOfMonth: String) {
        val beforeFormat = LocalDate.of(today.year, today.month, dayOfMonth.toInt())
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        val formattedDate = beforeFormat.format(formatter)

        binding.tvHomeDate.text = formattedDate
    }
}