package com.example.logmeet.ui.projects

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.logmeet.R
import com.example.logmeet.databinding.ActivityProjectDetailBinding

class ProjectDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProjectDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityProjectDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        init()
        getContent()
    }

    private fun init() {
        binding.ivDetailPBack.setOnClickListener { finish() }
        binding.ivDetailPBurgerMenu.setOnClickListener {
            //dialog열기
        }

        binding.tietDetailPName.isActivated = false
        binding.tietDetailPExplain.isActivated = false
    }

    private fun getContent() {
        //api로 정보 가져오기
        binding.tietDetailPName.setText("로그밋프로젝트")
        binding.tietDetailPExplain.setText("2024 졸업프로젝트로 진행하는 팀플")
        binding.tietDetailPDate.setText("yyyy.MM.dd")
        binding.vDetailPColor.setBackgroundResource(R.drawable.color_prj10)
    }
}