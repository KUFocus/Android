package com.example.logmeet.ui.projects

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.logmeet.R
import com.example.logmeet.databinding.FragmentProjectSidebarBinding

class ProjectSidebarFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentProjectSidebarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProjectSidebarBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

}