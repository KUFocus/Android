package com.example.logmeet.ui.join

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.logmeet.databinding.ActivityJoin1Binding

class Join1Activity : AppCompatActivity() {
    private lateinit var binding : ActivityJoin1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityJoin1Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        binding.ivJoin1Close.setOnClickListener {
            finish()
        }

        binding.tvJoin1LogmeetBtn.setOnClickListener {
            val intent = Intent(this, Join2Activity::class.java)
            startActivity(intent)
        }

        binding.tvJoin1KakaoBtn.setOnClickListener {  }
    }
}