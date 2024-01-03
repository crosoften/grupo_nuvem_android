package com.crosoften.emnuvem.service

import com.crosoften.emnuvem.model.request.Login
import com.crosoften.emnuvem.model.response.loginResponse.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Service {
    @POST("v1/sessions/standard")
    fun login(
        @Body login: Login
    ): Call<LoginResponse>


}