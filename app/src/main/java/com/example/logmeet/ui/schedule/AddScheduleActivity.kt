package com.example.logmeet.ui.schedule

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.logmeet.NETWORK
import com.example.logmeet.R
import com.example.logmeet.data.dto.project.ProjectListResult
import com.example.logmeet.data.dto.project.api_response.BaseResponseListProjectListResult
import com.example.logmeet.data.dto.schedule.api_request.ScheduleCreateRequest
import com.example.logmeet.data.dto.schedule.api_response.ScheduleCreateResponse
import com.example.logmeet.databinding.ActivityAddScheduleBinding
import com.example.logmeet.domain.entity.ProjectDrawableResources
import com.example.logmeet.network.RetrofitClient
import com.example.logmeet.ui.application.LogmeetApplication
import com.example.logmeet.ui.showMinutesToast
import formatDateForFront
import formatDateForServer
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddScheduleActivity() : AppCompatActivity(), ProjectSelectionAdapter.OnProjectClickListener {
    lateinit var binding: ActivityAddScheduleBinding
    var isNameNotNull = false
    private lateinit var projectSelectionAdapter: ProjectSelectionAdapter
    private var projectList: List<ProjectListResult> = arrayListOf()
    private var projectId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddScheduleBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.rvAddScheduleProjectList.visibility = View.GONE
        binding.ivAddScheduleClose.setOnClickListener { finish() }
        val btnNameClear = binding.ivAddScheduleNameClear
        binding.ivAddScheduleNameClear.setOnClickListener {
            binding.tietAddScheduleName.setText("")
        }

        binding.tietAddScheduleName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    btnNameClear.visibility = View.GONE
                } else {
                    btnNameClear.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {
                isNameNotNull = !s.isNullOrEmpty()
                checkBtnAvailable()
            }
        })

        binding.tietAddScheduleDate.setOnClickListener {
            val datePicker = DatePickerBottomSheetFragment { selectedDate ->
                val date = formatDateForFront(selectedDate)
                binding.tietAddScheduleDate.setText(date)
            }
            datePicker.show(supportFragmentManager, "DatePickerBottomSheet")
        }

        binding.tietAddScheduleTime.setOnClickListener {
            val timePicker = TimePickerBottomSheetFragment { selectedTime ->
                binding.tietAddScheduleTime.setText(selectedTime)
            }
            timePicker.show(supportFragmentManager, "TimePickerBottomSheet")
        }

        binding.tietAddScheduleProject.setOnClickListener {
            binding.rvAddScheduleProjectList.visibility = View.VISIBLE
            lifecycleScope.launch { setProjectList() }
        }

    }

    private suspend fun setProjectList() {
        val bearerAccessToken = LogmeetApplication.getInstance().getDataStore().bearerAccessToken.first()
        RetrofitClient.project_instance.getProjectList(
            bearerAccessToken
        ).enqueue(object : Callback<BaseResponseListProjectListResult> {
            override fun onResponse(
                p0: Call<BaseResponseListProjectListResult>,
                p1: Response<BaseResponseListProjectListResult>
            ) {
                when (p1.code()) {
                    200 -> {
                        val resp = p1.body()?.result
                        Log.d(NETWORK, "addSchedule - setProjectList() : 성공\n$resp")
                        if (resp != null) {
                            (projectList as ArrayList<ProjectListResult>).addAll(resp.toList())
                            setProjectListRV(projectList)
                        } else {
                            projectList = emptyList()
                        }
                    }

                    else -> {
                        Log.d(NETWORK, "addSchedule - setProjectList() : 실패")
                    }
                }
            }

            override fun onFailure(p0: Call<BaseResponseListProjectListResult>, p1: Throwable) {
                Log.d(NETWORK, "addSchedule - setProjectList() : 실패\nbecause $p1")
            }

        })
    }

    private fun setProjectListRV(projectList: List<ProjectListResult>) {
        projectSelectionAdapter = ProjectSelectionAdapter(projectList, this)
        binding.rvAddScheduleProjectList.adapter = projectSelectionAdapter
        binding.rvAddScheduleProjectList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }


    private fun checkBtnAvailable() {
        val btnDone = binding.tvAddScheduleDone
        if (isNameNotNull) {
            btnDone.setBackgroundResource(R.drawable.btn_blue_8px)
            btnDone.setOnClickListener {
                lifecycleScope.launch { createSchedule() }
            }
        } else {
            btnDone.setBackgroundResource(R.drawable.btn_gray_8px)
            btnDone.setOnClickListener { }
        }
    }

    private suspend fun createSchedule() {
        val content = binding.tietAddScheduleName.text
        val beforeDate = binding.tietAddScheduleDate.text
        val date = formatDateForServer(beforeDate.toString())
        val time = binding.tietAddScheduleTime.text

        val bearerAccessToken = LogmeetApplication.getInstance().getDataStore().bearerAccessToken.first()

        RetrofitClient.schedule_instance.createSchedule(
            authorization = bearerAccessToken,
            scheduleCreateRequest = ScheduleCreateRequest(
                scheduleContent = content.toString(),
                scheduleDate = "${date}T${time}",
                projectId = this.projectId
            )
        ).enqueue(object : Callback<ScheduleCreateResponse> {
            override fun onResponse(
                p0: Call<ScheduleCreateResponse>,
                p1: Response<ScheduleCreateResponse>
            ) {
                when (p1.code()) {
                    200 -> {
                        Log.d(NETWORK, "addSchedule - createSchedule() : 성공\n")
                        setResult(Activity.RESULT_OK)
                        finish()
                    }

                    else -> {
                        Log.d(NETWORK, "addSchedule - createSchedule() : 실패")
                    }
                }
            }

            override fun onFailure(p0: Call<ScheduleCreateResponse>, p1: Throwable) {
                Log.d(NETWORK, "addSchedule - createSchedule() : 실패\nbecause $p1")
            }

        })
    }

    override fun onProjectClick(
        projectId: Int,
        projectName: String,
        projectColor: String
    ) {
        binding.tietAddScheduleProject.setText("")
        binding.tietAddScheduleProject.hint = ""
        binding.clAddScheduleProject.visibility = View.VISIBLE
        binding.tvAddScheduleProjectName.text = projectName
        val number = projectColor.split("_")[1].toInt()
        val color = ProjectDrawableResources.colorList[number - 1]
        binding.vAddScheduleProjectColor.setBackgroundResource(color)
        binding.rvAddScheduleProjectList.visibility = View.GONE

        this.projectId = projectId
    }
}