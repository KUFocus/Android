package com.example.logmeet.ui.projects

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.logmeet.ProjectDrawableResources
import com.example.logmeet.R
import com.example.logmeet.domain.entity.PeopleData
import com.example.logmeet.databinding.ActivityProjectDetailBinding

class ProjectDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProjectDetailBinding
    private lateinit var peopleAdapter: PeopleAdapter
    private var peopleList: ArrayList<PeopleData> = arrayListOf()
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
            val intent = Intent(this, EditProjectActivity::class.java)
            intent.putExtra("projectId", intent.getStringExtra("projectId"))
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getContent() {
        //api로 정보 가져오기
        binding.tietDetailPName.setText("로그밋프로젝트")
        binding.tietDetailPExplain.setText("2024 졸업프로젝트로 진행하는 팀플")
        binding.tietDetailPDate.setText("yyyy.MM.dd")
        peopleList = arrayListOf(
            PeopleData("김채린", false, 1, "email"),
            PeopleData("구서정", false, 1, "email"),
            PeopleData("나나나", false, 1, "email"),
            PeopleData("이이이", false, 1, "email"),
            PeopleData("전우진", true, 1, "email"),
            PeopleData("마마마", false, 1, "email"),
            PeopleData("자자자", false, 1, "email"),
        )
        setPeopleRv()
        val colorId = "3"
        setProjectColor(colorId)
    }

    private fun setPeopleRv() {
        val peopleRV = binding.rvDetailPPeopleList
        val sortedList = sortPeopleList(peopleList)
        peopleAdapter = PeopleAdapter(sortedList)
        peopleRV.adapter = peopleAdapter
        peopleRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun sortPeopleList(peopleList: ArrayList<PeopleData>): ArrayList<PeopleData> {
        return ArrayList(
            peopleList
                .sortedWith(
                    compareByDescending<PeopleData> { it.leader }
                        .thenBy { it.name }
                )
        )
    }

    private fun setProjectColor(colorId: String) {
        val color = ProjectDrawableResources.colorList[colorId.toInt()-1]
        binding.vDetailPColor.setBackgroundResource(color)
    }
}