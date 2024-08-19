package com.example.logmeet

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.logmeet.databinding.ActivityMainBinding
import com.example.logmeet.ui.home.MainHomeActivity
import com.example.logmeet.ui.join.Join1Activity
import com.example.logmeet.ui.login.Login1Activity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.tvStartLogin.setOnClickListener {
            val intent = Intent(this, Login1Activity::class.java)
            startActivity(intent)
        }
        binding.tvStartJoin.setOnClickListener {
            val intent = Intent(this, Join1Activity::class.java)
            startActivity(intent)
        }

    }
}