package com.example.logmeet.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.logmeet.R
import com.example.logmeet.databinding.FragmentHomeBinding
import com.example.logmeet.ui.component.WeeklyCalendar
import com.example.logmeet.ui.component.WeeklyTitle
import com.example.logmeet.ui.projects.MakeProjectActivity

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        binding.compHomeCalendar.setContent {
            WeeklyCalendar()
        }


        //프로젝트가 아무것도 없을 때
        binding.clHomeAddProject.setOnClickListener {
            val intent = Intent(context, MakeProjectActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }
}