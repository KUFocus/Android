package com.example.logmeet.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.logmeet.R
import com.example.logmeet.databinding.ActivityLogin1Binding

class Login1Activity : AppCompatActivity() {
    private lateinit var binding: ActivityLogin1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLogin1Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.ivLogin1Back.setOnClickListener { finish() }

        binding.tvLogin1Login.setOnClickListener {
            val intent = Intent(this, Login2Activity::class.java)
            startActivity(intent)
        }

        binding.tvJoin1Kakao.setOnClickListener {
            //카카오로그인
        }
    }
}