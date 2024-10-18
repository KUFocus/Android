package com.example.logmeet.ui.minutes

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.logmeet.databinding.FragmentCreateMinutesBottomSheetBinding
import com.example.logmeet.domain.entity.FileType
import com.example.logmeet.ui.component.MakeMinutesDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CreateMinutesBottomSheetFragment() : BottomSheetDialogFragment() {

    private var _binding: FragmentCreateMinutesBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateMinutesBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

            }
            FileType.VOICE.type -> {

            }
            FileType.RECORD.type -> {

            }
        }
    }
}