package com.crosoften.emnuvem.data.service

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Service {

    //login

    @POST("v1/sessions/standard")
    fun login(
        @Body login: com.crosoften.emnuvem.data.model.request.Login
    ): Call<com.crosoften.emnuvem.data.model.response.loginResponse.LoginResponse>

    //recuperar senha

    @POST("v1/noAuth/password/forgot")
    fun forgotOne(
        @Body forgotOneRequest: com.crosoften.emnuvem.data.model.request.forgotOne.ForgotOneRequest
    ): Call<com.crosoften.emnuvem.data.model.response.addCamResponse.AddCamResponse>
    @POST("v1/noAuth/password/verify-code")
    fun forgotTwo(
        @Body forgotTwoRequest: com.crosoften.emnuvem.data.model.request.forgotTwo.ForgotTwoRequest
    ): Call<com.crosoften.emnuvem.data.model.response.addCamResponse.AddCamResponse>
    @POST("v1/noAuth/password/reset")
    fun forgotThree(
        @Body forgotThreeRequest: com.crosoften.emnuvem.data.model.request.forgotThree.ForgotThreeRequest
    ): Call<com.crosoften.emnuvem.data.model.response.addCamResponse.AddCamResponse>

    //cameras

    @POST("v1/mobile/cameras")
    fun addCam(
        @Body addCamRequest: com.crosoften.emnuvem.data.model.request.addCamRequest.AddCamRequest
    ): Call<com.crosoften.emnuvem.data.model.response.addCamResponse.AddCamResponse>

//    @GET("/v1/mobile/cameras")
//    fun allCam(): Call<AddCamResponse>


}