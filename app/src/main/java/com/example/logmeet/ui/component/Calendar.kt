package com.example.logmeet.ui.component

import DateOfMonth
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
    var monthlySchedules by remember { mutableStateOf<Map<LocalDate, List<String>>>(emptyMap()) }

//    LaunchedEffect(currentDate.year, currentDate.month) {
//        monthlySchedules = fetchMonthlySchedule(currentDate.year, currentDate.month.value)
//    }
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

//suspend fun fetchMonthlySchedule(year: Int, month: Int): Map<LocalDate, List<String>> {
//    //월별 api 연결
//    return
//}