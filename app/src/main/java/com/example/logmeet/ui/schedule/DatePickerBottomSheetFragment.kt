package com.example.logmeet.ui.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.logmeet.databinding.FragmentDatePickerBottomSheetBinding
import com.example.logmeet.ui.component.MonthlyCalendar
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DatePickerBottomSheetFragment(
    private val onDateSelected: (String)-> Unit
) : BottomSheetDialogFragment() {

    private var _binding : FragmentDatePickerBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDatePickerBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.compDatePickerCalendar.setContent {
            MonthlyCalendar(
                beforeActivity = null,
                isBottomSheet = true,
                selectedDate = {selectedDate ->
                    onDateSelected(selectedDate.toString())
                },
                onDismiss = { dismiss() }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}