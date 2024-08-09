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
import com.example.logmeet.R
import com.example.logmeet.databinding.ActivityMakeProjectBinding
import com.example.logmeet.ui.home.MainHomeActivity

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

        radioColors.forEach { selected ->
            selected.setOnClickListener {
                radioColors.forEach { prjColor ->
                    prjColor.isChecked = selected == prjColor
                }
                color = selected.text.toString()
            }
        }
        for (i in 1..radioColors.size) {
            radioColors[i-1].setOnClickListener {
                radioColors.forEach { prjColor ->
                    prjColor.isChecked = radioColors[i-1] == prjColor
                }
                color = i.toString()
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
                val intent = Intent(this, MainHomeActivity::class.java)
                startActivity(intent)
            }
        } else {
            binding.tvMakePDone.setBackgroundResource(R.drawable.btn_gray_8px)
            binding.tvMakePDone.setOnClickListener(null)
        }
    }
}