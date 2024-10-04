package com.example.logmeet.ui.projects

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
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
import com.example.logmeet.data.dto.project.UserProjectDto
import com.example.logmeet.data.dto.project.api_response.BaseResponseProjectInfoResult
import com.example.logmeet.domain.entity.PeopleData
import com.example.logmeet.databinding.ActivityEditProjectBinding
import com.example.logmeet.domain.entity.ProjectDrawableResources
import com.example.logmeet.network.RetrofitClient
import com.example.logmeet.tag
import com.example.logmeet.ui.application.LogmeetApplication
import com.example.logmeet.ui.home.MainHomeActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProjectActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProjectBinding
    private lateinit var peopleEditAdapter: PeopleEditAdapter
    private var peopleList: ArrayList<UserProjectDto> = arrayListOf()
    private var isNamed = false
    private var isExplained = false
    private var isColorChange = false
    private var beforeName = "-1"
    private var beforeExplain = "-1"
    private var beforeColor = "1"
    private var name = ""
    private var explain = ""
    private var color = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEditProjectBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {
            getContent()
        }
        init()
    }

    private fun init() {
        binding.ivEditPBack.setOnClickListener { finish() }
        setupTextWatchers()
        setupClearBtns()
        updateButtonState()
    }

    private fun setPeopleRV() {
        val peopleRV = binding.rvEditPPeopleList
        val sortedList = sortPeopleList(peopleList)
        peopleEditAdapter = PeopleEditAdapter(sortedList)
        peopleRV.adapter = peopleEditAdapter
        peopleRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun sortPeopleList(peopleList: ArrayList<UserProjectDto>): ArrayList<UserProjectDto> {
        return ArrayList(
            peopleList
                .sortedWith(
                    compareByDescending<UserProjectDto> { it.role }
                        .thenBy { it.userName }
                )
        )
    }

    private fun setupColorRadioBtn() {
        val radioColors = arrayOf(
            binding.p1,
            binding.p2,
            binding.p3,
            binding.p4,
            binding.p5,
            binding.p6,
            binding.p7,
            binding.p8,
            binding.p9,
            binding.p10,
            binding.p11,
            binding.p12
        )

        for (i in 1..radioColors.size) {
            if (i == color.toInt()) radioColors[i - 1].isChecked = true
            radioColors[i - 1].setOnClickListener {
                radioColors.forEach { prjColor ->
                    prjColor.isChecked = radioColors[i - 1] == prjColor
                }
                color = i.toString()
                val result = beforeColor == color
                updateButtonState(!result, "color")
            }
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
                    beforeName = resp.name
                    beforeExplain = resp.content
                    val dateTime = resp.createdAt.substring(0, 10)
                    binding.tietEditPDate.setText(dateTime)
                    peopleList.addAll(resp.userProjects)
                    setPeopleRV()

                    binding.tietEditPName.setText(beforeName)
                    binding.tvEditPTextLength.text = "${beforeName.length} / 8"
                    binding.tietEditPExplain.setText(beforeExplain)
                    beforeColor = resp.userProjects[0].color.split("_")[1]
                    color = beforeColor
                    setupColorRadioBtn()
                } else {
                    Log.d(NETWORK, "ProjectHome - getProjectDetail() : 실패")
                }
            }

            override fun onFailure(p0: Call<BaseResponseProjectInfoResult>, p1: Throwable) {
                Log.d(NETWORK, "ProjectHome - getProjectDetail()실패\nbecause : $p1")
            }

        })
//        binding.tietEditPName.setText(beforeName)
//        binding.tvEditPTextLength.text = "${beforeName.length} / 8"
//        binding.tietEditPExplain.setText(beforeExplain)
//        color = beforeColor
//
//        Log.d(tag, "22color = $color, before = $beforeColor")
    }

    private fun setupClearBtns() {
        binding.ivEditPNameClear.setOnClickListener { binding.tietEditPName.text?.clear() }
        binding.ivEditPExplainClear.setOnClickListener { binding.tietEditPExplain.text?.clear() }
    }

    private fun setupTextWatchers() {
        binding.tietEditPName.addTextChangedListener(createTextWatcher(
            onTextChanged = { s -> updateNameField(s) },
            afterTextChanged = { s ->
                val result = (s != null) and (s.toString() != beforeName)
                updateButtonState(result, "name")
                name = s.toString()
            }
        ))

        binding.tietEditPExplain.addTextChangedListener(createTextWatcher(
            onTextChanged = { s ->
                toggleClearButtonVisibility(
                    s,
                    binding.ivEditPExplainClear
                )
            },
            afterTextChanged = { s ->
                val result = (s != null) and (s.toString() != beforeExplain)
                updateButtonState(result, "explain")
                explain = s.toString()
            }
        ))
    }

    private fun createTextWatcher(
        onTextChanged: (CharSequence?) -> Unit,
        afterTextChanged: (Editable?) -> Unit
    ) = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged(s)
        }

        override fun afterTextChanged(s: Editable?) {
            afterTextChanged(s)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateNameField(text: CharSequence?) {
        toggleClearButtonVisibility(text, binding.ivEditPNameClear)
        binding.tvEditPTextLength.text = "${text?.length ?: 0} / 8"
    }

    private fun toggleClearButtonVisibility(text: CharSequence?, clearButton: View) {
        clearButton.visibility = if (text.isNullOrEmpty()) View.GONE else View.VISIBLE
    }

    private fun updateButtonState(isValid: Boolean, flag: String) {
        if (isValid) {
            if (flag == "name") isNamed = true
            else if (flag == "explain") isExplained = true
            else if (flag == "color") isColorChange = true
        } else {
            if (flag == "name") isNamed = false
            else if (flag == "explain") isExplained = false
            else if (flag == "color") isColorChange = false
        }
        updateButtonState()
    }

    @SuppressLint("ResourceAsColor")
    private fun updateButtonState() {
        if (isNamed or isExplained or isColorChange) {
            binding.tvEditPDone.setTextColor(ContextCompat.getColor(this, R.color.main_blue))
            binding.tvEditPDone.setOnClickListener {
                val intent = Intent(this, MainHomeActivity::class.java)
                startActivity(intent)
                //수정완료 api
            }
        } else {
            binding.tvEditPDone.setTextColor(ContextCompat.getColor(this, R.color.gray400))
            binding.tvEditPDone.setOnClickListener(null)
        }
    }
}