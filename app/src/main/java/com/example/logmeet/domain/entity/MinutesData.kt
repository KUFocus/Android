package com.example.logmeet.domain.entity

data class MinutesData(
    val prjId: Int,
    val title: String,
    val date: String,
    val prjColor: String,
    val type: Int,
    val isShort: Boolean,
)

//type
//0: 그냥 작성
//1: 요약본 있음
//2: 사진
//3: 음성
