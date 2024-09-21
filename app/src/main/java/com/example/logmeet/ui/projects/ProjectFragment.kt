package com.example.logmeet.ui.projects

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Rect
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.logmeet.R
import com.example.logmeet.entity.ProjectData
import com.example.logmeet.databinding.FragmentProjectBinding

class ProjectFragment : Fragment() {
    private lateinit var binding: FragmentProjectBinding
    private var tabNum = 0
    private lateinit var projectAdapter: ProjectPrjAdapter
    private var allProjectList: ArrayList<ProjectData> = arrayListOf()
    private var bookmarkProjectList: ArrayList<ProjectData> = arrayListOf()

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
        setProjectTab()
        getAllProjectList()
        initSetProjectRV(allProjectList)

        binding.ivProjectAddBtn.setOnClickListener {
            val intent = Intent(requireContext(), MakeProjectActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initSetProjectRV(projectList: ArrayList<ProjectData>) {
        checkListEmpty(projectList.size)
        val projectRV = binding.rvProjectProjectList
        val spanCount = 2
        val spacing = 18
        projectAdapter = ProjectPrjAdapter(projectList)
        projectRV.adapter = projectAdapter
        projectRV.layoutManager =
            GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
        projectRV.addItemDecoration(
            GridSpacingItemDecoration(
                spanCount,
                spacing
            )
        )
    }

    private fun checkListEmpty(size: Int) {
        val isEmpty = size == 0
        binding.clProjectNoneProject.visibility = if (isEmpty && tabNum == 0) View.VISIBLE else View.GONE
        binding.tvProjectNoneBookmark.visibility = if (isEmpty && tabNum != 0) View.VISIBLE else View.GONE
    }

    private fun setProjectRV(projectList: ArrayList<ProjectData>) {
        checkListEmpty(projectList.size)
        val projectRV = binding.rvProjectProjectList
        projectAdapter = ProjectPrjAdapter(projectList)
        projectRV.adapter = projectAdapter
        projectRV.layoutManager =
            GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
    }


    class GridSpacingItemDecoration(private val spanCount: Int, private val spacing: Int) :
        RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = (view.layoutParams as GridLayoutManager.LayoutParams).spanIndex
            val column = position % spanCount

            if (column == 0) {
                outRect.right = spacing
            } else {
                outRect.left = spacing
            }
        }
    }


    private fun getAllProjectList() {
        allProjectList = arrayListOf()
        bookmarkProjectList = arrayListOf()
        allProjectList.addAll(
            arrayListOf(
                ProjectData("3",0,"졸업프로젝트","2024.04.10", "24", false),
                ProjectData("4",1,"졸업프로젝트","2024.04.10", "24", false),
                ProjectData("7",2,"졸업프로젝트","2024.04.10", "24", true),
                ProjectData("9",3,"졸업프로젝트","2024.04.10", "24", false),
                ProjectData("11",4,"졸업프로젝트","2024.04.10", "24", false),
            )
        )
        allProjectList.forEach {
            if (it.bookmark) bookmarkProjectList.add(it)
        }
    }

    private fun setProjectTab() {
        val tabList = arrayListOf(
            arrayOf(
                binding.btnProjectTabAll,
                binding.tvProjectTabAll,
                binding.vProjectTabAll
            ), arrayOf(
                binding.btnProjectTabBookmark,
                binding.tvProjectTabBookmark,
                binding.vProjectTabBookmark
            )
        )

        for (index in 0..1) {
            tabList[index][0].setOnClickListener {
                val selectedTextView = tabList[index][1] as TextView
                val selectedView = tabList[index][2] as View

                val unselectedIndex = if (index == 0) 1 else 0
                val unselectedTextView = tabList[unselectedIndex][1] as TextView
                val unselectedView = tabList[unselectedIndex][2] as View

                applyTabStyle(selectedTextView, selectedView, R.color.main_blue, Typeface.BOLD)
                applyTabStyle(unselectedTextView, unselectedView, R.color.gray500, Typeface.NORMAL)

                tabNum = index
                changeListMode(tabNum)
            }
        }
    }

    private fun applyTabStyle(
        textView: TextView,
        view: View,
        textColorRes: Int,
        typefaceStyle: Int
    ) {
        textView.setTextColor(ContextCompat.getColor(requireContext(), textColorRes))
        textView.setTypeface(textView.typeface, typefaceStyle)
        view.setBackgroundColor(ContextCompat.getColor(requireContext(), textColorRes))
    }

    private fun changeListMode(index: Int) {
        getAllProjectList()
        val list = if (index == 0) allProjectList else bookmarkProjectList
        setProjectRV(list)
    }

}