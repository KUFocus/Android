package com.example.logmeet.ui.schedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.logmeet.R
import com.example.logmeet.databinding.FragmentTimePickerBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TimePickerBottomSheetFragment(
    private val onTimeSelected: (String) -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: FragmentTimePickerBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimePickerBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.compTimePickerCalendar.setContent {
            TimePicker(
                onTimeSelected = { selectedTime ->
                    onTimeSelected(selectedTime)
                    dismiss()
                }
            )
        }
    }
}