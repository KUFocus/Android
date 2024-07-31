package com.example.logmeet.ui.join

import android.annotation.SuppressLint
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
import com.example.logmeet.databinding.ActivityJoin4Binding

class Join4Activity : AppCompatActivity() {
    private lateinit var binding: ActivityJoin4Binding
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

        val tvName = binding.tietJoin4UserName
        val btnNameClear = binding.ivJoin4UserNameClear
        val tvLength = binding.tvJoin4TextLength
        val btnNext = binding.tvJoin4Next

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
                    btnNext.setOnClickListener {  }
                } else {
                    btnNext.setBackgroundResource(R.drawable.btn_blue_8px)
                    btnNext.setOnClickListener {
                        val intent = Intent(this@Join4Activity, Join4Activity::class.java)
                        startActivity(intent)
                    }
                }
            }

        })
    }
}