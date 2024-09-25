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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.logmeet.NETWORK
import com.example.logmeet.R
import com.example.logmeet.data.dto.project.api_response.BaseResponseProjectCreateResponse
import com.example.logmeet.data.dto.project.api_reqeust.ProjectCreateRequest
import com.example.logmeet.data.dto.project.api_response.ProjectCreateResponse
import com.example.logmeet.databinding.ActivityMakeProjectBinding
import com.example.logmeet.network.RetrofitClient
import com.example.logmeet.tag
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MakeProjectActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMakeProjectBinding
    private var isNamed = false
    private var isExplained = false
    private var name = ""
    private var explain = ""
    private var color = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMakeProjectBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupTextWatchers()
        setupClearBtns()
        setupColorRadioBtn()
        updateButtonState()

        binding.ivMakePClose.setOnClickListener { finish() }

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
            radioColors[i-1].setOnClickListener {
                radioColors.forEach { prjColor ->
                    prjColor.isChecked = radioColors[i-1] == prjColor
                }
                color = i.toString()
                Log.d(tag, "MakeProject - color선택 = $color")
            }
        }
    }

    private fun setupClearBtns() {
        binding.ivMakePNameClear.setOnClickListener { binding.tietMakePName.text?.clear() }
        binding.ivMakePExplainClear.setOnClickListener { binding.tietMakePExplain.text?.clear() }
    }

    private fun setupTextWatchers() {
        binding.tietMakePName.addTextChangedListener(createTextWatcher(
            onTextChanged = { s -> updateNameField(s) },
            afterTextChanged = { s ->
                if (s != null) {
                    updateButtonState(s.isNotEmpty(), "name")
                    name = s.toString()
                }
            }
        ))

        binding.tietMakePExplain.addTextChangedListener(createTextWatcher(
            onTextChanged = { s ->
                toggleClearButtonVisibility(
                    s,
                    binding.ivMakePExplainClear
                )
            },
            afterTextChanged = { s ->
                if (s != null) {
                    updateButtonState(s.isNotEmpty(), "explain")
                    explain = s.toString()
                }
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
        toggleClearButtonVisibility(text, binding.ivMakePNameClear)
        binding.tvMakePTextLength.text = "${text?.length ?: 0} / 8"
    }

    private fun toggleClearButtonVisibility(text: CharSequence?, clearButton: View) {
        clearButton.visibility = if (text.isNullOrEmpty()) View.GONE else View.VISIBLE
    }

    private fun updateButtonState(isValid: Boolean, flag: String) {
        if (isValid) {
            if (flag == "name") isNamed = true
            else if (flag == "explain") isExplained = true
        } else {
            if (flag == "name") isNamed = false
            else if (flag == "explain") isExplained = false
        }
        updateButtonState()
    }

    private fun updateButtonState() {
        if (isNamed && isExplained) {
            binding.tvMakePDone.setBackgroundResource(R.drawable.btn_blue_8px)
            binding.tvMakePDone.setOnClickListener {
                Log.d("chrin", "name : $name, explain : $explain, color : $color")
                makeNewPrj()
            }
        } else {
            binding.tvMakePDone.setBackgroundResource(R.drawable.btn_gray_8px)
            binding.tvMakePDone.setOnClickListener(null)
        }
    }

    private fun makeNewPrj() {
        Log.d(tag, "makeNewPrj: 함수 안 color : PROJECT_$color")
        RetrofitClient.project_instance.projectCreate(
            projectCreateRequest = ProjectCreateRequest(
                name = name,
                content = explain,
                color = "PROJECT_$color"
            )
        ).enqueue(object : Callback<BaseResponseProjectCreateResponse> {
            override fun onResponse(p0: Call<BaseResponseProjectCreateResponse>, p1: Response<BaseResponseProjectCreateResponse>) {
                if (p1.isSuccessful) {
                    val resp = requireNotNull(p1.body()?.result)
                    Log.d(NETWORK, "makeProject - makeNewPrj() : 성공\nresp = ${resp.projectId}")
                    val intent = Intent(this@MakeProjectActivity, ProjectHomeActivity::class.java)
                    intent.putExtra("projectId", resp.projectId)
                    startActivity(intent)
                } else {
                    Log.d(NETWORK, "makeProject - makeNewPrj() : 실패")
                }
            }

            override fun onFailure(p0: Call<BaseResponseProjectCreateResponse>, p1: Throwable) {
                Log.d(NETWORK, "makeProject - makeNewPrj()실패\nbecause : $p1")
            }
        })
    }
}