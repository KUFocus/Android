package com.example.logmeet.ui.minutes

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.logmeet.R
import com.example.logmeet.domain.entity.MinutesData
import com.example.logmeet.databinding.FragmentMinutesBinding

class MinutesFragment : Fragment() {
    private lateinit var binding: FragmentMinutesBinding
    private lateinit var minutesAdapter: MinutesAdapter
    private var minutesList: ArrayList<MinutesData> = arrayListOf()
    private var minutesPhotoList: ArrayList<MinutesData> = arrayListOf()
    private var minutesVoiceList: ArrayList<MinutesData> = arrayListOf()

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
        binding.ivMinutesSearch.setOnClickListener {
            val intent = Intent(context, SearchMinutesActivity::class.java)
            startActivity(intent)
        }

        setMinutesDataList()
        setMinutesRV(minutesList)
        binding.clMinutesTab1.setOnClickListener {
            setTabOnClick(1)
            setMinutesRV(minutesList)
        }
        binding.clMinutesTab2.setOnClickListener {//photo
            setTabOnClick(2)
            setMinutesRV(minutesPhotoList)
        }
        binding.clMinutesTab3.setOnClickListener {//voice
            setTabOnClick(3)
            setMinutesRV(minutesVoiceList)
        }
        binding.clMinutesOrder.setOnClickListener {

        }
    }

    private fun setMinutesDataList() {
        minutesList.addAll(
            arrayListOf(
                MinutesData(0, "회의 예시 1","2024.03.04", "1", 0, false),
                MinutesData(1, "회의 예시 2","2024.03.05", "2", 2, true),
                MinutesData(4, "회의 예시 3","2024.03.06", "7", 1, false),
                MinutesData(5, "회의 예시 4","2024.03.07", "11", 0, false),
            )
        )
        minutesList.forEach {
            if (it.type==1) minutesPhotoList.add(it)
            else if (it.type==2) minutesVoiceList.add(it)
        }
    }

    private fun setMinutesRV(list: ArrayList<MinutesData>) {
        minutesAdapter = MinutesAdapter(list)
        binding.rvMinutesList.adapter = minutesAdapter
        binding.rvMinutesList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
}