package com.example.logmeet.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.logmeet.ui.home.HomeFragment
import com.example.logmeet.ui.minutes.MinutesFragment
import com.example.logmeet.ui.my.MyFragment
import com.example.logmeet.ui.projects.ProjectFragment

class MainFragmentStatePagerAdapter(fm : FragmentManager, val fragmentCount : Int) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> return HomeFragment()
            1 -> return ProjectFragment()
            2 -> return HomeFragment()
            3 -> return MinutesFragment()
            4 -> return MyFragment()
            else -> return HomeFragment()
        }
    }

    override fun getCount(): Int = fragmentCount // 자바에서는 { return fragmentCount }

}