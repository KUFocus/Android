package com.example.logmeet.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.logmeet.R
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun DayOfWeek() {
    val daysOfWeek = listOf("일", "월", "화", "수", "목", "금", "토")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp)
            .background(Color.White),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        var color = R.color.black
        for (day in daysOfWeek) {
            color = if (day == "일") R.color.error else if (day == "토") R.color.main_blue else R.color.black

            Text(
                text = day,
                style = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_bold)),
                    fontWeight = FontWeight(700),
                    color = colorResource(id = color),
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier.width(20.dp)
            )
        }
    }
}


@Preview
@Composable
fun PreviewDayofWeek() {
    DayOfWeek()
}