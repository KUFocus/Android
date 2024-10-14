package com.example.logmeet.ui.projects

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.logmeet.R
import com.example.logmeet.databinding.FragmentProjectSidebarBinding

class ProjectSidebarFragment(
    val projectId: Int,
    val projectName: String,
    val isStar: Boolean,
    val projectHomeActivity: ProjectHomeActivity
) : Fragment(), View.OnClickListener {
//    private lateinit var binding: FragmentProjectSidebarBinding
    private var _binding: FragmentProjectSidebarBinding? =null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectSidebarBinding.inflate(inflater, container, false)

        binding.tvPrjSideName.text = "${projectName}입니다 :)"
        binding.ivPrjSideEmptyStar.visibility = if (isStar) View.GONE else View.VISIBLE
        binding.ivPrjSideFullStar.visibility = if (isStar) View.VISIBLE else View.GONE

        binding.clPrjSideStar.setOnClickListener(this)
        binding.clPrjSideNotice.setOnClickListener(this)
        binding.clPrjSideMinutes.setOnClickListener(this)
        binding.clPrjSideCalendar.setOnClickListener(this)
        binding.clPrjSideSetting.setOnClickListener(this)
        binding.clPrjSideInvite.setOnClickListener(this)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
    }

}