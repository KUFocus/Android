package com.example.logmeet.ui.join

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.service.autofill.Validators.and
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.logmeet.MainActivity
import com.example.logmeet.R
import com.example.logmeet.databinding.ActivityJoin2Binding

class Join2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityJoin2Binding
    private var userEmail = ""
    private var verificationCode = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityJoin2Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(binding.root)
        setupWindowInsets()
        setupClickListeners()
        setupTextWatchers()
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupClickListeners() {
        binding.ivJoin2Back.setOnClickListener { finish() }

        binding.ivJoin2EmailClear.setOnClickListener { binding.tietJoin2Email.setText("") }
        binding.ivJoin2CodeClear.setOnClickListener { binding.tietJoin2Code.setText("") }

        binding.tvJoin2Send.setOnClickListener {
            binding.tietJoin2Email.clearFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.tietJoin2Email.windowToken, 0)
            sendCodeToUserEmail()
        }
        binding.tvJoin2Resend.setOnClickListener {
            sendCodeToUserEmail()
        }
    }

    private fun sendCodeToUserEmail() {
        verificationCode = (100000..999999).random()

    }


    private fun setupTextWatchers() {
        setupEmailTextWatcher()
        setupCodeTextWatcher()
    }

    private fun setupEmailTextWatcher() {
        binding.tietJoin2Email.addTextChangedListener(createTextWatcher(
            onTextChanged = { s ->
                toggleClearButton(s, binding.ivJoin2EmailClear)
            },
            afterTextChanged = { s ->
                toggleSendButton(s, binding.tvJoin2Send, binding.clJoin2EmailError) {
                    sendEmailVerification()
                }
                userEmail = s.toString()
            }
        ))
    }

    private fun setupCodeTextWatcher() {
        binding.tietJoin2Code.addTextChangedListener(createTextWatcher(
            onTextChanged = { s ->
                toggleClearButton(s, binding.ivJoin2CodeClear)
            },
            afterTextChanged = { s ->
                toggleSendButton(s, binding.tvJoin2Certify, binding.clJoin2CodeError) {
                    verifyCode()
                }
            }
        ))
    }

    private fun createTextWatcher(
        onTextChanged: (s: CharSequence?) -> Unit = {},
        afterTextChanged: (s: Editable?) -> Unit = {}
    ): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onTextChanged(s)
            }

            override fun afterTextChanged(s: Editable?) {
                afterTextChanged(s)
            }
        }
    }

    private fun toggleClearButton(s: CharSequence?, button: ImageView) {
        button.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
    }

    private fun toggleSendButton(
        s: Editable?,
        button: TextView,
        errorView: View,
        onValidAction: () -> Unit
    ) {
        if (s.isNullOrEmpty()) {
            button.setBackgroundResource(R.drawable.btn_gray_8px)
            button.setOnClickListener { } // 클릭 비활성화
        } else {
            button.setBackgroundResource(R.drawable.btn_blue_8px)
            button.setOnClickListener {
                if (checkForm(s)) {
                    errorView.visibility = View.GONE
                    onValidAction()
                } else {
                    errorView.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun sendEmailVerification() {
        binding.ivJoin2EmailClear.visibility = View.GONE
        binding.tvJoin2Send.visibility = View.GONE
        binding.clCode.visibility = View.VISIBLE
        hideKeyboard(binding.tvJoin2Send)
    }

    private fun verifyCode() {
        //코드 확인
        binding.clJoin2CodeError.visibility = View.VISIBLE

        binding.ivJoin2CodeClear.visibility = View.GONE
        binding.tvJoin2Resend.visibility = View.GONE
        binding.tvJoin2Certify.visibility = View.GONE
        binding.clJoin2CodeError.visibility = View.GONE
        binding.tvJoin2DoneMsg.visibility = View.VISIBLE
        binding.tvJoin2Next.visibility = View.VISIBLE
        hideKeyboard(binding.tvJoin2Certify)

        binding.tvJoin2Next.setOnClickListener {
            val intent = Intent(this@Join2Activity, Join3Activity::class.java)
            intent.putExtra("email", binding.tietJoin2Email.text.toString())
            startActivity(intent)
        }
    }

    private fun checkForm(s: Editable): Boolean {
        return when {
            binding.tietJoin2Email.text == s -> checkEmailForm(s)
            binding.tietJoin2Code.text == s -> checkCodeForm(s)
            else -> false
        }
    }

    private fun checkCodeForm(s: Editable): Boolean {
        return s.length == 6
    }

    private fun checkEmailForm(s: Editable): Boolean {
        return s.contains("@") && s.contains(".")
    }

    private fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
