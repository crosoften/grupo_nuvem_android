package com.crosoften.emnuvem.service

import com.crosoften.emnuvem.model.request.Login
import com.crosoften.emnuvem.model.request.addCamRequest.AddCamRequest
import com.crosoften.emnuvem.model.request.forgotOne.ForgotOneRequest
import com.crosoften.emnuvem.model.request.forgotThree.ForgotThreeRequest
import com.crosoften.emnuvem.model.request.forgotTwo.ForgotTwoRequest
import com.crosoften.emnuvem.model.response.addCamResponse.AddCamResponse
import com.crosoften.emnuvem.model.response.loginResponse.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Service {

    //login

    @POST("v1/sessions/standard")
    fun login(
        @Body login: Login
    ): Call<LoginResponse>

    //recuperar senha

    @POST("v1/noAuth/password/forgot")
    fun forgotOne(
        @Body forgotOneRequest: ForgotOneRequest
    ): Call<AddCamResponse>
    @POST("v1/noAuth/password/verify-code")
    fun forgotTwo(
        @Body forgotTwoRequest: ForgotTwoRequest
    ): Call<AddCamResponse>
    @POST("v1/noAuth/password/reset")
    fun forgotThree(
        @Body forgotThreeRequest: ForgotThreeRequest
    ): Call<AddCamResponse>

    //cameras

    @POST("v1/mobile/cameras")
    fun addCam(
        @Body addCamRequest: AddCamRequest
    ): Call<AddCamResponse>

//    @GET("/v1/mobile/cameras")
//    fun allCam(): Call<AddCamResponse>


}