package com.example.logmeet.ui.minutes

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.logmeet.R
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

        setTabOnClick(1)
        init()

        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    private fun setTabOnClick(num: Int) {
        val tabTextList = arrayOf(binding.tvMinutesTab1, binding.tvMinutesTab2, binding.tvMinutesTab3)
        val tabViewList = arrayOf(binding.vMinutesTab1, binding.vMinutesTab2, binding.vMinutesTab3)
        val tabImgList = arrayOf(null, binding.ivMinutesTab2, binding.ivMinutesTab3)
        val gray = context?.let { ContextCompat.getColor(it, R.color.gray200) }
        val blue = context?.let { ContextCompat.getColor(it, R.color.main_blue) }

        for (i in 0..2) {
            if (gray != null) {
                tabTextList[i].setTextColor(gray)
                tabViewList[i].setBackgroundColor(gray)
                if (i!=0) tabImgList[i]?.setColorFilter(gray)
            }
        }

        if (blue != null) {
            tabTextList[num-1].setTextColor(blue)
            tabViewList[num-1].setBackgroundColor(blue)
            if (num-1!=0) tabImgList[num-1]?.setColorFilter(blue)
        }
    }

    private fun init() {
        binding.clMinutesTab1.setOnClickListener { setTabOnClick(1) }
        binding.clMinutesTab2.setOnClickListener { setTabOnClick(2) }
        binding.clMinutesTab3.setOnClickListener { setTabOnClick(3) }
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