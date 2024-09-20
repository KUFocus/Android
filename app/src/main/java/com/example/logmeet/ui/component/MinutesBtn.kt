package com.example.logmeet.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.logmeet.R

@Composable
fun MakeMinutesBtn(
    image: Int,
    content: String,
) {
    Row(
        modifier = Modifier
            .width(106.dp)
            .height(40.dp)
            .background(
                color = colorResource(id = R.color.gray100),
                shape = RoundedCornerShape(size = 8.dp)
            )
            .padding(start = 10.dp, top = 7.dp, end = 11.dp, bottom = 7.dp)
    ) {
        Box(
            modifier = Modifier
                .size(26.dp)
                .background(color = Color.White, shape = RoundedCornerShape(size = 50.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .size(20.dp),
                alignment = Alignment.TopEnd,
                painter = painterResource(id = image),
                contentDescription = "이미지"
            )
        }
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .width(60.dp),
                text = content,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center,
                )
            )
        }
    }

}

@Preview
@Composable
private fun Preview() {
    MakeMinutesBtn(
        image = R.drawable.ic_photo_gray,
        content = "사진 업로드"
    )
}