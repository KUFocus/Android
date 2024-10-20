package com.example.logmeet.ui.minutes

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.logmeet.NETWORK
import com.example.logmeet.R
import com.example.logmeet.data.dto.minutes.BaseResponseListMinutesListResult
import com.example.logmeet.data.dto.minutes.MinutesListResult
import com.example.logmeet.data.dto.project.ProjectListResult
import com.example.logmeet.data.dto.project.api_response.BaseResponseListProjectListResult
import com.example.logmeet.domain.entity.MinutesData
import com.example.logmeet.databinding.FragmentMinutesBinding
import com.example.logmeet.domain.entity.FileType
import com.example.logmeet.network.RetrofitClient
import com.example.logmeet.ui.application.LogmeetApplication
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MinutesFragment : Fragment() {
    private lateinit var binding: FragmentMinutesBinding
    private lateinit var minutesAdapter: MinutesAdapter
    private var minutesList: ArrayList<MinutesListResult> = arrayListOf()
    private var minutesPhotoList: ArrayList<MinutesListResult> = arrayListOf()
    private var minutesVoiceList: ArrayList<MinutesListResult> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMinutesBinding.inflate(layoutInflater, container, false)

        setTabOnClick(1)
        init()

        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    private fun setTabOnClick(num: Int) {
        val tabTextList = arrayOf(binding.tvMinutesTab1, binding.tvMinutesTab2, binding.tvMinutesTab3)
        val tabViewList = arrayOf(binding.vMinutesTab1, binding.vMinutesTab2, binding.vMinutesTab3)
        val tabImgList = arrayOf(null, binding.ivMinutesTab2, binding.ivMinutesTab3)
        val gray = context?.let { ContextCompat.getColor(it, R.color.gray200) }
        val blue = context?.let { ContextCompat.getColor(it, R.color.main_blue) }

        for (i in 0..2) {
            if (gray != null) {
                tabTextList[i].setTextColor(gray)
                tabViewList[i].setBackgroundColor(gray)
                if (i!=0) tabImgList[i]?.setColorFilter(gray)
            }
        }

        if (blue != null) {
            tabTextList[num-1].setTextColor(blue)
            tabViewList[num-1].setBackgroundColor(blue)
            if (num-1!=0) tabImgList[num-1]?.setColorFilter(blue)
        }
    }

    private fun init() {
        binding.ivMinutesSearch.setOnClickListener {
            val intent = Intent(context, SearchMinutesActivity::class.java)
            startActivity(intent)
        }

        lifecycleScope.launch {
            setMinutesDataList()
        }
        setMinutesRV(minutesList)
        binding.clMinutesTab1.setOnClickListener {
            setTabOnClick(1)
            setMinutesRV(minutesList)
        }
        binding.clMinutesTab2.setOnClickListener {//photo
            setTabOnClick(2)
            setMinutesRV(minutesPhotoList)
        }
        binding.clMinutesTab3.setOnClickListener {//voice
            setTabOnClick(3)
            setMinutesRV(minutesVoiceList)
        }
        binding.clMinutesOrder.setOnClickListener {

        }
    }

    private suspend fun setMinutesDataList() {
        val bearerAccessToken = LogmeetApplication.getInstance().getDataStore().bearerAccessToken.first()
        RetrofitClient.minutes_instance.getUserMinutesList(
            authorization = bearerAccessToken
        ).enqueue(object : Callback<BaseResponseListMinutesListResult> {
            override fun onResponse(
                p0: Call<BaseResponseListMinutesListResult>,
                p1: Response<BaseResponseListMinutesListResult>
            ) {
                when (p1.code()) {
                    200 -> {
                        val resp = p1.body()?.result
                        Log.d(NETWORK, "MinutesFragments - setMinutesDataList() : 성공\n$resp")
                        if (resp != null) {
                            //status 관련 처리 필요
                            minutesList.addAll(resp.toList())
                            minutesList.forEach { minutes ->
                                when (minutes.type) {
                                    FileType.PICTURE.type -> {
                                        minutesPhotoList.add(minutes)
                                    }
                                    FileType.VOICE.type -> {
                                        minutesVoiceList.add(minutes)
                                    }
                                }
                            }
                        } else {
                            minutesList = arrayListOf()
                        }
                    }

                    else -> {
                        Log.d(NETWORK, "MinutesFragments - setMinutesDataList() : 실패")
                    }
                }
            }

            override fun onFailure(p0: Call<BaseResponseListMinutesListResult>, p1: Throwable) {
                Log.d(NETWORK, "MinutesFragments - setMinutesDataList() : 실패\nbecause $p1")
            }

        })
    }

    private fun setMinutesRV(list: ArrayList<MinutesListResult>) {
        minutesAdapter = MinutesAdapter(list)
        binding.rvMinutesList.adapter = minutesAdapter
        binding.rvMinutesList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
}