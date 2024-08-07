package com.example.logmeet.ui.minutes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.logmeet.R
import com.example.logmeet.databinding.FragmentMinutesBinding

class MinutesFragment : Fragment() {
    private lateinit var binding: FragmentMinutesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMinutesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}