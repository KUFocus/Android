package com.example.logmeet.ui.home

import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.logmeet.R
import com.example.logmeet.databinding.ActivityMainHomeBinding
import com.example.logmeet.ui.MainFragmentStatePagerAdapter
import com.example.logmeet.ui.component.WeeklyCalendar
import com.google.android.material.tabs.TabLayoutMediator

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

//        configureBottomNavigation()
        val adapter = MainFragmentStatePagerAdapter(supportFragmentManager, 4)
        binding.vpAcMainFragPager.adapter = adapter

        // TabLayout과 ViewPager 연결
        TabLayoutMediator(binding.tlAcMainBottomMenu, binding.vpAcMainFragPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Home"
                1 -> "Projects"
                2 -> "Minutes"
                3 -> "My Page"
                else -> "Home"
            }
        }.attach()
    }

//    private fun configureBottomNavigation(){
//        binding.vpAcMainFragPager.adapter = MainFragmentStatePagerAdapter(supportFragmentManager, 5)
//
//        binding.tlAcMainBottomMenu.setupWithViewPager(binding.vpAcMainFragPager)
//
//        val bottomNaviLayout: View = this.layoutInflater.inflate(R.layout.bottom_navigation_tab, null, false)
//
//        binding.tlAcMainBottomMenu.getTabAt(0)!!.customView = bottomNaviLayout.findViewById<RelativeLayout>(R.id.btn_bottom_navi_home_tab)!!
//        binding.tlAcMainBottomMenu.getTabAt(1)!!.customView = bottomNaviLayout.findViewById<RelativeLayout>(R.id.btn_bottom_navi_search_tab)!!
//        binding.tlAcMainBottomMenu.getTabAt(2)!!.customView = bottomNaviLayout.findViewById<RelativeLayout>(R.id.btn_bottom_navi_add_tab)!!
//        binding.tlAcMainBottomMenu.getTabAt(3)!!.customView = bottomNaviLayout.findViewById<RelativeLayout>(R.id.btn_bottom_navi_like_tab)!!
//        binding.tlAcMainBottomMenu.getTabAt(4)!!.customView = bottomNaviLayout.findViewById<RelativeLayout>(R.id.btn_bottom_navi_my_page_tab)!!
//    }
}