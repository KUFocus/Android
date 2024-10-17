package com.example.logmeet.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.example.logmeet.R
import com.example.logmeet.databinding.ToastMinutesBinding

fun showMinutesToast(context: Context, img: Int, message: String) {
    val inflater = LayoutInflater.from(context)
    val layout = inflater.inflate(R.layout.toast_minutes, null)
    val binding = ToastMinutesBinding.bind(layout)

    binding.ivToastMinutes.setImageResource(img)
    binding.tvToastMinutes.text = message

    val toast = Toast(context)
    toast.duration = Toast.LENGTH_SHORT
    toast.view = layout
    toast.show()
}
