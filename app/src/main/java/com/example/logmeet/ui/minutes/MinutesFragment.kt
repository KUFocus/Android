package com.example.logmeet.ui.minutes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.logmeet.data.MinutesData
import com.example.logmeet.databinding.FragmentMinutesBinding

class MinutesFragment : Fragment() {
    private lateinit var binding: FragmentMinutesBinding
    private lateinit var minutesAdapter: MinutesAdapter
    private var minutesDataList: ArrayList<MinutesData> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMinutesBinding.inflate(layoutInflater, container, false)

        init()

        return binding.root
    }

    private fun init() {
        setMinutesDataList()
        setMinutesRV()
    }

    private fun setMinutesDataList() {
        minutesDataList.addAll(
            arrayListOf(
                MinutesData(0, "1차 회의록","2024.03.04", "1", 0, false),
                MinutesData(1, "2차 회의록", "2024.03.04", "2", 2, true),
                MinutesData(0, "1차 회의록","2024.03.04", "3", 0, false),
                MinutesData(1, "2차 회의록", "2024.03.04", "4", 2, true),
                MinutesData(0, "1차 회의록","2024.03.04", "5", 0, false),
                MinutesData(1, "2차 회의록", "2024.03.04", "6", 2, true),
                MinutesData(0, "1차 회의록","2024.03.04", "7", 0, false),
                MinutesData(1, "2차 회의록", "2024.03.04", "8", 2, true),
                MinutesData(0, "1차 회의록","2024.03.04", "9", 0, false),
                MinutesData(1, "2차 회의록", "2024.03.04", "10", 2, true),
            )
        )
    }

    private fun setMinutesRV() {
        minutesAdapter = MinutesAdapter(minutesDataList)
        binding.rvMinutesList.adapter = minutesAdapter
        binding.rvMinutesList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
}