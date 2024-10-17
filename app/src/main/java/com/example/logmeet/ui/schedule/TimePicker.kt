package com.example.logmeet.ui.schedule

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.logmeet.R

@SuppressLint("DefaultLocale")
@Composable
fun TimePicker(
    onTimeSelected: (String) -> Unit
) {
    var selectedHour by remember { mutableStateOf(12) }
    var selectedMinute by remember { mutableStateOf(0) }
    val context = LocalContext.current
    val strokeColor = Color.Gray

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CustomStrokeColorImage(
                    imageId = R.drawable.ic_chevron_up,
                    strokeColor = strokeColor,
                    onClick = {
                        selectedHour = if (selectedHour == 23) 0 else ++selectedHour
                        Log.d("chrin", "CustomStrokeColorImage: 클릭됨 $selectedHour")
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .border(
                            BorderStroke(1.dp, Color.LightGray),
                            shape = RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = String.format("%02d", selectedHour),
                        style = TextStyle(
                            fontSize = 30.sp,
                            lineHeight = 36.sp,
                            fontFamily = FontFamily(Font(R.font.pretendard_bold)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFF000000),
                        )
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                CustomStrokeColorImage(
                    imageId = R.drawable.ic_chevron_down,
                    strokeColor = strokeColor,
                    onClick = {
                        selectedHour = if (selectedHour == 0) 23 else --selectedHour
                    }
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = " : ",
                style = TextStyle(
                    fontSize = 30.sp,
                    lineHeight = 36.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_bold)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF000000),
                )
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomStrokeColorImage(
                    imageId = R.drawable.ic_chevron_up,
                    strokeColor = strokeColor,
                    onClick = {
                        selectedMinute = if (selectedMinute == 59) 0 else ++selectedMinute
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .border(
                            BorderStroke(1.dp, Color.LightGray),
                            shape = RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = String.format("%02d", selectedMinute),
                        style = TextStyle(
                            fontSize = 30.sp,
                            lineHeight = 36.sp,
                            fontFamily = FontFamily(Font(R.font.pretendard_bold)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFF000000),
                        )
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                CustomStrokeColorImage(
                    imageId = R.drawable.ic_chevron_down,
                    strokeColor = strokeColor,
                    onClick = {
                        selectedMinute = if (selectedMinute == 0) 59 else --selectedMinute
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(26.dp))
        Button(
            onClick = {
                val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                onTimeSelected(formattedTime)
            },
            modifier = Modifier
                .size(width = 200.dp, height = 36.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(ContextCompat.getColor(context, R.color.main_blue))
            )
        ) {
            Text(text = "선택")
        }
    }
}

@Composable
fun CustomStrokeColorImage(
    imageId: Int,
    strokeColor: Color,
    onClick: () -> Unit
) {
    val imageVector = ImageVector.vectorResource(id = imageId)
    val painter = rememberVectorPainter(imageVector)

    Canvas(
        modifier = Modifier
            .size(30.dp)
            .clickable {
                onClick()
            }
    ) {
        with(painter) {
            draw(
                size = size,
                colorFilter = ColorFilter.tint(strokeColor)
            )
        }
    }
}

@Preview
@Composable
fun PreviewTimePicker(modifier: Modifier = Modifier) {
    TimePicker(
        onTimeSelected = { }
    )
}
