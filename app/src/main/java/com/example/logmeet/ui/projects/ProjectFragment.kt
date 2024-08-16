package com.example.logmeet.ui.projects

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.logmeet.R
import com.example.logmeet.databinding.FragmentProjectBinding

class ProjectFragment : Fragment() {
    private lateinit var binding: FragmentProjectBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProjectBinding.inflate(layoutInflater, container, false)

        init()

        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    private fun init() {
        val tabList = arrayListOf(
            arrayOf(binding.btnProjectTabAll, binding.tvProjectTabAll, binding.vProjectTabAll),
            arrayOf(binding.btnProjectTabBookmark, binding.tvProjectTabBookmark, binding.vProjectTabBookmark)
        )

        for (index in 0..1) {
            tabList[index][0].setOnClickListener {
                (tabList[index][1] as TextView).setTextColor(ContextCompat.getColor(requireContext(), R.color.main_blue))
                (tabList[index][1] as TextView).setTypeface((tabList[index][1] as TextView).typeface, Typeface.BOLD)
                tabList[index][2].setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.main_blue))
                val index2 = if (index==0) 1 else 0
                (tabList[index2][1] as TextView).setTextColor(ContextCompat.getColor(requireContext(), R.color.gray500))
                (tabList[index2][1] as TextView).setTypeface((tabList[index2][1] as TextView).typeface, Typeface.NORMAL)
                tabList[index2][2].setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray200))
            }
        }
        //프로젝트 정보 얻어오는 api
    }
}