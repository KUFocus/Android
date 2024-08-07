package com.example.logmeet.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.logmeet.R
import com.example.logmeet.databinding.FragmentHomeBinding
import com.example.logmeet.ui.component.WeeklyCalendar
import com.example.logmeet.ui.component.WeeklyTitle

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


        return binding.root
    }
}