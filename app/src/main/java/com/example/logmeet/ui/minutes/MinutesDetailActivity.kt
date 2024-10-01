package com.example.logmeet.ui.minutes

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.logmeet.R
import com.example.logmeet.databinding.ActivityMinutesDetailBinding
import com.example.logmeet.ui.showMinutesToast

class MinutesDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMinutesDetailBinding
    private var contentPage = 0 //0 기본 내용, 1 요약본
    //private val minutes :
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

        setClickBtn()
        init()
//        showMinutesToast(this, R.drawable.ic_check_circle, "회의록 등록이 완료되었습니다.")
    }

    private fun setClickBtn() {
        binding.ivMinuteDetailClose.setOnClickListener { finish() }
        binding.clMinutesDetailEdit.setOnClickListener {
            //수정페이지로 이동
        }
        binding.tvMinutesDetailShortBtn.setOnClickListener {
            //요약하기 api 연결
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun init() {
        //회의록 정보 받아오기 api
        val title = "회의록 제목"
        val prjName = "졸업프로젝트"
        val date = "2024.05.10"
        val isShort = true
        val type = "0"
        val content = "회의록 내용이지롱~회의록 내용이지롱~회의록 내용이지롱~회의록 내용이지롱~회의록 내용이지롱~회의록 내용이지롱~회의록 내용이지롱~회의록 내용이지롱~"
        val short = "요약 부분이지롱~요약 부분이지롱~요약 부분이지롱~요약 부분이지롱~요약 부분이지롱~요약 부분이지롱~요약 부분이지롱~"

        setType(type)
        binding.tvMinutesDetailTitle.text = title
        binding.tvMinutesDetailPrjName.text = prjName
        binding.tvMinutesDetailDate.text = date
        binding.tvMinutesDetailContentTitle.setOnClickListener {
            binding.tvMinutesDetailShortTitle.setTextColor(ContextCompat.getColor(this, R.color.gray400))
            binding.tvMinutesDetailContentTitle.setTextColor(ContextCompat.getColor(this, R.color.black))
            binding.tvMinutesDetailContent.text = content
            binding.tvMinutesDetailContent.setTextColor(ContextCompat.getColor(this, R.color.black))
            contentPage = 0
        }
        setShortPage(isShort, short)
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
            contentPage = 1
        }
        binding.tvMinutesDetailShortBtn.visibility = if (isShort) View.GONE else View.VISIBLE
    }

    private fun setType(type: String) {

    }

}