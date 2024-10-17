package com.example.logmeet.ui.component

import DateOfMonth
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
import java.time.LocalDate

@Composable
fun WeeklyCalendar(
    selectedDate: (String) -> Unit
) {
    Column {
        WeeklyTitle()
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
    selectedDate: (LocalDate) -> Unit,
    isBottomSheet: Boolean,
    onDismiss: () -> Unit
) {
    var currentDate by remember { mutableStateOf<LocalDate>(LocalDate.now()) }
    Column {
        MonthlyTitle(
            isBottomSheet = isBottomSheet,
            clicked = {
                currentDate = LocalDate.of(currentDate.year, it, currentDate.dayOfMonth)
                selectedDate(currentDate)
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        DayOfWeek()
        Spacer(modifier = Modifier.height(16.dp))
        DateOfMonth(
            date = currentDate
        ) {
            selectedDate(it)
            currentDate = it
            onDismiss()
        }
    }
}