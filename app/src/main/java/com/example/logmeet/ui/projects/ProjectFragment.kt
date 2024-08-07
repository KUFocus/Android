package com.example.logmeet.ui.projects

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.logmeet.R
import com.example.logmeet.databinding.FragmentProjectBinding

class ProjectFragment : Fragment() {
    private lateinit var binding: FragmentProjectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProjectBinding.inflate(layoutInflater, container, false)

        return binding.root
    }
}