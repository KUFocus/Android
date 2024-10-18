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
import com.example.logmeet.data.dto.minutes.MinutesListResult
import com.example.logmeet.domain.entity.MinutesData
import com.example.logmeet.databinding.ActivityResultMinutesBinding

class ResultMinutesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultMinutesBinding
    private lateinit var minutesAdapter: MinutesAdapter
    private var resultList: ArrayList<MinutesListResult> = arrayListOf()
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

    }
}