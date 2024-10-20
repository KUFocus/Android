package com.example.logmeet.ui.join

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.logmeet.R
import com.example.logmeet.databinding.ActivityJoin2Binding
import com.example.logmeet.tag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Properties
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

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

        binding.tvJoin2Resend.setOnClickListener {
            binding.tietJoin2Code.setText("")
            lifecycleScope.launch {
                sendCodeToUserEmail()
            }
        }
    }

    private suspend fun sendCodeToUserEmail() = withContext(Dispatchers.IO) {
        verificationCode = (100000..999999).random()
        try {
            val props = Properties()
            props["mail.smtp.host"] = "smtp.gmail.com"
            props["mail.smtp.port"] = "587"
            props["mail.smtp.auth"] = "true"
            props["mail.smtp.starttls.enable"] = "true"

            val session = Session.getInstance(props, object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication("rinring105@gmail.com", "gbmy yryz akxb fozw")
                }
            })

            val message = MimeMessage(session).apply {
                setFrom(InternetAddress("rinring105@gmail.com"))
                setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail))
                subject = "[Logmeet] 이메일 인증코드"
                setText("안녕하세요, 로그밋입니다.\n 인증코드는 다음과 같습니다. 앱에 인증코드 6자리를 입력해주세요. \n$verificationCode")
            }

            Transport.send(message)
        } catch (e: Exception) {
            e.printStackTrace()
        }
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
                    userEmail = s.toString()
                    sendEmailVerification()
                }
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
                    verifyCode(s.toString())
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
        binding.clJoin2EmailDone.visibility = View.VISIBLE
        binding.tietJoin2Email.clearFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.tietJoin2Email.windowToken, 0)
        binding.clJoin2EmailError.visibility = View.GONE
        lifecycleScope.launch {
            sendCodeToUserEmail()
            Log.d(tag, "setupClickListeners: 실행")
        }
        binding.ivJoin2EmailClear.visibility = View.GONE
        binding.tvJoin2Send.visibility = View.GONE
        binding.clCode.visibility = View.VISIBLE
        hideKeyboard(binding.tvJoin2Send)
    }

    private fun verifyCode(inputCode: String) {
        if (verificationCode.toString() == inputCode) {
            binding.ivJoin2CodeClear.visibility = View.GONE
            binding.tvJoin2Resend.visibility = View.GONE
            binding.tvJoin2Certify.visibility = View.GONE
            binding.clJoin2CodeError.visibility = View.GONE
            binding.ivJoin2EmailDone.visibility = View.GONE
            binding.tvJoin2DoneMsg.visibility = View.VISIBLE
            binding.tvJoin2Next.visibility = View.VISIBLE

            hideKeyboard(binding.tvJoin2Certify)
        } else {
            binding.clJoin2CodeError.visibility = View.VISIBLE
        }

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
