package com.example.logmeet.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.logmeet.R
import java.time.LocalDate

@Composable
fun DateOfMonth(
    date: LocalDate, // 기준 날짜 (보통 오늘 날짜를 사용)
    clicked: (String) -> Unit, // 날짜 클릭 시 실행되는 콜백 함수
) {
    val startOfMonth = date.withDayOfMonth(1) // 이번 달의 첫 날
    val endOfMonth = date.withDayOfMonth(date.lengthOfMonth()) // 이번 달의 마지막 날

    val firstDayOfWeek = startOfMonth.minusDays((startOfMonth.dayOfWeek.value % 7).toLong()) // 달력 시작 주차의 첫 날
    val lastDayOfWeek = endOfMonth.plusDays((6 - endOfMonth.dayOfWeek.value % 7).toLong()) // 달력 끝 주차의 마지막 날

    // 각 주를 저장할 리스트
    val weeks = mutableListOf<List<LocalDate>>()

    var currentDay = firstDayOfWeek
    while (currentDay <= lastDayOfWeek) {
        val week = (0..6).map { currentDay.plusDays(it.toLong()) } // 주차 생성
        weeks.add(week)
        currentDay = currentDay.plusWeeks(1) // 다음 주로 이동
    }

    Column {
        weeks.forEach { week ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                week.forEach { day ->
                    val isSelected = (LocalDate.now() == day)
                    val textColor = determineTextColor(LocalDate.now(), day.dayOfMonth, day.dayOfWeek.value)

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .width(32.dp)
                                .clickable {
                                    clicked(day.toString())
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            DrawCircle(
                                today = LocalDate.now(),
                                currentDate = day.dayOfMonth,
                                isSelected = isSelected
                            )
                            Text(
                                text = day.dayOfMonth.toString(),
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    lineHeight = 16.sp,
                                    fontFamily = FontFamily(Font(R.font.pretendard_bold)),
                                    color = textColor
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        GetDaySchedule(
                            date = day
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}