package com.example.logmeet.ui.projects

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.logmeet.NETWORK
import com.example.logmeet.ProjectColorResources
import com.example.logmeet.R
import com.example.logmeet.data.dto.project.api_response.BaseResponseProjectInfoResult
import com.example.logmeet.entity.MinutesData
import com.example.logmeet.entity.ScheduleData
import com.example.logmeet.databinding.ActivityProjectHomeBinding
import com.example.logmeet.network.RetrofitClient
import com.example.logmeet.tag
import com.example.logmeet.ui.home.HomeScheduleAdapter
import com.example.logmeet.ui.minutes.MinutesAdapter
import formatDate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

class ProjectHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProjectHomeBinding
    private lateinit var scheduleAdapter: HomeScheduleAdapter
    private var scheduleList: ArrayList<ScheduleData> = arrayListOf()
    private lateinit var minutesAdapter: MinutesAdapter
    private var minutesList: ArrayList<MinutesData> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityProjectHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.ivPrjHomeBack.setOnClickListener { finish() }
        init()
    }

    private fun init() {
        binding.tvPrjHomeDate.text = formatDate(LocalDate.now().toString())
        getProjectDetail()

        setScheduleListData()
        val isScheduleEmpty = scheduleList.isEmpty()
        binding.clPrjHomeNonschedule.visibility = if (isScheduleEmpty) View.VISIBLE else View.GONE
        binding.rvPrjHomeScheduleList.visibility = if (isScheduleEmpty) View.GONE else View.VISIBLE
        if (!isScheduleEmpty) setScheduleRV()

        setMinutesListData()
        val isMinutesEmpty = minutesList.isEmpty()
        binding.tvPrjHomeNoneMinutes.visibility = if (isMinutesEmpty) View.VISIBLE else View.GONE
        binding.rvPrjHomeMinutesList.visibility = if (isMinutesEmpty) View.GONE else View.VISIBLE
        if (!isMinutesEmpty) setMinutesRV()
    }

    private fun getProjectDetail() {
        val projectId = intent.getStringExtra("projectId")?.toInt()
        if (projectId != null) {
            RetrofitClient.projectInstance.getProjectDetail(
                projectId = projectId
            ).enqueue(object : Callback<BaseResponseProjectInfoResult> {
                override fun onResponse(
                    p0: Call<BaseResponseProjectInfoResult>,
                    p1: Response<BaseResponseProjectInfoResult>
                ) {
                    if (p1.isSuccessful) {
                        val resp = requireNotNull(p1.body()?.result)
                        Log.d(NETWORK, "ProjectHome - getProjectDetail() : 성공")
                        binding.tvPrjHomePrjName.text = resp.name
                        binding.tvPrjHomePrjExplain.text = resp.content
                        val color = ProjectColorResources.getColorResourceByProject(resp.userProjects.color)
                        if (color!=null) {
                            binding.vPrjHomeBackground.setBackgroundColor(ContextCompat.getColor(this@ProjectHomeActivity, color))
                        }else {
                            Log.d(tag, "onResponse: ProjectHome (90) color = $color")
                        }
                    } else {
                        Log.d(NETWORK, "ProjectHome - getProjectDetail() : 실패")
                    }
                }

                override fun onFailure(p0: Call<BaseResponseProjectInfoResult>, p1: Throwable) {
                    Log.d(NETWORK, "ProjectHome - getProjectDetail()실패\nbecause : $p1")
                }

            })
        } else {
            Log.d(tag, "ProjectHome - getProjectDetail : projectId = null 값임")
        }
    }

    private fun setMinutesRV() {
        minutesAdapter = MinutesAdapter(minutesList)
        binding.rvPrjHomeMinutesList.adapter = minutesAdapter
        binding.rvPrjHomeMinutesList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun setMinutesListData() {
        minutesList.addAll(
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
            )
        )
    }

    private fun setScheduleListData() {
        scheduleList.addAll(
            arrayListOf(
                ScheduleData("1", "12:00", "디자인 회의", "졸업프로젝트"),
                ScheduleData("1", "16:00", "디자인 회의2", "졸업프로젝트"),
            )
        )
    }

    private fun setScheduleRV() {
        scheduleAdapter = HomeScheduleAdapter(scheduleList)
        binding.rvPrjHomeScheduleList.adapter = scheduleAdapter
        binding.rvPrjHomeScheduleList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}