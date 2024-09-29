package com.example.logmeet.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.logmeet.ui.home.HomeFragment
import com.example.logmeet.ui.minutes.MinutesFragment
import com.example.logmeet.ui.user.MyFragment
import com.example.logmeet.ui.projects.ProjectFragment

class MainFragmentStatePagerAdapter(fm: FragmentManager, private val fragmentCount: Int) :
    FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> ProjectFragment()
            3 -> MinutesFragment()
            4 -> MyFragment()
            else -> HomeFragment()
        }
    }

    override fun getCount(): Int = fragmentCount // 자바에서는 { return fragmentCount }
}