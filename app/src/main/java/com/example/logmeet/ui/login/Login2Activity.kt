package com.example.logmeet.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.logmeet.MainActivity
import com.example.logmeet.R
import com.example.logmeet.databinding.ActivityLogin2Binding
import com.example.logmeet.ui.join.Join4Activity

class Login2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityLogin2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLogin2Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.ivLogin2Back.setOnClickListener { finish() }

        val tvEmail = binding.tietLogin2Email
        val btnEmailClear = binding.ivLogin2EmailClear
        val emailError = binding.clLogin2EmailError

        val tvPwd = binding.tietLogin2Pwd
        val btnPwdClear = binding.ivLogin2PwdClear
        val pwdError = binding.clLogin2PwdError
        
        val btnLogin = binding.tvLogin2LoginBtn
        
        var isEmailTyped = false
        var isPwdTyped = false
        
        btnEmailClear.setOnClickListener { tvEmail.setText("") }
        btnPwdClear.setOnClickListener { tvPwd.setText("") }

        fun checkAvailable() {
            if (isEmailTyped and isPwdTyped) {
                btnLogin.setBackgroundResource(R.drawable.btn_blue_8px)
                btnLogin.setOnClickListener {
//                    val intent = Intent(this@Login2Activity, Join4Activity::class.java)
//                    startActivity(intent)
                }
            } else {
                btnLogin.setBackgroundResource(R.drawable.btn_gray_8px)
                btnLogin.setOnClickListener {  }
            }
        }

        tvEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    btnEmailClear.visibility = View.GONE
                } else {
                    btnEmailClear.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {
                isEmailTyped = !s.isNullOrEmpty()
                checkAvailable()
            }

        })
        
        tvPwd.addTextChangedListener(object : TextWatcher{
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
                isPwdTyped = !s.isNullOrEmpty()
                checkAvailable()
            }

        })

        btnLogin.setOnClickListener {
            //서버 연결 및 로그인
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}