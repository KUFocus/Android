package com.example.logmeet.ui.projects

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.input.key.Key.Companion.F
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.logmeet.NETWORK
import com.example.logmeet.R
import com.example.logmeet.data.dto.minutes.MinutesListResult
import com.example.logmeet.data.dto.project.api_response.BaseResponseListProjectListResult
import com.example.logmeet.databinding.ActivityProjectMinutesBinding
import com.example.logmeet.domain.entity.FileType
import com.example.logmeet.network.RetrofitClient
import com.example.logmeet.ui.application.LogmeetApplication
import com.example.logmeet.ui.minutes.MinutesAdapter
import com.example.logmeet.ui.minutes.SearchMinutesActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjectMinutesActivity : AppCompatActivity() {
    lateinit var binding : ActivityProjectMinutesBinding
    private lateinit var minutesAdapter: MinutesAdapter
    private var minutesList: ArrayList<MinutesListResult> = arrayListOf()
    private var minutesPhotoList: ArrayList<MinutesListResult> = arrayListOf()
    private var minutesVoiceList: ArrayList<MinutesListResult> = arrayListOf()
    private var projectId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityProjectMinutesBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        projectId = intent.getIntExtra("projectId", -1)
        setTabOnClick(1)
        init()

    }

    @SuppressLint("ResourceAsColor")
    private fun setTabOnClick(num: Int) {
        val tabTextList = arrayOf(binding.tvPrjMinutesTab1, binding.tvPrjMinutesTab2, binding.tvPrjMinutesTab3)
        val tabViewList = arrayOf(binding.vPrjMinutesTab1, binding.vPrjMinutesTab2, binding.vPrjMinutesTab3)
        val tabImgList = arrayOf(null, binding.ivPrjMinutesTab2, binding.ivPrjMinutesTab3)
        val gray = this.let { ContextCompat.getColor(it, R.color.gray200) }
        val blue = this.let { ContextCompat.getColor(it, R.color.main_blue) }

        for (i in 0..2) {
            tabTextList[i].setTextColor(gray)
            tabViewList[i].setBackgroundColor(gray)
            if (i!=0) tabImgList[i]?.setColorFilter(gray)
        }

        tabTextList[num-1].setTextColor(blue)
        tabViewList[num-1].setBackgroundColor(blue)
        if (num-1!=0) tabImgList[num-1]?.setColorFilter(blue)
    }

    private fun init() {
        binding.ivPrjMinutesClose.setOnClickListener { finish() }

        val projectName = intent.getStringExtra("projectName")
        binding.tvPrjMinutesTitle.text = projectName

        binding.ivPrjMinutesSearch.setOnClickListener {
            val intent = Intent(this, SearchMinutesActivity::class.java)
            intent.putExtra("projectId", projectId)
            startActivity(intent)
        }

        lifecycleScope.launch { setMinutesDataList() }
        binding.clPrjMinutesTab1.setOnClickListener {
            setTabOnClick(1)
            setMinutesRV(minutesList)
        }
        binding.clPrjMinutesTab2.setOnClickListener {//photo
            setTabOnClick(2)
            setMinutesRV(minutesPhotoList)
        }
        binding.clPrjMinutesTab3.setOnClickListener {//voice
            setTabOnClick(3)
            setMinutesRV(minutesVoiceList)
        }
        binding.clPrjMinutesOrder.setOnClickListener {

        }
    }

    private suspend fun setMinutesDataList() {
        val bearerAccessToken = LogmeetApplication.getInstance().getDataStore().bearerAccessToken.first()
        RetrofitClient.minutes_instance.getProjectMinutesList(
            authorization = bearerAccessToken,
            projectId = projectId
        ).enqueue(object : Callback<BaseResponseListProjectListResult> {
            override fun onResponse(
                p0: Call<BaseResponseListProjectListResult>,
                p1: Response<BaseResponseListProjectListResult>
            ) {
                when (p1.code()) {
                    200 -> {
                        val resp = p1.body()?.result
                        Log.d(NETWORK, "projectMinutes - setMinutesDataList() : 성공\n$resp")
                        if (resp != null) {
                            //status 관련 처리 필요
                            minutesList.addAll(resp.toList())
                            minutesList.forEach { minutes ->
                                when (minutes.type) {
                                    FileType.PICTURE.type -> {
                                        minutesPhotoList.add(minutes)
                                    }
                                    FileType.VOICE.type -> {
                                        minutesVoiceList.add(minutes)
                                    }
                                }
                            }
                            setMinutesRV(minutesList)
                        } else {
                            minutesList = arrayListOf()
                        }
                    }

                    else -> {
                        Log.d(NETWORK, "projectMinutes - setMinutesDataList() : 실패")
                    }
                }
            }

            override fun onFailure(p0: Call<BaseResponseListProjectListResult>, p1: Throwable) {
                Log.d(NETWORK, "projectMinutes - setMinutesDataList() : 실패\nbecause $p1")
            }

        })
    }

    private fun setMinutesRV(list: ArrayList<MinutesListResult>) {
        minutesAdapter = MinutesAdapter(list)
        binding.rvPrjMinutesList.adapter = minutesAdapter
        binding.rvPrjMinutesList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}