package com.example.logmeet.ui.minutes

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide.init
import com.example.logmeet.NETWORK
import com.example.logmeet.R
import com.example.logmeet.data.dto.minutes.BaseResponseMinutesSummarizeResult
import com.example.logmeet.databinding.ActivityAiResultBinding
import com.example.logmeet.network.RetrofitClient
import com.example.logmeet.ui.application.LogmeetApplication
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.JsonNull.content
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AiResultActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAiResultBinding
    private var short = ""
    private var minutesId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAiResultBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        minutesId = intent.getIntExtra("minutesId", -1)
        init()

        binding.ivAiResultClose.setOnClickListener { finish() }
        binding.tvAiResultShortBtn.setOnClickListener {
            //요약하기 api
        }
    }

    private fun init() {

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
                        Log.d(NETWORK, "aiResult - createSummarize() : 성공\n$resp")

                        if (resp != null) {
                            short = resp.summarizedText
                            binding.tvAiResultContent.text = short
                            setType("short")
                        }
                    }

                    else -> {
                        Log.d(NETWORK, "aiResult - createSummarize() : 실패")
                    }
                }
            }

            override fun onFailure(p0: Call<BaseResponseMinutesSummarizeResult>, p1: Throwable) {
                Log.d(NETWORK, "aiResult - createSummarize() : 실패\nbecause $p1")
            }

        })
    }

    private fun setType(type: String) {
        when (type) {
            "content" -> {
                binding.tvAiResultContentTitle.setTextColor(ContextCompat.getColor(this, R.color.black))
                binding.tvAiResultShortTitle.setTextColor(ContextCompat.getColor(this, R.color.gray400))
                binding.tvAiResultContent.text = content
            }
            "short" -> {
                binding.tvAiResultContentTitle.setTextColor(ContextCompat.getColor(this, R.color.gray400))
                binding.tvAiResultShortTitle.setTextColor(ContextCompat.getColor(this, R.color.black))
                binding.tvAiResultContent.text = short
                if (short.isEmpty()) {
                    binding.tvAiResultNoShort.visibility = View.VISIBLE
                    binding.tvAiResultContent.visibility = View.GONE
                } else {
                    binding.tvAiResultNoShort.visibility = View.GONE
                    binding.tvAiResultContent.visibility = View.VISIBLE
                }
            }
        }
    }
}