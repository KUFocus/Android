package com.example.logmeet.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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