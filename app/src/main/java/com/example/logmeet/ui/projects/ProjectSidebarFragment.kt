package com.example.logmeet.ui.projects

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.logmeet.NETWORK
import com.example.logmeet.R
import com.example.logmeet.data.dto.BaseResponseVoid
import com.example.logmeet.data.dto.project.api_response.BaseResponseProjectBookmarkResult
import com.example.logmeet.databinding.FragmentProjectSidebarBinding
import com.example.logmeet.network.RetrofitClient
import com.example.logmeet.ui.application.LogmeetApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjectSidebarFragment(
    val projectId: Int,
    val projectName: String,
    val isStar: Boolean,
    val projectHomeActivity: ProjectHomeActivity
) : Fragment(), View.OnClickListener {
    private var _binding: FragmentProjectSidebarBinding? =null
    private val binding get() = _binding!!
    var isBookmark = isStar

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
        binding.clPrjSideExit.setOnClickListener(this)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.cl_prj_side_star -> {
                CoroutineScope(Dispatchers.IO).launch {
                    changeBookmark()
                }
                isBookmark = !isBookmark
                binding.ivPrjSideEmptyStar.visibility = if (isBookmark) View.GONE else View.VISIBLE
                binding.ivPrjSideFullStar.visibility = if (isBookmark) View.VISIBLE else View.GONE
            }
            R.id.cl_prj_side_notice -> {

            }
            R.id.cl_prj_side_minutes -> {
                val intent = Intent(projectHomeActivity, ProjectMinutesActivity::class.java)
                intent.putExtra("projectId", projectId)
                startActivity(intent)
            }
            R.id.cl_prj_side_calendar -> {
                val intent = Intent(projectHomeActivity, ProjectCalendarActivity::class.java)
                intent.putExtra("projectId", projectId)
                startActivity(intent)
            }
            R.id.cl_prj_side_setting -> {

            }
            R.id.cl_prj_side_invite -> {
                shareProjectLink(projectId.toString())
            }
            R.id.cl_prj_side_exit -> {
                CoroutineScope(Dispatchers.IO).launch {
                    leaveProject()
                }
            }
        }
    }

    private fun shareProjectLink(projectId: String) {
        val inviteLink = createInviteLink(projectId)
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "[Logmeet]\n초대링크가 도착했습니다!\n이 프로젝트에 참여해 보세요: $inviteLink")
        }
        startActivity(Intent.createChooser(intent, "초대 링크 공유"))
    }

    private fun createInviteLink(projectId: String): String {
        val appPackageName = "com.example.logmeet" // 본인의 패키지 이름으로 변경
        val appStoreUrl = "https://play.google.com/store/apps/details?id=$appPackageName"
        val deepLink = "https://example.com/join?projectId=$projectId"

        return "intent:$deepLink#Intent;package=$appPackageName;scheme=https;end"
    }

    private suspend fun leaveProject() {
        val bearerAccessToken =
            LogmeetApplication.getInstance().getDataStore().bearerAccessToken.first()
        RetrofitClient.project_instance.leaveProject(
            authorization = bearerAccessToken,
            projectId = projectId
        ).enqueue(object : Callback<BaseResponseVoid> {
            override fun onResponse(p0: Call<BaseResponseVoid>, p1: Response<BaseResponseVoid>) {
                when (p1.code()) {
                    200 -> {
                        val resp = p1.body()?.result
                        Log.d(NETWORK, "ProjectSidebar - leaveProject() : 성공\nprojectId : $projectId => $resp")
                    }
                    else -> Log.d(NETWORK, "ProjectSidebar - leaveProject() : 실패")
                }
            }

            override fun onFailure(p0: Call<BaseResponseVoid>, p1: Throwable) {
                Log.d(NETWORK, "ProjectSidebar - leaveProject() : 실패\nbecause $p1")
            }

        })
    }

    private suspend fun changeBookmark() {
        val bearerAccessToken =
            LogmeetApplication.getInstance().getDataStore().bearerAccessToken.first()
        RetrofitClient.project_instance.changeBookmark(
            authorization = bearerAccessToken,
            projectId = projectId
        ).enqueue(object : Callback<BaseResponseProjectBookmarkResult> {
            override fun onResponse(
                p0: Call<BaseResponseProjectBookmarkResult>,
                p1: Response<BaseResponseProjectBookmarkResult>
            ) {
                when (p1.code()) {
                    200 -> {
                        val resp = p1.body()?.result
                        Log.d(NETWORK, "ProjectSidebar - changeBookmark() : 성공\nprojectId : $projectId => $resp")
                    }
                    else -> Log.d(NETWORK, "ProjectSidebar - changeBookmark() : 실패")
                }
            }

            override fun onFailure(p0: Call<BaseResponseProjectBookmarkResult>, p1: Throwable) {
                Log.d(NETWORK, "ProjectSidebar - changeBookmark() : 실패\nbecause $p1")
            }

        })
    }

}