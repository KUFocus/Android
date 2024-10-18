package com.example.logmeet.ui.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.logmeet.R
import com.example.logmeet.databinding.ActivityMainHomeBinding
import com.example.logmeet.ui.minutes.CreateMinutesBottomSheetFragment
import com.example.logmeet.ui.minutes.MinutesFragment
import com.example.logmeet.ui.projects.ProjectFragment
import com.example.logmeet.ui.user.MyFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainHomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bottomNavigationView: BottomNavigationView = binding.tlAcMainBottomMenu
        replaceFragment(HomeFragment())

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.nav_projects -> {
                    replaceFragment(ProjectFragment())
                    true
                }
                R.id.nav_create -> {
                    val createMinutesDialog = CreateMinutesBottomSheetFragment()
                    createMinutesDialog.show(supportFragmentManager, "CreateMinutesDialog")
                    true
                }
                R.id.nav_minutes -> {
                    replaceFragment(MinutesFragment())
                    true
                }
                R.id.nav_my -> {
                    replaceFragment(MyFragment())
                    true
                }
                else -> false
            }
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.vp_ac_main_frag_pager, fragment)
            .commit()
    }
}