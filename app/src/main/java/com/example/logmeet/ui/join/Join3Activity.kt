package com.example.logmeet.ui.join

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.logmeet.R
import com.example.logmeet.databinding.ActivityJoin3Binding
import java.util.zip.Inflater

class Join3Activity : AppCompatActivity() {
    private lateinit var binding: ActivityJoin3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityJoin3Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.ivJoin3Back.setOnClickListener { finish() }

        val tvPwd = binding.tietJoin3Pwd
        val tvPwdCheck = binding.tietJoin3PwdCheck
        val btnPwdClear = binding.ivJoin3PwdClear
        val btnPwdCheckClear = binding.ivJoin3PwdCheckClear
        val pwdError = binding.clJoin3PwdError
        val pwdCheckError = binding.clJoin3PwdCheckError
        val btnNext = binding.tvJoin3Next

        var isPwdDone = false
        var isPwdCheckDone = false

        var pwd = ""

        fun checkAvailable() {
            if (isPwdDone and isPwdCheckDone) {
                btnNext.setBackgroundResource(R.drawable.btn_blue_8px)
                btnNext.setOnClickListener {
                    val intent = Intent(this@Join3Activity, Join4Activity::class.java)
                    //이메일, 비번 intent로 넘기기
                    startActivity(intent)
                }
            } else {
                btnNext.setBackgroundResource(R.drawable.btn_gray_8px)
                btnNext.setOnClickListener {  }
            }
        }

        btnPwdClear.setOnClickListener { tvPwd.setText("") }
        btnPwdCheckClear.setOnClickListener { tvPwdCheck.setText("") }

        tvPwd.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    btnPwdClear.visibility = View.GONE
                } else {
                    btnPwdClear.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    isPwdDone = false
                } else {
                    if ((s.length > 8) and (containsLetterAndDigit(s.toString()))) {
                        pwdError.visibility = View.GONE
                        isPwdDone = true
                        pwd = s.toString()
                    } else {
                        pwdError.visibility = View.VISIBLE
                        isPwdDone = false
                    }
                }
                checkAvailable()
            }

        })

        tvPwdCheck.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    btnPwdCheckClear.visibility = View.GONE
                } else {
                    btnPwdCheckClear.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    isPwdCheckDone = false
                } else {
                    if (s.toString() == pwd) {
                        pwdCheckError.visibility = View.GONE
                        isPwdCheckDone = true
                    } else {
                        pwdCheckError.visibility = View.VISIBLE
                        isPwdCheckDone = false
                    }
                }
                checkAvailable()
            }
        })
    }

    fun containsLetterAndDigit(input: String): Boolean {
        val regex = Regex("^(?=.*[a-zA-Z])(?=.*\\d).+$")
        return regex.containsMatchIn(input)
    }
}