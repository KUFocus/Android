package com.example.logmeet.ui.component

import android.content.Intent
import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.logmeet.R
import com.example.logmeet.ui.home.HomeFullCalendarActivity
import java.time.LocalDate
import java.time.Month
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
            val context = LocalContext.current
            Image(
                modifier = Modifier
                    .clickable {
                        val intent = Intent(context, HomeFullCalendarActivity::class.java)
                        context.startActivity(intent)
                    }
                    .size(24.dp),
                painter = painterResource(id = R.drawable.ic_calendar_png),
                contentDescription = "달력버튼"
            )
            Spacer(modifier = Modifier.width(14.dp))
            Image(
                modifier = Modifier
                    .clickable {
//                        val intent = Intent(context, Add::class.java)
//                        context.startActivity(intent)
                    }
                    .size(24.dp),
                painter = painterResource(id = R.drawable.ic_add_calendar),
                contentDescription = "일정추가"
            )
        }
    }
}

@Composable
fun MonthlyTitle(
    clicked: (Month) -> Unit
) {
    var currentDate by remember { mutableStateOf(LocalDate.now()) }
    var year = currentDate.year.toString()
    var month by remember { mutableStateOf(currentDate.month.toString()) }

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
                        .clickable {
                            currentDate = LocalDate.of(
                                currentDate.year,
                                currentDate.minusMonths(1).month,
                                currentDate.dayOfMonth
                            )
                            clicked(currentDate.month)
                            month = currentDate.month.toString()
                        },
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
                        .clickable {
                            currentDate = LocalDate.of(
                                currentDate.year,
                                currentDate.plusMonths(1).month,
                                currentDate.dayOfMonth
                            )
                            clicked(currentDate.month)
                            month = currentDate.month.toString()
                        },
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