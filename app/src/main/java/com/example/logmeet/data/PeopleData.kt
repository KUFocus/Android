package com.example.logmeet.data

data class PeopleData(
    val name: String,
    var leader: Boolean, //나중에 prjId array로 변경필요
    val userId: Int,
    val email: String,
    //val projectList: Array<Int>
)
