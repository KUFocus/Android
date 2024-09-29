package com.example.logmeet.ui

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import com.example.logmeet.R
import com.example.logmeet.databinding.ToastMinutesBinding

fun showMinutesToast(context: Context, img: Int, message: String) {
    val inflater = LayoutInflater.from(context)
    val layout = inflater.inflate(R.layout.toast_minutes, null)
    val binding = ToastMinutesBinding.inflate(inflater)

    binding.ivToastMinutes.setImageResource(img)
    binding.tvToastMinutes.text = message

//    val toastIcon = layout.findViewById<ImageView>(R.id.toast_icon)
//    toastIcon.setImageResource(R.drawable.ic_success) // 아이콘을 원하는 대로 설정

    val toast = Toast(context)
    toast.duration = Toast.LENGTH_SHORT
    toast.view = layout
    toast.show()
}
