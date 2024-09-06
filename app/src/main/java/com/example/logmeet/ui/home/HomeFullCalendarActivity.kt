package com.example.logmeet.ui.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.logmeet.R
import com.example.logmeet.databinding.ActivityHomeFullCalendarBinding
import com.example.logmeet.ui.component.MonthlyCalendar

class HomeFullCalendarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeFullCalendarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeFullCalendarBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.compHomeFullCalendar.setContent {
            MonthlyCalendar {

            }
        }
    }

    @Preview
    @Composable
    fun Preview() {
        MonthlyCalendar {

        }
    }
}