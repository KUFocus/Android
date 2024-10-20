package com.example.logmeet.ui.minutes

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.logmeet.NETWORK
import com.example.logmeet.R
import com.example.logmeet.data.dto.minutes.BaseResponseMinutesFileUploadResponse
import com.example.logmeet.data.dto.minutes.MinutesFileUploadRequest
import com.example.logmeet.data.dto.project.ProjectListResult
import com.example.logmeet.data.dto.project.api_response.BaseResponseListProjectListResult
import com.example.logmeet.databinding.ActivitySetMinutesInfoBinding
import com.example.logmeet.domain.entity.FileType
import com.example.logmeet.domain.entity.ProjectDrawableResources
import com.example.logmeet.network.RetrofitClient
import com.example.logmeet.ui.application.LogmeetApplication
import com.example.logmeet.ui.schedule.ProjectSelectionAdapter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CreateMinutesCameraActivity : AppCompatActivity(), ProjectSelectionAdapter.OnProjectClickListener {
    lateinit var binding: ActivitySetMinutesInfoBinding
    var isNameNotNull = false
    private var isProjectSelected = false
    private lateinit var projectSelectionAdapter: ProjectSelectionAdapter
    private var projectList: List<ProjectListResult> = arrayListOf()
    private var projectId = -1

    private val CAMERA_PERMISSION_CODE = 100
    private lateinit var photoURI: Uri
    private lateinit var fileName: String
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>

    private lateinit var photoFile: File

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySetMinutesInfoBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        photoFile = createImageFile()

        cameraLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                photoURI.let { uri ->
//                    val base64String = uriToBase64(uri)
                    val base64Image = photoFile.toBase64()
                    println("Base64 Image: $base64Image")
                    lifecycleScope.launch { createMinutesFile(base64Image) }
                }
            } else {
                Toast.makeText(this, "사진 촬영이 취소되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        takePhoto()
        //requestCameraPermission()

        init()
    }

    private fun init() {
        binding.ivSetMinutesInfoBack.setOnClickListener { finish() }
        val btnNameClear = binding.ivSetMinutesInfoNameClear
        btnNameClear.setOnClickListener { binding.tietSetMinutesInfoName.setText("") }

        binding.tietSetMinutesInfoName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    btnNameClear.visibility = View.GONE
                } else {
                    btnNameClear.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {
                isNameNotNull = !s.isNullOrEmpty()
                checkBtnAvailable()
            }
        })

        binding.rvSetMinutesInfoProjectList.visibility = View.GONE
        binding.tietSetMinutesInfoProject.setOnClickListener {
            binding.rvSetMinutesInfoProjectList.visibility = View.VISIBLE
            lifecycleScope.launch { setProjectList() }
        }
    }

    private suspend fun setProjectList() {
        val bearerAccessToken = LogmeetApplication.getInstance().getDataStore().bearerAccessToken.first()
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
                        Log.d(NETWORK, "addSchedule - setProjectList() : 성공\n$resp")
                        if (resp != null) {
                            (projectList as ArrayList<ProjectListResult>).addAll(resp.toList())
                            setProjectListRV(projectList)
                        } else {
                            projectList = emptyList()
                        }
                    }

                    else -> {
                        Log.d(NETWORK, "addSchedule - setProjectList() : 실패")
                    }
                }
            }

            override fun onFailure(p0: Call<BaseResponseListProjectListResult>, p1: Throwable) {
                Log.d(NETWORK, "addSchedule - setProjectList() : 실패\nbecause $p1")
            }

        })
    }

    private fun setProjectListRV(projectList: List<ProjectListResult>) {
        projectSelectionAdapter = ProjectSelectionAdapter(projectList, this)
        binding.rvSetMinutesInfoProjectList.adapter = projectSelectionAdapter
        binding.rvSetMinutesInfoProjectList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onProjectClick(
        projectId: Int,
        projectName: String,
        projectColor: String
    ) {
        binding.tietSetMinutesInfoProject.setText("")
        binding.tietSetMinutesInfoProject.hint = ""
        binding.clSetMinutesInfoProject.visibility = View.VISIBLE
        binding.tvSetMinutesInfoProjectName.text = projectName
        val number = projectColor.split("_")[1].toInt()
        val color = ProjectDrawableResources.colorList[number - 1]
        binding.vSetMinutesInfoProjectColor.setBackgroundResource(color)
        binding.rvSetMinutesInfoProjectList.visibility = View.GONE

        this.projectId = projectId
        isProjectSelected = true
    }

    private fun checkBtnAvailable() {
        val btnDone = binding.tvSetMinutesInfoDone
        if (isNameNotNull && isProjectSelected) {
            btnDone.setBackgroundResource(R.drawable.btn_blue_8px)
            btnDone.setOnClickListener {
                //로딩화면
                val intent = Intent(this, AiResultActivity::class.java)
                startActivity(intent)
                finish()
            }
        } else {
            btnDone.setBackgroundResource(R.drawable.btn_gray_8px)
            btnDone.setOnClickListener { }
        }
    }

    private fun takePhoto() {
//        photoFile = createImageFile()
//        fileName = photoFile.toString()

        photoURI = FileProvider.getUriForFile(
            this,
            "${applicationContext.packageName}.provider",
            photoFile
        )

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        }
        cameraLauncher.launch(intent)
    }

    private fun createImageFile(): File {
        val timeStamp: String = Date().time.toString()
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        fileName = "JPEG_${timeStamp}.jpg"
        Log.d("okhttp", "createImageFile: fileName  = $fileName")
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
    }

    private fun File.toBase64(): String {
        val bytes = readBytes()
        return Base64.encodeToString(bytes, Base64.NO_WRAP)
    }

//    private fun uriToBase64(uri: Uri): String {
//        val inputStream = contentResolver.openInputStream(uri)
//        val byteArray = inputStream?.readBytes()
//        inputStream?.close()
//
//        return Base64.encodeToString(byteArray, Base64.DEFAULT)
//    }
//
//    private fun createImageFileorigin(): File {
//        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
//        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir).apply {
//            Log.d("Camera", "Image file created: $absolutePath")
//        }
//    }
//
//    private fun openCamera() {
//        val photoFile = createImageFile()
//        fileName = photoFile.toString()
//        photoURI = FileProvider.getUriForFile(
//            this,
//            "${packageName}.provider",
//            photoFile
//        )
//
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
//            putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
//        }
//
//        cameraLauncher.launch(intent)
//    }
//
//    private fun requestCameraPermission() {
//        if (ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.CAMERA
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(Manifest.permission.CAMERA),
//                CAMERA_PERMISSION_CODE
//            )
//        } else {
//            openCamera()
//        }
//    }

    private suspend fun createMinutesFile(base64String: String) {
        val bearerAccessToken = LogmeetApplication.getInstance().getDataStore().bearerAccessToken.first()
        RetrofitClient.minutes_instance.createMinutesFile(
            authorization = bearerAccessToken,
            minutesFileUploadRequest = MinutesFileUploadRequest(
                base64FileData = base64String,
                fileName = fileName,
                fileType = FileType.PICTURE.type
            )
        ).enqueue(object : Callback<BaseResponseMinutesFileUploadResponse> {
            override fun onResponse(
                p0: Call<BaseResponseMinutesFileUploadResponse>,
                p1: Response<BaseResponseMinutesFileUploadResponse>
            ) {
                when (p1.code()) {
                    200 -> {
                        val resp = p1.body()?.result
                        Log.d(NETWORK, "createMinutesCamera - createMinutesFile() : 성공")

                    }

                    else -> {
                        Log.d(NETWORK, "createMinutesCamera - createMinutesFile() : 실패")
                    }
                }
            }

            override fun onFailure(p0: Call<BaseResponseMinutesFileUploadResponse>, p1: Throwable) {
                Log.d(NETWORK, "createMinutesCamera - createMinutesFile() : 실패\nbecause $p1")
            }

        })

    }
}