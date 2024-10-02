package com.example.logmeet.ui.login

import android.content.Context
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
import androidx.lifecycle.lifecycleScope
import com.example.logmeet.NETWORK
import com.example.logmeet.R
import com.example.logmeet.data.dto.auth.api_request.AuthLoginRequest
import com.example.logmeet.data.dto.auth.api_response.BaseResponseAuthLoginResponse
import com.example.logmeet.databinding.ActivityLogin2Binding
import com.example.logmeet.network.RetrofitClient
import com.example.logmeet.ui.application.LogmeetApplication
import com.example.logmeet.ui.home.MainHomeActivity
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        tvEmail.transformationMethod = null
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
                    login(tvEmail.text.toString(), tvPwd.text.toString())
                }
            } else {
                btnLogin.setBackgroundResource(R.drawable.btn_gray_8px)
                btnLogin.setOnClickListener { }
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
                isPwdTyped = !s.isNullOrEmpty()
                checkAvailable()
            }

        })
    }

    private fun login(email: String, password: String) {
        RetrofitClient.auth_instance.login(
            login = AuthLoginRequest(
                email = email,
                password = password
            )
        ).enqueue(object : Callback<BaseResponseAuthLoginResponse> {
            override fun onResponse(
                p0: Call<BaseResponseAuthLoginResponse>,
                p1: Response<BaseResponseAuthLoginResponse>
            ) {
                if (p1.isSuccessful) {
                    val resp = requireNotNull(p1.body()?.result)
                    Log.d(
                        NETWORK,
                        "login2 - login() : 성공\nresp = ${resp.accessToken}, ${resp.refreshToken}"
                    )

                    lifecycleScope.launch {
                        LogmeetApplication.getInstance().getDataStore().also {
                            it.setAccessToken(resp.accessToken)
                            it.setRefreshToken(resp.refreshToken)
                        }
                    }
                    val intent = Intent(this@Login2Activity, MainHomeActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    startActivity(intent)
                } else {
                    Log.d(NETWORK, "login2 - login() : 실패")
                }
            }

            override fun onFailure(p0: Call<BaseResponseAuthLoginResponse>, p1: Throwable) {
                Log.d(NETWORK, "login2 - login()실패\nbecause : $p1")
            }

        })
    }
}