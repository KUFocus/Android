package com.example.logmeet.ui.minutes

import android.graphics.PorterDuff
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.logmeet.R
import com.example.logmeet.domain.entity.MinutesData
import com.example.logmeet.databinding.ActivityResultMinutesBinding

class ResultMinutesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultMinutesBinding
    private lateinit var minutesAdapter: MinutesAdapter
    private var resultList: ArrayList<MinutesData> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityResultMinutesBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.ivResultMSearch.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_IN)

        setClickBtn()
        init()

    }

    private fun setClickBtn() {
        binding.ivResultMClose.setOnClickListener { finish() }
        binding.vResultMBtn.setOnClickListener {
            //검색api
            //list, RV 재설정
        }
    }

    private fun init() {
        setResultList()
        setResultRV()
    }

    private fun setResultRV() {
        minutesAdapter = MinutesAdapter(resultList)
        binding.rvResultMList.adapter = minutesAdapter
        binding.rvResultMList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun setResultList() {
        resultList.addAll(
            arrayListOf(
                MinutesData(0, "1차 회의록","2024.03.04", "1", 0, false),
                MinutesData(1, "2차 회의록","2024.03.04", "2", 2, true),
                MinutesData(4, "3차 회의록","2024.03.04", "3", 1, false),
                MinutesData(5, "1차 회의록","2024.03.04", "1", 0, false),
                MinutesData(6, "2차 회의록","2024.03.04", "2", 2, true),
                MinutesData(7, "3차 회의록","2024.03.04", "3", 1, false),
                MinutesData(8, "1차 회의록","2024.03.04", "1", 0, false),
                MinutesData(9, "2차 회의록","2024.03.04", "2", 2, true),
                MinutesData(41, "3차 회의록","2024.03.04", "3", 1, false),
                MinutesData(20, "1차 회의록","2024.03.04", "1", 0, false),
                MinutesData(14, "2차 회의록","2024.03.04", "2", 2, true),
                MinutesData(47, "3차 회의록","2024.03.04", "3", 1, false),
                MinutesData(92, "1차 회의록","2024.03.04", "1", 0, false),
                MinutesData(12, "2차 회의록","2024.03.04", "2", 2, true),
                MinutesData(45, "3차 회의록","2024.03.04", "3", 1, false),
            )
        )
    }
}