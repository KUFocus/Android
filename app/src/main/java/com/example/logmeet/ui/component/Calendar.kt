package com.example.logmeet.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate

@Composable
fun WeeklyCalendar(
    selectedDate : (String) -> Unit
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
    selectedDate: (String) -> Unit
) {
    Column {
        MonthlyTitle()
        Spacer(modifier = Modifier.height(16.dp))
        DayOfWeek()
        Spacer(modifier = Modifier.height(16.dp))
        val today = LocalDate.now()
        val date = today

    }
}