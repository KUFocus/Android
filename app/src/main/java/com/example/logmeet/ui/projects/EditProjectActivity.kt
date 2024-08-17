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
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.logmeet.R
import com.example.logmeet.databinding.ActivityEditProjectBinding
import com.example.logmeet.ui.home.MainHomeActivity

class EditProjectActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProjectBinding
    private var isNamed = false
    private var isExplained = false
    private var isColorChange = false
    private var beforeName = ""
    private var beforeExplain = ""
    private var beforeColor = ""
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

        getContent()
        init()
    }

    private fun init() {
        binding.ivEditPBack.setOnClickListener { finish() }
        setupTextWatchers()
        setupClearBtns()
        setupColorRadioBtn()
        updateButtonState()
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
    private fun getContent() {
        //api로 정보 가져오기
        beforeName = "로그밋프로젝트"
        beforeExplain = "2024 졸업프로젝트로 진행하는 팀플"
        beforeColor = "3"

        binding.tietEditPName.setText(beforeName)
        binding.tvEditPTextLength.text = "${beforeName.length} / 8"
        binding.tietEditPExplain.setText(beforeExplain)
        binding.tietEditPDate.setText("yyyy.MM.dd")
        color = beforeColor
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