package com.example.logmeet.ui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import com.example.logmeet.databinding.CheckDialogBinding

class CheckDialog(context: Context, title: String) : Dialog(context) {
    private lateinit var itemClickListener: ItemClickListener
    private lateinit var binding: CheckDialogBinding
    val title = title

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CheckDialogBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        binding.tvCheckDialogTitle.text = title

        // 배경을 투명하게 (Make the background transparent)
        // 다이얼로그를 둥글게 표현하기 위해 필요 (Required to round corner)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setCanceledOnTouchOutside(true)

        setCancelable(true)

        binding.tvCheckDialogCancel.setOnClickListener {
            dismiss()
        }

        binding.tvCheckDialogConfirm.setOnClickListener {
            itemClickListener.onClick()
            dismiss()
        }
    }

    interface ItemClickListener {
        fun onClick() {

        }
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }
}