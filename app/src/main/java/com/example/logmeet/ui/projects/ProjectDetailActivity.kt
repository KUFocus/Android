package com.example.logmeet.ui.projects

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.logmeet.NETWORK
import com.example.logmeet.domain.entity.ProjectDrawableResources
import com.example.logmeet.R
import com.example.logmeet.data.dto.project.api_response.BaseResponseProjectInfoResult
import com.example.logmeet.domain.entity.PeopleData
import com.example.logmeet.databinding.ActivityProjectDetailBinding
import com.example.logmeet.domain.entity.ProjectColorResources
import com.example.logmeet.network.RetrofitClient
import com.example.logmeet.tag
import com.example.logmeet.ui.application.LogmeetApplication
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ProjectDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProjectDetailBinding
    private lateinit var peopleAdapter: PeopleAdapter
    private var peopleList: ArrayList<PeopleData> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityProjectDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        init()
        lifecycleScope.launch {
            getContent()
        }
    }

    private fun init() {
        binding.ivDetailPBack.setOnClickListener { finish() }
        binding.ivDetailPBurgerMenu.setOnClickListener {
            val intent = Intent(this, EditProjectActivity::class.java)
            val projectID = getIntent().getIntExtra("projectId", -1)
            intent.putExtra("projectId", projectID)
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    private suspend fun getContent() {
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
                    binding.tietDetailPName.setText(resp.name)
                    binding.tietDetailPExplain.setText(resp.content)
                    val dateTime = resp.createdAt.substring(0, 10)
                    binding.tietDetailPDate.setText(dateTime)
                    peopleList = arrayListOf(
                        PeopleData("김채린", false, 1, "email"),
                        PeopleData("구서정", false, 1, "email"),
                        PeopleData("학생1", false, 1, "email"),
                        PeopleData("학생2", false, 1, "email"),
                        PeopleData("전우진", true, 1, "email"),
                        PeopleData("학생3", false, 1, "email"),
                    )
                    setPeopleRv()

                    val number = resp.userProjects[0].color.split("_")[1]
                    setProjectColor(number)
                } else {
                    Log.d(NETWORK, "ProjectHome - getProjectDetail() : 실패")
                }
            }

            override fun onFailure(p0: Call<BaseResponseProjectInfoResult>, p1: Throwable) {
                Log.d(NETWORK, "ProjectHome - getProjectDetail()실패\nbecause : $p1")
            }

        })
    }

    private fun setPeopleRv() {
        val peopleRV = binding.rvDetailPPeopleList
        val sortedList = sortPeopleList(peopleList)
        peopleAdapter = PeopleAdapter(sortedList)
        peopleRV.adapter = peopleAdapter
        peopleRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun sortPeopleList(peopleList: ArrayList<PeopleData>): ArrayList<PeopleData> {
        return ArrayList(
            peopleList
                .sortedWith(
                    compareByDescending<PeopleData> { it.leader }
                        .thenBy { it.name }
                )
        )
    }

    private fun setProjectColor(colorId: String) {
        val color = ProjectDrawableResources.colorList[colorId.toInt()-1]
        binding.vDetailPColor.setBackgroundResource(color)
    }
}