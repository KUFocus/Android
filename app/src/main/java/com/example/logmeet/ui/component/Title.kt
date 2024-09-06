package com.example.logmeet.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.logmeet.R
import java.time.LocalDate
import java.util.Locale

@Composable
fun WeeklyTitle() {
    val today = LocalDate.now()
    val year = today.year.toString()
    val month = today.month.toString()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp)
            .background(color = Color.White),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = capitalize(month),
                style = TextStyle(
                    fontSize = 18.sp,
                    lineHeight = 22.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_bold)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF000000),

                    )
            )
            Text(
                text = year,
                style = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                    fontWeight = FontWeight(500),
                    color = Color(R.color.gray500),
                )
            )
        }

        Row {
            Image(
                modifier = Modifier
                    .clickable {
                        //전체캘린더 페이지로 연결

                    }
                    .size(24.dp),
                painter = painterResource(id = R.drawable.ic_add_calendar),
                contentDescription = "달력버튼"
            )
            Spacer(modifier = Modifier.width(14.dp))
            Image(
                modifier = Modifier
                    .clickable { }
                    .size(24.dp),
                painter = painterResource(id = R.drawable.ic_add_calendar),
                contentDescription = "일정추가"
            )
        }
    }
}

@Composable
fun MonthlyTitle() {
    val today = LocalDate.now()
    val year = today.year.toString()
    val month = today.month.toString()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = year,
            style = TextStyle(
                fontSize = 12.sp,
                lineHeight = 16.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                fontWeight = FontWeight(500),
                color = Color(R.color.gray500),
            )
        )
        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {  },
                    painter = painterResource(id = R.drawable.ic_calendar_left),
                    contentDescription = "이전"
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = capitalize(month),
                    style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 22.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_bold)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFF000000),

                        )
                )
                Spacer(modifier = Modifier.width(12.dp))
                Image(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {  },
                    painter = painterResource(id = R.drawable.ic_calendar_right),
                    contentDescription = "다음"
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.End)
            ) {
                Image(
                    modifier = Modifier
                        .clickable { }
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.ic_add_calendar),
                    contentDescription = "일정추가"
                )
            }
        }
    }
}

fun capitalize(input: String): String {
    return if (input.isEmpty()) {
        input
    } else input.substring(0, 1).uppercase(Locale.getDefault()) + input.substring(1).lowercase(
        Locale.getDefault()
    )
}

@Preview
@Composable
fun Preview() {
    Column {
        WeeklyTitle()
        MonthlyTitle()
    }
}