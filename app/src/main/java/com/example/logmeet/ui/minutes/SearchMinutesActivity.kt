package com.example.logmeet.ui.minutes

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.logmeet.R
import com.example.logmeet.domain.entity.RecentData
import com.example.logmeet.databinding.ActivitySearchMinutesBinding

class SearchMinutesActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchMinutesBinding
    private lateinit var recentAdapter: RecentAdapter
    private var recentList: ArrayList<RecentData> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySearchMinutesBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.ivSearchMSearch.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_IN)
        binding.ivSearchMDelete.setColorFilter(ContextCompat.getColor(this, R.color.black))
        init()
    }

    private fun init(){
        binding.ivSearchMClose.setOnClickListener { finish() }
        binding.vSearchMBtn.setOnClickListener {
            val intent = Intent(this, ResultMinutesActivity::class.java)
            intent.putExtra("keyword", binding.tietSearchMInput.text)
            startActivity(intent)
            finish()
        }

        setRecentList()
        setRecentRV()
        binding.clSearchMDeleteAll.setOnClickListener {
            //data 모두 지우는 api
            recentList = arrayListOf()
            setRecentRV()
        }
    }

    private fun setRecentRV() {
        binding.tvSearchMNoRecent.visibility = if (recentList.size==0) View.VISIBLE else View.GONE
        binding.tvSearchMYesRecent.visibility = if (recentList.size==0) View.GONE else View.VISIBLE
        binding.clSearchMDeleteAll.visibility = if (recentList.size==0) View.GONE else View.VISIBLE

        recentAdapter = RecentAdapter(recentList)
        binding.rvSearchM.adapter = recentAdapter
        binding.rvSearchM.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun setRecentList() {
        recentList.addAll(
            arrayListOf(
                RecentData("검색", "2024.03.01"),
                RecentData("검색1", "2024.03.02"),
                RecentData("검색2", "2024.03.03"),
                RecentData("검색3", "2024.03.04"),
                RecentData("검색4", "2024.03.05"),
            )
        )
    }
}