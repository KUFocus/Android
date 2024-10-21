package com.example.logmeet.ui.projects

import android.os.Bundle
import android.system.Os.remove
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide.init
import com.example.logmeet.NETWORK
import com.example.logmeet.domain.entity.ProjectColorResources
import com.example.logmeet.R
import com.example.logmeet.data.dto.minutes.BaseResponseListMinutesListResult
import com.example.logmeet.data.dto.minutes.MinutesListResult
import com.example.logmeet.data.dto.project.api_response.BaseResponseProjectInfoResult
import com.example.logmeet.data.dto.schedule.ScheduleListResult
import com.example.logmeet.data.dto.schedule.api_response.ScheduleDayResponse
import com.example.logmeet.domain.entity.MinutesData
import com.example.logmeet.domain.entity.ScheduleData
import com.example.logmeet.databinding.ActivityProjectHomeBinding
import com.example.logmeet.domain.entity.FileType
import com.example.logmeet.network.RetrofitClient
import com.example.logmeet.tag
import com.example.logmeet.ui.application.LogmeetApplication
import com.example.logmeet.ui.home.HomeScheduleAdapter
import com.example.logmeet.ui.minutes.MinutesAdapter
import formatDate
import formatDateForServer
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

class ProjectHomeActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: ActivityProjectHomeBinding
    private lateinit var scheduleAdapter: HomeScheduleAdapter
    private var scheduleList: ArrayList<ScheduleListResult> = arrayListOf()
    private lateinit var minutesAdapter: MinutesAdapter
    private var minutesList: ArrayList<MinutesListResult> = arrayListOf()
    private var projectId = -1

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

        projectId = intent.getIntExtra("projectId", -1)
        init()
    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {
            getProjectDetail()
            setScheduleListData()
            setMinutesListData()
        }
    }

    private fun init() {
        binding.tvPrjHomeDate.text = formatDate(LocalDate.now().toString())
        binding.ivPrjHomeBack.setOnClickListener { finish() }
    }

    private suspend fun getProjectDetail() {
        val bearerAccessToken =
            LogmeetApplication.getInstance().getDataStore().bearerAccessToken.first()
        RetrofitClient.project_instance.getProjectDetail(
            bearerAccessToken,
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
                    val color =
                        ProjectColorResources.getColorResourceByProject(resp.userProjects[0].color)
                    if (color != null) {
                        binding.vPrjHomeBackground.setBackgroundColor(
                            ContextCompat.getColor(
                                this@ProjectHomeActivity,
                                color
                            )
                        )

                        drawerLayout = binding.root
                        supportFragmentManager.beginTransaction()
                            .replace(
                                R.id.fl_sidebar_menu, ProjectSidebarFragment(
                                    resp.projectId, resp.name, resp.userProjects[0].bookmark, this@ProjectHomeActivity
                                )
                            ).commit()
                        binding.ivPrjHomeBurgerMenu.setOnClickListener {
                            drawerLayout.openDrawer(GravityCompat.END)
                        }
                    } else {
                        Log.d(tag, "onResponse: ProjectHome (90) color = null")
                    }
                } else {
                    Log.d(NETWORK, "ProjectHome - getProjectDetail() : 실패")
                }
            }

            override fun onFailure(p0: Call<BaseResponseProjectInfoResult>, p1: Throwable) {
                Log.d(NETWORK, "ProjectHome - getProjectDetail()실패\nbecause : $p1")
            }

        })
    }

    private fun setMinutesRV() {
        minutesAdapter = MinutesAdapter(minutesList)
        binding.rvPrjHomeMinutesList.adapter = minutesAdapter
        binding.rvPrjHomeMinutesList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private suspend fun setMinutesListData() {
        val bearerAccessToken = LogmeetApplication.getInstance().getDataStore().bearerAccessToken.first()
        RetrofitClient.minutes_instance.getProjectMinutesList(
            authorization = bearerAccessToken,
            projectId = projectId
        ).enqueue(object : Callback<BaseResponseListMinutesListResult> {
            override fun onResponse(
                p0: Call<BaseResponseListMinutesListResult>,
                p1: Response<BaseResponseListMinutesListResult>
            ) {
                when (p1.code()) {
                    200 -> {
                        val resp = p1.body()?.result
                        Log.d(NETWORK, "projectHome - setMinutesListData() : 성공\n$resp")
                        if (resp != null) {
                            //status 관련 처리 필요
                            minutesList.addAll(resp.toList())
                            val isMinutesEmpty = minutesList.isEmpty()
                            binding.tvPrjHomeNoneMinutes.visibility = if (isMinutesEmpty) View.VISIBLE else View.GONE
                            binding.rvPrjHomeMinutesList.visibility = if (isMinutesEmpty) View.GONE else View.VISIBLE
                            if (!isMinutesEmpty) setMinutesRV()
                        } else {
                            minutesList = arrayListOf()
                        }
                    }

                    else -> {
                        Log.d(NETWORK, "projectHome - setMinutesListData() : 실패")
                    }
                }
            }

            override fun onFailure(p0: Call<BaseResponseListMinutesListResult>, p1: Throwable) {
                Log.d(NETWORK, "projectHome - setMinutesListData() : 실패\nbecause $p1")
            }

        })
    }

    private suspend fun setScheduleListData() {
        scheduleList = arrayListOf()
        val dayOfMonth = formatDateForServer(binding.tvPrjHomeDate.text.toString())
        val bearerAccessToken =
            LogmeetApplication.getInstance().getDataStore().bearerAccessToken.first()

        if (dayOfMonth != null) {
            RetrofitClient.schedule_instance.getUsersDaySchedule(
                authorization = bearerAccessToken,
                date = dayOfMonth
            ).enqueue(object : Callback<ScheduleDayResponse>{
                override fun onResponse(
                    p0: Call<ScheduleDayResponse>,
                    p1: Response<ScheduleDayResponse>
                ) {
                    when (p1.code()) {
                        200 -> {
                            val resp = p1.body()?.result
                            Log.d(NETWORK, "projectHome - setScheduleListData() : 성공\n")
                            scheduleList = resp as ArrayList<ScheduleListResult>

                            val isScheduleEmpty = scheduleList.size == 0
                            binding.clPrjHomeNonschedule.visibility = if (isScheduleEmpty) View.VISIBLE else View.GONE
                            binding.rvPrjHomeScheduleList.visibility = if (isScheduleEmpty) View.GONE else View.VISIBLE
                            if (!isScheduleEmpty) setScheduleRV()
                        }
                        else -> Log.d(NETWORK, "projectHome - setScheduleListData() : 실패")
                    }
                }

                override fun onFailure(p0: Call<ScheduleDayResponse>, p1: Throwable) {
                    Log.d(NETWORK, "projectHome - setScheduleListData() : 실패\nbecause $p1")
                }

            })
        }
    }

    private fun setScheduleRV() {
        scheduleAdapter = HomeScheduleAdapter(scheduleList)
        binding.rvPrjHomeScheduleList.adapter = scheduleAdapter
        binding.rvPrjHomeScheduleList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}