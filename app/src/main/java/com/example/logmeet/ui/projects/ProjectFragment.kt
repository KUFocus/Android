package com.example.logmeet.ui.projects

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Rect
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.logmeet.NETWORK
import com.example.logmeet.R
import com.example.logmeet.data.dto.project.ProjectListResult
import com.example.logmeet.data.dto.project.api_response.BaseResponseListProjectListResult
import com.example.logmeet.domain.entity.ProjectData
import com.example.logmeet.databinding.FragmentProjectBinding
import com.example.logmeet.network.RetrofitClient
import com.example.logmeet.ui.application.LogmeetApplication
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjectFragment : Fragment() {
    private lateinit var binding: FragmentProjectBinding
    private var tabNum = 0
    private lateinit var projectAdapter: ProjectPrjAdapter
    private lateinit var allProjectList: List<ProjectListResult>
    private lateinit var bookmarkProjectList: List<ProjectListResult>
    private var isProjectRVInitialized = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProjectBinding.inflate(layoutInflater, container, false)
        init()

        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {
            getAllProjectList()
        }
        setProjectRV(allProjectList)
    }

    private fun init() {
        setProjectTab()
        binding.ivProjectAddBtn.setOnClickListener {
            val intent = Intent(requireContext(), MakeProjectActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initProjectRV(projectList: List<ProjectListResult>) {
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
        isProjectRVInitialized = true
    }

    private fun checkListEmpty(size: Int) {
        val isEmpty = size == 0
        binding.clProjectNoneProject.visibility =
            if (isEmpty && tabNum == 0) View.VISIBLE else View.GONE
        binding.tvProjectNoneBookmark.visibility =
            if (isEmpty && tabNum != 0) View.VISIBLE else View.GONE
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setProjectRV(projectList: List<ProjectListResult>) {
        Log.d(tag, "setProjectRV: 실행됨 \nprojectList $projectList")
        checkListEmpty(projectList.size)
        if (!isProjectRVInitialized) {
            initProjectRV(allProjectList)
        }
        projectAdapter.data = projectList
        projectAdapter.notifyDataSetChanged()
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


    private suspend fun getAllProjectList() {
        allProjectList = arrayListOf()
        bookmarkProjectList = arrayListOf()
        val bearerAccessToken =
            LogmeetApplication.getInstance().getDataStore().bearerAccessToken.first()
        RetrofitClient.project_instance.getProjectList(
            bearerAccessToken
        ).enqueue(object : Callback<BaseResponseListProjectListResult> {
            override fun onResponse(
                p0: Call<BaseResponseListProjectListResult>,
                p1: Response<BaseResponseListProjectListResult>
            ) {
                when (p1.code()) {
                    200 -> {
                        val resp = p1.body()?.result
                        Log.d(NETWORK, "projectFragment - getAllProjectList() : 성공\n$resp")
                        if (resp != null) {
                            (allProjectList as ArrayList<ProjectListResult>).addAll(resp.toList())
                            setProjectRV(allProjectList)
                        } else {
                            allProjectList = emptyList()
                        }
                    }

                    else -> {
                        Log.d(NETWORK, "projectFragment - getAllProjectList() : 실패")
                    }
                }
            }

            override fun onFailure(p0: Call<BaseResponseListProjectListResult>, p1: Throwable) {
                Log.d(NETWORK, "projectFragment - getAllProjectList() : 실패\nbecause $p1")
            }

        })
    }

    private suspend fun getBookmarkList() {
        bookmarkProjectList = arrayListOf()
        val bearerAccessToken =
            LogmeetApplication.getInstance().getDataStore().bearerAccessToken.first()
        RetrofitClient.project_instance.getBookmarkList(
            bearerAccessToken
        ).enqueue(object : Callback<BaseResponseListProjectListResult> {
            override fun onResponse(
                p0: Call<BaseResponseListProjectListResult>,
                p1: Response<BaseResponseListProjectListResult>
            ) {
                when (p1.code()) {
                    200 -> {
                        val resp = p1.body()?.result
                        Log.d(NETWORK, "projectFragment - getBookmarkList() : 성공")
                        if (resp != null) {
                            (bookmarkProjectList as ArrayList<ProjectListResult>).addAll(resp.toList())
                            setProjectRV(bookmarkProjectList)
                        } else {
                            bookmarkProjectList = emptyList()
                        }
                    }

                    else -> {
                        Log.d(NETWORK, "projectFragment - getBookmarkList() : 실패")
                    }
                }
            }

            override fun onFailure(p0: Call<BaseResponseListProjectListResult>, p1: Throwable) {
                Log.d(NETWORK, "projectFragment - getAllProjectList() : 실패\nbecause $p1")
            }

        })
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
                val selectedView = tabList[index][2]

                val unselectedIndex = if (index == 0) 1 else 0
                val unselectedTextView = tabList[unselectedIndex][1] as TextView
                val unselectedView = tabList[unselectedIndex][2]

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
        if (index == 0) {
            lifecycleScope.launch { getAllProjectList() }
            setProjectRV(allProjectList)
        } else {
            lifecycleScope.launch { getBookmarkList() }
            setProjectRV(bookmarkProjectList)
        }
    }

}