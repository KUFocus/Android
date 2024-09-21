package com.example.logmeet.data.api

import com.example.logmeet.data.dto.auth.RequestLogin
import com.example.logmeet.data.dto.auth.RequestSignup
import com.example.logmeet.data.dto.auth.ResponseLogin
import com.example.logmeet.data.dto.auth.ResponseSignup
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthController {
    @POST("auth/signup")
    fun signup(
        @Body
        signup: RequestSignup
    ): Call<ResponseSignup>

    @POST("auth/login")
    fun login(
        @Body
        login: RequestLogin
    ): Call<ResponseLogin>

}