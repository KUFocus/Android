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
import com.example.logmeet.NETWORK
import com.example.logmeet.domain.entity.ProjectColorResources
import com.example.logmeet.R
import com.example.logmeet.data.dto.project.api_response.BaseResponseProjectInfoResult
import com.example.logmeet.data.dto.schedule.ScheduleListResult
import com.example.logmeet.domain.entity.MinutesData
import com.example.logmeet.domain.entity.ScheduleData
import com.example.logmeet.databinding.ActivityProjectHomeBinding
import com.example.logmeet.network.RetrofitClient
import com.example.logmeet.tag
import com.example.logmeet.ui.application.LogmeetApplication
import com.example.logmeet.ui.home.HomeScheduleAdapter
import com.example.logmeet.ui.minutes.MinutesAdapter
import formatDate
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

        init()
        binding.ivPrjHomeBack.setOnClickListener { finish() }
    }

    private fun init() {
        binding.tvPrjHomeDate.text = formatDate(LocalDate.now().toString())
        lifecycleScope.launch {
            getProjectDetail()
        }

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

    private suspend fun getProjectDetail() {
        val projectId = intent.getIntExtra("projectId", -1)
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

    private fun setMinutesListData() {
        //회의록 데이터 불러오기
    }

    private fun setScheduleListData() {
        //스케줄 목록 불러오기
    }

    private fun setScheduleRV() {
        scheduleAdapter = HomeScheduleAdapter(scheduleList)
        binding.rvPrjHomeScheduleList.adapter = scheduleAdapter
        binding.rvPrjHomeScheduleList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}