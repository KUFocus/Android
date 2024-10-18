package com.example.logmeet.ui.minutes

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.logmeet.NETWORK
import com.example.logmeet.R
import com.example.logmeet.data.dto.minutes.BaseResponseMinutesInfoResult
import com.example.logmeet.data.dto.minutes.BaseResponseMinutesSummarizeResult
import com.example.logmeet.databinding.ActivityMinutesDetailBinding
import com.example.logmeet.network.RetrofitClient
import com.example.logmeet.ui.application.LogmeetApplication
import com.example.logmeet.ui.showMinutesToast
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonNull.content
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitDateTime
import kotlin.math.min
import kotlin.properties.Delegates

class MinutesDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMinutesDetailBinding
    private var minutesId by Delegates.notNull<Int>()
    private var content = ""
    private var short = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMinutesDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        minutesId = intent.getIntExtra("minutesId", -1)

        setClickBtn()
        init()
    }

    @Deprecated("Deprecated in Java")
    override fun onResume() {
        super.onResume()

        val intent = intent
        val fromScreen = intent.getStringExtra("from_screen")

        if (fromScreen == "A") {
            showMinutesToast(this, R.drawable.ic_check_circle, "회의록 등록이 완료되었습니다.")
        }
    }

    private fun setClickBtn() {
        binding.ivMinuteDetailClose.setOnClickListener { finish() }
        binding.clMinutesDetailEdit.setOnClickListener {
            //수정페이지로 이동
        }
        binding.tvMinutesDetailShortBtn.setOnClickListener {
            lifecycleScope.launch { createSummarize() }
            showMinutesToast(this, R.drawable.ic_check_circle, "요약본이 생성되었습니다.")
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun init() {
        lifecycleScope.launch { getMinutesDetail() }

        setType("content")
        binding.tvMinutesDetailContentTitle.setOnClickListener { setType("content") }
        binding.tvMinutesDetailShortTitle.setOnClickListener { setType("short") }

    }

    private suspend fun createSummarize() {
        val bearerAccessToken = LogmeetApplication.getInstance().getDataStore().bearerAccessToken.first()
        RetrofitClient.minutes_instance.createSummarize(
            authorization = bearerAccessToken,
            minutesId = minutesId
        ).enqueue(object : Callback<BaseResponseMinutesSummarizeResult> {
            override fun onResponse(
                p0: Call<BaseResponseMinutesSummarizeResult>,
                p1: Response<BaseResponseMinutesSummarizeResult>
            ) {
                when (p1.code()) {
                    200 -> {
                        val resp = p1.body()?.result
                        Log.d(NETWORK, "minutesDetail - createSummarize() : 성공\n$resp")

                        if (resp != null) {
                            short = resp.summarizedText
                            binding.tvMinutesDetailContent.text = short
                            setType("short")
                        }
                    }

                    else -> {
                        Log.d(NETWORK, "minutesDetail - createSummarize() : 실패")
                    }
                }
            }

            override fun onFailure(p0: Call<BaseResponseMinutesSummarizeResult>, p1: Throwable) {
                Log.d(NETWORK, "minutesDetail - createSummarize() : 실패\nbecause $p1")
            }

        })
    }

    private suspend fun getMinutesDetail() {
        val bearerAccessToken = LogmeetApplication.getInstance().getDataStore().bearerAccessToken.first()
        RetrofitClient.minutes_instance.getMinutesDetail(
            authorization = bearerAccessToken,
            minutesId = minutesId
        ).enqueue(object : Callback<BaseResponseMinutesInfoResult> {
            override fun onResponse(
                p0: Call<BaseResponseMinutesInfoResult>,
                p1: Response<BaseResponseMinutesInfoResult>
            ) {
                when (p1.code()) {
                    200 -> {
                        val resp = p1.body()?.result
                        Log.d(NETWORK, "minutesDetail - getMinutesDetail() : 성공\n$resp")

                        if (resp != null) {
                            binding.tvMinutesDetailTitle.text = resp.name
                            binding.tvMinutesDetailPrjName.text = resp.projectId.toString()
                            binding.tvMinutesDetailDate.text = splitDateTime(resp.createdAt).first
                            content = resp.content
                            binding.tvMinutesDetailContent.text = content
                        }
                    }

                    else -> {
                        Log.d(NETWORK, "minutesDetail - getMinutesDetail() : 실패")
                    }
                }
            }

            override fun onFailure(p0: Call<BaseResponseMinutesInfoResult>, p1: Throwable) {
                Log.d(NETWORK, "minutesDetail - getMinutesDetail() : 실패\nbecause $p1")
            }

        })

    }

    @SuppressLint("ResourceAsColor")
    private fun setShortPage(isShort: Boolean, short: String?) {
        binding.tvMinutesDetailShortTitle.setOnClickListener {
            binding.tvMinutesDetailShortTitle.setTextColor(ContextCompat.getColor(this, R.color.black))
            binding.tvMinutesDetailContentTitle.setTextColor(ContextCompat.getColor(this, R.color.gray400))
            if (isShort) { //요약본이 있다면
                binding.tvMinutesDetailContent.text = short
            } else {
                binding.tvMinutesDetailContent.text = "하단에 요약하기 버튼을 눌러 요약본을 생성할 수 있습니다."
                binding.tvMinutesDetailContent.setTextColor(ContextCompat.getColor(this, R.color.gray200))
            }
        }
    }

    private fun setType(type: String) {
        when (type) {
            "content" -> {
                binding.tvMinutesDetailContentTitle.setTextColor(ContextCompat.getColor(this, R.color.black))
                binding.tvMinutesDetailShortTitle.setTextColor(ContextCompat.getColor(this, R.color.gray400))
                binding.tvMinutesDetailContent.text = content
            }
            "short" -> {
                binding.tvMinutesDetailContentTitle.setTextColor(ContextCompat.getColor(this, R.color.gray400))
                binding.tvMinutesDetailShortTitle.setTextColor(ContextCompat.getColor(this, R.color.black))
                binding.tvMinutesDetailContent.text = short
            }
        }
    }

}