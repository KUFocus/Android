package com.example.logmeet.ui.component

import DateOfMonth
import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.logmeet.R
import com.example.logmeet.ui.home.HomeFragment
import com.example.logmeet.ui.showMinutesToast
import java.time.LocalDate

@Composable
fun WeeklyCalendar(
    selectedDate: (String) -> Unit,
    onAddScheduleComplete: (Int) -> Unit
) {
    Column {
        WeeklyTitle() { resultCode ->
            onAddScheduleComplete(resultCode)
        }
        Spacer(modifier = Modifier.height(16.dp))
        DayOfWeek()
        Spacer(modifier = Modifier.height(16.dp))
        DateOfWeek(
            date = LocalDate.now()
        ) { selectedDate(it) }
    }
}

@Composable
fun MonthlyCalendar(
    selectedDate: (String) -> Unit,
    isBottomSheet: Boolean,
    onDismiss: () -> Unit,
    onAddScheduleComplete: (Int) -> Unit
) {
    var currentDate by remember { mutableStateOf<LocalDate>(LocalDate.now()) }
    Column {
        MonthlyTitle(
            isBottomSheet = isBottomSheet,
            clicked = {
                currentDate = LocalDate.of(currentDate.year, it, currentDate.dayOfMonth)
                selectedDate(currentDate.toString())
            },
            onAddScheduleComplete = { resultCode ->
                onAddScheduleComplete(resultCode)
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        DayOfWeek()
        Spacer(modifier = Modifier.height(16.dp))
        DateOfMonth(
            isBottomSheet = isBottomSheet,
            date = currentDate
        ) {
            selectedDate(it.toString())
            currentDate = it
            onDismiss()
        }
    }
}