package com.example.logmeet.ui.join

import android.content.Intent
import android.os.Bundle
import android.service.autofill.Validators.and
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.logmeet.MainActivity
import com.example.logmeet.R
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

        binding.ivJoin2Back.setOnClickListener { finish() }

        val tvEmail = binding.tietJoin2Email
        val btnEmailClear = binding.ivJoin2EmailClear
        val btnEmailSend = binding.tvJoin2Send
        val emailError = binding.clJoin2EmailError

        val tvCode = binding.tietJoin2Code
        val btnCodeClear = binding.ivJoin2CodeClear
        val codeError = binding.clJoin2CodeError
        val btnEmailResend = binding.tvJoin2Resend
        val btnCertify = binding.tvJoin2Certify

        val btnNext = binding.tvJoin2Next

        btnEmailClear.setOnClickListener { tvEmail.setText("") }
        btnCodeClear.setOnClickListener { tvCode.setText("") }

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
                if (s.isNullOrEmpty()) {
                    btnEmailSend.setBackgroundResource(R.drawable.btn_gray_8px)
                    btnEmailSend.setOnClickListener {  } //클릭 비활성화
                } else {
                    btnEmailSend.setBackgroundResource(R.drawable.btn_blue_8px)
                    btnEmailSend.setOnClickListener {
                        if (checkEmailForm(s)) {
                            emailError.visibility = View.GONE
                            btnEmailClear.visibility = View.GONE
                            //인증코드 메일보내기
                            btnEmailSend.visibility = View.GONE
                            binding.clCode.visibility = View.VISIBLE
                        } else emailError.visibility = View.VISIBLE
                    }
                }
            }
        })

        btnEmailResend.setOnClickListener {
            //인증코드 다시보내기
        }

        tvCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    btnCodeClear.visibility = View.GONE
                } else {
                    btnCodeClear.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    btnCertify.setBackgroundResource(R.drawable.btn_gray_8px)
                    btnEmailSend.setOnClickListener {  } //클릭 비활성화
                } else {
                    btnCertify.setBackgroundResource(R.drawable.btn_blue_8px)
                    btnCertify.setOnClickListener {
                        if (checkCodeForm(s)) {
                            codeError.visibility = View.GONE
                            btnCodeClear.visibility = View.GONE
                            btnEmailResend.visibility = View.GONE
                            btnCertify.visibility = View.GONE

                            binding.tvJoin2DoneMsg.visibility = View.VISIBLE

                            //인증하기 성공하면 다음 단계 버튼 생성
                            btnNext.visibility = View.VISIBLE
                            btnNext.setOnClickListener {
                                val intent = Intent(this@Join2Activity, Join3Activity::class.java)
                                intent.putExtra("email", tvEmail.text)
                                startActivity(intent)
                            }
                        } else codeError.visibility = View.VISIBLE
                    }
                }
            }

        })
    }

    private fun checkCodeForm(s: Editable): Boolean { //code 형식 검증
        return s.length == 6
    }

    private fun checkEmailForm(s: Editable): Boolean { //email 형식 검증
        return ("@" in s) and ("." in s)
    }
}