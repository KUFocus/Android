package com.example.logmeet.data.api

import com.example.logmeet.data.dto.auth.api_request.AuthLoginRequest
import com.example.logmeet.data.dto.auth.api_request.AuthSignupRequest
import com.example.logmeet.data.dto.auth.api_response.BaseResponseAuthLoginResponse
import com.example.logmeet.data.dto.auth.api_response.BaseResponseAuthSignupResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthController {
    @POST("auth/signup")
    fun signup(
        @Body
        signup: AuthSignupRequest
    ): Call<BaseResponseAuthSignupResponse>

    @POST("auth/login")
    fun login(
        @Body
        login: AuthLoginRequest
    ): Call<BaseResponseAuthLoginResponse>

}