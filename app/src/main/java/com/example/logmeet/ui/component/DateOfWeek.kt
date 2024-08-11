package com.example.logmeet.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.logmeet.R
import java.time.LocalDate

@Composable
fun DateOfWeek(
    date: LocalDate,
    clicked: (String) -> Unit,
) {
    val startOfWeek = date.minusDays((date.dayOfWeek.value % 7).toLong())
    val today = LocalDate.now()
    var clickedDate by remember { mutableStateOf(-1) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color.White),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        (0..6).forEach { i ->
            val currentDate = startOfWeek.plusDays(i.toLong()).dayOfMonth
            val isSelected = (clickedDate == currentDate)
            val textColor = determineTextColor(today, currentDate, i)

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .width(32.dp)
                        .clickable {
                            clickedDate = currentDate
                            clicked(clickedDate.toString()) },
                    contentAlignment = Alignment.Center
                ) {
                    DrawCircle(
                        today = today,
                        currentDate = currentDate,
                        isSelected = isSelected
                    )
                    Text(
                        text = currentDate.toString(),
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
                    date = LocalDate.of(
                        startOfWeek.year,
                        startOfWeek.month,
                        currentDate
                    )
                )
            }
        }
    }
}

@Composable
fun GetDaySchedule(date: LocalDate?) {
    val projectColorList = arrayOf(
        R.color.project1, R.color.project2, R.color.project3, R.color.project4,
        R.color.project5, R.color.project6, R.color.project7, R.color.project8,
        R.color.project9, R.color.project10, R.color.project11, R.color.project12
    )
    val scheduleList = intArrayOf(1, 10, 12, 3) // 서버에서 가져올 데이터
    val size = scheduleList.size
    val spacer = if (size == 2) 4 else 0

    Column(
        modifier = Modifier
            .height(24.dp)
            .width(32.dp),
    ) {
        if (size > 0) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = when (size) {
                    1, 2 -> Arrangement.Center
                    else -> Arrangement.SpaceBetween
                }
            ) {
                scheduleList.take(3).forEach { i ->
                    ScheduleCircle(color = projectColorList[i - 1])
                    Spacer(modifier = Modifier.width(spacer.dp))
                }
            }
            if (size > 3) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "+${size - 3}",
                        style = TextStyle(
                            fontSize = 12.sp,
                            lineHeight = 16.sp,
                            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                            fontWeight = FontWeight(400),
                            color = colorResource(id = R.color.gray500),
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun DrawCircle(today: LocalDate, currentDate: Int, isSelected: Boolean) {
    if (today.dayOfMonth == currentDate) {
        TodayBackgroundCircle()
    } else {
        val backgroundColor = if (isSelected) R.color.sub_blue else R.color.white
        BackgroundCircle(color = backgroundColor)
    }
}

@Composable
fun BackgroundCircle(color: Int) {
    val backgroundColor = colorResource(id = color)
    Canvas(
        modifier = Modifier.size(24.dp)
    ) {
        drawCircle(
            color = backgroundColor,
            radius = size.minDimension / 2,
            style = Stroke(width = 5f)
        )
    }
}

@Composable
fun TodayBackgroundCircle() {
    val backgroundColor = colorResource(id = R.color.main_blue)
    Canvas(
        modifier = Modifier.size(24.dp)
    ) {
        drawCircle(
            color = backgroundColor,
            radius = size.minDimension / 2
        )
    }
}

@Composable
fun ScheduleCircle(color: Int) {
    val backgroundColor = colorResource(id = color)
    Canvas(
        modifier = Modifier.size(8.dp)
    ) {
        drawCircle(
            color = backgroundColor,
            radius = size.minDimension / 2
        )
    }
}

@Composable
fun determineTextColor(today: LocalDate, currentDate: Int, dayIndex: Int): Color {
    return when {
        today.dayOfMonth == currentDate -> colorResource(id = R.color.white)
        dayIndex == 0 -> colorResource(id = R.color.error)
        dayIndex == 6 -> colorResource(id = R.color.main_blue)
        else -> colorResource(id = R.color.black)
    }
}
