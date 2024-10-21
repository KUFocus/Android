package com.example.logmeet.ui.schedule

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils.split
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.logmeet.NETWORK
import com.example.logmeet.R
import com.example.logmeet.data.dto.BaseResponseVoid
import com.example.logmeet.data.dto.project.ProjectListResult
import com.example.logmeet.data.dto.project.api_response.BaseResponseListProjectListResult
import com.example.logmeet.data.dto.schedule.ScheduleInfoResult
import com.example.logmeet.data.dto.schedule.api_request.ScheduleUpdateRequest
import com.example.logmeet.databinding.ActivityScheduleDetailBinding
import com.example.logmeet.domain.entity.ProjectDrawableResources
import com.example.logmeet.network.RetrofitClient
import com.example.logmeet.ui.application.LogmeetApplication
import com.example.logmeet.ui.showMinutesToast
import formatDateForFront
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitDateTime

class ScheduleDetailActivity() : AppCompatActivity(), ProjectSelectionAdapter.OnProjectClickListener {
    lateinit var binding: ActivityScheduleDetailBinding
    private var scheduleId = -1
    private var isTitle = false
    private var isDate = false
    private var isTime = false
    private var isProject = false
    private var oldName = ""
    private var oldDate = ""
    private var oldTime = ""
    private var oldProjectId = -1
    private lateinit var projectSelectionAdapter: ProjectSelectionAdapter
    private var projectList: List<ProjectListResult> = arrayListOf()
    private var projectId = -1
    

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityScheduleDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        scheduleId = intent.getIntExtra("scheduleId", -1)
        lifecycleScope.launch { getScheduleDetail() }

        val type = intent.getStringExtra("type")
        val title = binding.tvScheduleDetailTitle
        when (type) {
            "EDIT" -> {
                title.text = "일정 수정하기"
                binding.tvScheduleDetailEditDone.visibility = View.VISIBLE
                binding.clScheduleDetailDeleteBtn.visibility = View.GONE
                setTiet()
                setTietEnable(true)
            }
            "DETAIL" -> {
                title.text = "일정 상세"
                binding.tvScheduleDetailEditDone.visibility = View.GONE
                binding.clScheduleDetailDeleteBtn.visibility = View.VISIBLE
                binding.clScheduleDetailDeleteBtn.setOnClickListener {
                    lifecycleScope.launch { deleteSchedule() }
                }
                setTietEnable(false)
            }
            else -> {
                Log.d("chrin", "scheduleDetial - type이 잘못 설정됨")
            }
        }
    }
    
    private fun setTiet() {
        val btnNameClear = binding.ivScheduleDetailNameClear
        binding.tietScheduleDetailName.addTextChangedListener(object : TextWatcher {
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
                if (s.toString() == oldName || s.toString().isEmpty()) {
                    updateButtonState(false, "name")
                } else {
                    updateButtonState(true, "name")
                }
            }
        })

        binding.tietScheduleDetailDate.setOnClickListener {
            val datePicker = DatePickerBottomSheetFragment { selectedDate ->
                val date = formatDateForFront(selectedDate)
                binding.tietScheduleDetailDate.setText(date)
                if (date == oldDate) {
                    updateButtonState(false, "date")
                } else {
                    updateButtonState(true, "date")
                }
            }
            datePicker.show(supportFragmentManager, "DatePickerBottomSheet")
        }

        binding.tietScheduleDetailTime.setOnClickListener {
            val timePicker = TimePickerBottomSheetFragment { selectedTime ->
                binding.tietScheduleDetailTime.setText(selectedTime)
                if (selectedTime == oldTime) {
                    updateButtonState(false, "time")
                } else {
                    updateButtonState(true, "time")
                }
            }
            timePicker.show(supportFragmentManager, "TimePickerBottomSheet")
        }

        binding.tietScheduleDetailProject.setOnClickListener {
            binding.rvScheduleDetailProjectList.visibility = View.VISIBLE
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
                        Log.d(NETWORK, "scheduleDetail - setProjectList() : 성공\n$resp")
                        if (resp != null) {
                            (projectList as ArrayList<ProjectListResult>).addAll(resp.toList())
                            setProjectListRV(projectList)
                        } else {
                            projectList = emptyList()
                        }
                    }

                    else -> {
                        Log.d(NETWORK, "scheduleDetail - setProjectList() : 실패")
                    }
                }
            }

            override fun onFailure(p0: Call<BaseResponseListProjectListResult>, p1: Throwable) {
                Log.d(NETWORK, "scheduleDetail - setProjectList() : 실패\nbecause $p1")
            }

        })
    }

    private fun setProjectListRV(projectList: List<ProjectListResult>) {
        projectSelectionAdapter = ProjectSelectionAdapter(projectList, this)
        binding.rvScheduleDetailProjectList.adapter = projectSelectionAdapter
        binding.rvScheduleDetailProjectList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
    
    private fun setTietEnable(res: Boolean) {
        binding.tietScheduleDetailName.isClickable = res
        binding.tietScheduleDetailDate.isClickable = res
        binding.tietScheduleDetailTime.isClickable = res
        binding.tietScheduleDetailProject.isClickable = res
    }

    private fun updateButtonState(isValid: Boolean, flag: String) {
        if (flag == "name") isTitle = isValid
        else if (flag == "date") isDate = isValid
        else if (flag == "time") isTime = isValid
        else if (flag == "project") isProject = isValid
        updateButtonState()
    }

    @SuppressLint("ResourceAsColor")
    private fun updateButtonState() {
        if (isTitle or isDate or isTitle or isProject) {
            binding.tvScheduleDetailEditDone.setTextColor(ContextCompat.getColor(this, R.color.main_blue))
            binding.tvScheduleDetailEditDone.setOnClickListener {
                lifecycleScope.launch { updateScheduleDetail() }
            }
        } else {
            binding.tvScheduleDetailEditDone.setTextColor(ContextCompat.getColor(this, R.color.gray400))
            binding.tvScheduleDetailEditDone.setOnClickListener(null)
        }
    }

    private suspend fun getScheduleDetail() {
        val bearerAccessToken = LogmeetApplication.getInstance().getDataStore().bearerAccessToken.first()
        RetrofitClient.schedule_instance.getScheduleDetail(
            authorization = bearerAccessToken,
            scheduleId = scheduleId
        ).enqueue(object : Callback<ScheduleInfoResult> {
            override fun onResponse(
                p0: Call<ScheduleInfoResult>,
                p1: Response<ScheduleInfoResult>
            ) {
                when (p1.code()) {
                    200 -> {
                        Log.d(NETWORK, "scheduleDetail - getScheduleDetail() : 성공\n$p1")
                        var resp = p1.body()
                        if (resp != null) {
                            binding.tietScheduleDetailName.setText(resp.scheduleContent)
//                            var date = splitDateTime(resp.scheduleDate).first
//                            var time = splitDateTime(resp.scheduleDate).second
//                            binding.tietScheduleDetailDate.setText(date)
//                            binding.tietScheduleDetailTime.setText(time)

                            binding.clScheduleDetailProject.visibility = View.VISIBLE
                            binding.tvScheduleDetailProjectName.text = resp.projectName
                            val number = resp.color.split("_")[1].toInt()
                            val color = ProjectDrawableResources.colorList[number - 1]
                            binding.vScheduleDetailProjectColor.setBackgroundResource(color)
                            binding.rvScheduleDetailProjectList.visibility = View.GONE
                        }
                    }

                    else -> {
                        Log.d(NETWORK, "scheduleDetail - getScheduleDetail() : 실패")
                    }
                }
            }

            override fun onFailure(p0: Call<ScheduleInfoResult>, p1: Throwable) {
                Log.d(NETWORK, "scheduleDetail - getScheduleDetail() : 실패\nbecause $p1")
            }

        })
    }

    private fun setOldValue() {
        oldName = binding.tietScheduleDetailName.text.toString()
        oldDate = binding.tietScheduleDetailDate.text.toString()
        oldTime = binding.tietScheduleDetailDate.text.toString()
        oldProjectId = projectId

        binding.tvScheduleDetailTitle.text = "일정 상세"
        binding.tvScheduleDetailEditDone.visibility = View.GONE
        binding.clScheduleDetailDeleteBtn.visibility = View.VISIBLE
        binding.clScheduleDetailDeleteBtn.setOnClickListener {
            lifecycleScope.launch { deleteSchedule() }
        }
        setTietEnable(false)
    }

    private suspend fun updateScheduleDetail() {
        val bearerAccessToken = LogmeetApplication.getInstance().getDataStore().bearerAccessToken.first()
        val date = binding.tietScheduleDetailDate.text
        val time = binding.tietScheduleDetailTime.text
        RetrofitClient.schedule_instance.updateScheduleDetail(
            authorization = bearerAccessToken,
            scheduleId = scheduleId,
            scheduleUpdateRequest = ScheduleUpdateRequest(
                scheduleContent = binding.tvScheduleDetailNameTitle.text.toString(),
                scheduleDate = "${date}T${time}"
            )
        ).enqueue(object : Callback<BaseResponseVoid> {
            override fun onResponse(p0: Call<BaseResponseVoid>, p1: Response<BaseResponseVoid>) {
                when (p1.code()) {
                    200 -> {
                        val resp = p1.body()
                        Log.d(NETWORK, "scheduleDetail - updateScheduleDetail() : 성공\n$resp")
                        showMinutesToast(this@ScheduleDetailActivity, R.drawable.ic_check_circle, "스케줄이 수정되었습니다.")
                        setOldValue()
                    }

                    else -> {
                        Log.d(NETWORK, "scheduleDetail - updateScheduleDetail() : 실패")
                    }
                }
            }

            override fun onFailure(p0: Call<BaseResponseVoid>, p1: Throwable) {
                Log.d(NETWORK, "scheduleDetail - updateScheduleDetail() : 실패\nbecause $p1")
            }
        })
    }

    private suspend fun deleteSchedule() {
        val bearerAccessToken = LogmeetApplication.getInstance().getDataStore().bearerAccessToken.first()
        RetrofitClient.schedule_instance.deleteScheduleDetail(
            authorization = bearerAccessToken,
            scheduleId = scheduleId
        ).enqueue(object : Callback<BaseResponseVoid> {
            override fun onResponse(p0: Call<BaseResponseVoid>, p1: Response<BaseResponseVoid>) {
                when (p1.code()) {
                    200 -> {
                        val resp = p1.body()
                        Log.d(NETWORK, "scheduleDetail - deleteSchedule() : 성공\n$resp")
                        finish()
                    }

                    else -> {
                        Log.d(NETWORK, "scheduleDetail - deleteSchedule() : 실패")
                    }
                }
            }

            override fun onFailure(p0: Call<BaseResponseVoid>, p1: Throwable) {
                Log.d(NETWORK, "scheduleDetail - deleteSchedule() : 실패\nbecause $p1")
            }

        })
    }

    override fun onProjectClick(
        projectId: Int,
        projectName: String,
        projectColor: String
    ) {
        binding.tietScheduleDetailProject.setText("")
        binding.tietScheduleDetailProject.hint = ""
        binding.clScheduleDetailProject.visibility = View.VISIBLE
        binding.tvScheduleDetailProjectName.text = projectName
        val number = projectColor.split("_")[1].toInt()
        val color = ProjectDrawableResources.colorList[number - 1]
        binding.vScheduleDetailProjectColor.setBackgroundResource(color)
        binding.rvScheduleDetailProjectList.visibility = View.GONE

        this.projectId = projectId
        if (oldProjectId == projectId) {
            updateButtonState(true, "project")
        } else {
            updateButtonState(false, "project")
        }
    }
}