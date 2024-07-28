package com.example.logmeet

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.logmeet.databinding.ActivityJoin2Binding

class Join2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityJoin2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityJoin2Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.ivJoin2Close.setOnClickListener { finish() }

        binding.tietJoin2Email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    binding.ivJoin2EmailClear.visibility = View.GONE
                } else {
                    binding.ivJoin2EmailClear.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    binding.tvJoin2Send.setBackgroundResource(R.drawable.btn_gray_8px)
                    activteSend(false, s)
                }
                else {
                    binding.tvJoin2Send.setBackgroundResource(R.drawable.btn_blue_8px)
                    activteSend(true, s)
                }
            }

        })

        binding.ivJoin2EmailClear.setOnClickListener { binding.tietJoin2Email.setText("") }
        binding.ivJoin2CodeClear.setOnClickListener { binding.tietJoin2Code.setText("") }
    }

    private fun activteSend(b: Boolean, s: Editable?) {
        if (s != null) {
            if ("@" in s) {

            } else {

            }
                if (b) binding.tvJoin2Send.setOnClickListener {
                    //메일보내기
                    binding.tvJoin2Send.visibility = View.GONE
                    binding.clCode.visibility = View.VISIBLE
                } else {
                    binding.tvJoin2Send.setOnClickListener { //클릭 비활성화 }
                    }
                }
        }
    }
}