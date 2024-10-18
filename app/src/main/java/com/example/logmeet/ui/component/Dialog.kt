package com.example.logmeet.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.logmeet.R
import com.example.logmeet.domain.entity.FileType

@Composable
fun MakeMinutesDialog(
    title: String,
    onClickCancel: () -> Unit,
    onClick: (String) -> Unit
) {
    Dialog(
        onDismissRequest = { onClickCancel() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        )
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
        )
        {
            Column(
                modifier = Modifier
                    .width(300.dp)
                    .wrapContentHeight()
                    .background(
                        color = Color.White,
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Image(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .clickable { onClickCancel() }
                            .size(18.dp),
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "닫기버튼"
                    )
                }
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 18.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFF000000),
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    MakeMinutesBtn(image = R.drawable.ic_photo, content = "사진 업로드") {
                        onClick(FileType.PICTURE.type)
                    }
                    Spacer(modifier = Modifier.width(18.dp))
                    MakeMinutesBtn(image = R.drawable.ic_camera, content = "사진 촬영") {
                        onClick(FileType.CAMERA.type)
                    }
                }
                Row(
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    MakeMinutesBtn(image = R.drawable.ic_video, content = "녹음 업로드") {
                        onClick(FileType.VOICE.type)
                    }
                    Spacer(modifier = Modifier.width(18.dp))
                    MakeMinutesBtn(image = R.drawable.ic_microphone, content = "음성녹음") {
                        onClick(FileType.RECORD.type)
                    }
                }
                MakeMinutesBtn(image = R.drawable.ic_text, content = "직접 작성") {
                    onClick(FileType.MANUAL.type)
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    MakeMinutesDialog(title = "회의록 생성 방식을 선택해주세요", onClickCancel = { /*TODO*/ }) {
        
    }
    
}