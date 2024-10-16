package com.example.logmeet.ui.schedule

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.example.logmeet.R
import com.example.logmeet.databinding.ActivityAddScheduleBinding

class AddScheduleActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddScheduleBinding
    var isNameNotNull = false

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
        val btnNameClear = binding.ivAddScheduleNameClear

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

        binding.tietAddScheduleDate.setOnClickListener {  }

        binding.tietAddScheduleTime.setOnClickListener {  }

        binding.tietAddScheduleProject.setOnClickListener {  }

    }

    fun checkBtnAvailable() {
        val btnDone = binding.tvAddScheduleDone
        if (isNameNotNull) {
            btnDone.setBackgroundResource(R.drawable.btn_blue_8px)
            btnDone.setOnClickListener {
                //api 연결 및 화면 이동
            }
        } else {
            btnDone.setBackgroundResource(R.drawable.btn_gray_8px)
            btnDone.setOnClickListener { }
        }
    }
}