package com.example.logmeet.data.api

import com.example.logmeet.data.dto.project.RequestNewprj
import com.example.logmeet.data.dto.project.ResponseNewprj
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ProjectController {
    @POST("project/new")
    fun newprj(
        @Body
        newprj : RequestNewprj
    ): Call<ResponseNewprj>
}