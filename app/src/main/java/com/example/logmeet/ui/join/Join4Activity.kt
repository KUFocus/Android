package com.example.logmeet.ui.join

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
import com.example.logmeet.data.dto.auth.RequestSignup
import com.example.logmeet.data.dto.auth.ResponseSignup
import com.example.logmeet.databinding.ActivityJoin4Binding
import com.example.logmeet.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Join4Activity : AppCompatActivity() {
    private lateinit var binding: ActivityJoin4Binding
    val tvName = binding.tietJoin4UserName
    val btnNameClear = binding.ivJoin4UserNameClear
    val tvLength = binding.tvJoin4TextLength
    val btnNext = binding.tvJoin4Next

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityJoin4Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.ivJoin4Back.setOnClickListener { finish() }
        btnNameClear.setOnClickListener { tvName.setText("") }

        tvName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            @SuppressLint("SetTextI18n")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    btnNameClear.visibility = View.GONE
                    tvLength.text = "0 / 10"
                } else {
                    btnNameClear.visibility = View.VISIBLE
                    tvLength.text = "${s.length} / 10"
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    btnNext.setBackgroundResource(R.drawable.btn_gray_8px)
                    btnNext.setOnClickListener { }
                } else {
                    btnNext.setBackgroundResource(R.drawable.btn_blue_8px)
                    btnNext.setOnClickListener {
                        val email = intent.getStringExtra("email")
                        val password = intent.getStringExtra("password")

                        if (email != null && password != null) {
                            val userInfo = RequestSignup(
                                email = email,
                                password = password,
                                userName = s.toString()
                            )
                            signup(userInfo)
                        } else {
                            Log.d("chrin-api", "Join4 - user 정보에 null 값이 포함되어있습니다.")
                        }
                    }
                }
            }

        })
    }

    private fun signup(userInfo: RequestSignup) {
        RetrofitClient.authInstance.signup(
            signup = userInfo
        ).enqueue(object : Callback<ResponseSignup> {
            override fun onResponse(p0: Call<ResponseSignup>, p1: Response<ResponseSignup>) {
                if (p1.isSuccessful) {
                    Log.d(NETWORK, "Join4 - signup() : 성공")
                    val intent = Intent(this@Join4Activity, Join5Activity::class.java)
                    startActivity(intent)
                } else {
                    Log.d(NETWORK, "Join4 - signup() : 실패")
                }
            }

            override fun onFailure(p0: Call<ResponseSignup>, p1: Throwable) {
                Log.d(NETWORK, "Join4 - signup()\nbecause : $p1")
            }
        })
    }
}