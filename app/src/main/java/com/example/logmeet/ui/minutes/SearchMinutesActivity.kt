package com.example.logmeet.ui.minutes

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.adapters.ViewGroupBindingAdapter
import com.example.logmeet.R
import com.example.logmeet.databinding.ActivitySearchMinutesBinding

class SearchMinutesActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchMinutesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySearchMinutesBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


    }
}