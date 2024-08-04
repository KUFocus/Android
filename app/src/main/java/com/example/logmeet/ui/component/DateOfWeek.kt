package com.example.logmeet.ui.component

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.logmeet.R
import java.time.LocalDate

@Composable
fun DateOfWeek(date: LocalDate) {
    val startOfWeek = date.minusDays((date.dayOfWeek.value % 7).toLong())

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
            .background(Color.White),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        var clickedDate by remember { mutableStateOf(-1) }

        for (i in 0..6) {
            val currentDate = startOfWeek.plusDays(i.toLong()).dayOfMonth
            val isSelected = (clickedDate == currentDate)

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .clickable { clickedDate = currentDate },
                contentAlignment = Alignment.Center
            ) {
                val backgroundColor = if (isSelected) R.color.main_blue else R.color.white
                val textColor = if (isSelected) colorResource(id = R.color.white)
                else {
                    if (i == 0) colorResource(id = R.color.error)
                    else if (i == 6) colorResource(id = R.color.main_blue)
                    else colorResource(id = R.color.black)
                }

                BackgroundCircle(backgroundColor)
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
        }
    }
}

@Composable
fun BackgroundCircle(color: Int) {
    val backgroundColor = colorResource(id = color)
    Canvas(
        modifier = Modifier
            .size(24.dp)
    ) {
        drawCircle(
            color = backgroundColor,
            radius = size.minDimension / 2
        )
    }
}