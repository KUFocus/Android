package com.example.logmeet.ui.minutes

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import com.example.logmeet.databinding.FragmentCreateMinutesBottomSheetBinding
import com.example.logmeet.domain.entity.FileType
import com.example.logmeet.ui.component.MakeMinutesDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.ByteArrayOutputStream
import java.io.File
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class CreateMinutesBottomSheetFragment() : BottomSheetDialogFragment() {

    private var _binding: FragmentCreateMinutesBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val CAMERA_PERMISSION_CODE = 100
//    private lateinit var cameraPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var photoURI: Uri
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateMinutesBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                photoURI.let { uri ->
                    val base64String = uriToBase64(uri)
                    Log.d("Base64", "Image Base64: $base64String")
                }
            } else {
                Toast.makeText(requireContext(), "사진 촬영이 취소되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.compCreateMinutesCalendar.setContent {
            MakeMinutesDialog(
                title = "회의록 생성 방식을 선택해주세요",
                onClick = { type ->
                    openMinuteType(type)
                    dismiss()
                },
                onClickCancel = {
                    dismiss()
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun openMinuteType(type: String) {
        when (type) {
            FileType.MANUAL.type -> {
                val intent = Intent(requireContext(), CreateMintuesTextActivity::class.java)
                startActivity(intent)
            }
            FileType.PICTURE.type -> {

            }
            FileType.CAMERA.type -> {
                requestCameraPermission()
            }
            FileType.VOICE.type -> {

            }
            FileType.RECORD.type -> {

            }
        }
    }

    private fun uriToBase64(uri: Uri): String {
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val byteArray = inputStream?.readBytes()
        inputStream?.close()

        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir).apply {
            Log.d("Camera", "Image file created: $absolutePath")
        }
    }

    private fun openCamera() {
        val photoFile = createImageFile()
        photoURI = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.provider",
            photoFile
        )

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        }

        cameraLauncher.launch(intent)
    }

//    private fun encodeImageToBase64(uri: Uri) {
//        val inputStream = requireContext().contentResolver.openInputStream(uri)
//        val bitmap = BitmapFactory.decodeStream(inputStream)
//        val byteArrayOutputStream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
//        val byteArray = byteArrayOutputStream.toByteArray()
//        val base64String = Base64.encodeToString(byteArray, Base64.DEFAULT)
//
//        // 원하는 화면으로 전환
////        val intent = Intent(this, AnotherActivity::class.java)
////        intent.putExtra("image_base64", base64String)
////        startActivity(intent)
//    }

    private fun requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_CODE
            )
        } else {
            openCamera()
        }
    }
}