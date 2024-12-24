package com.emnuvem.data.service

import com.emnuvem.data.model.RegisterModel
import com.emnuvem.data.model.request.Login
import com.emnuvem.data.model.request.addCamRequest.AddCamRequest
import com.emnuvem.data.model.request.contactus.ContactUsRequest
import com.emnuvem.data.model.request.forgotOne.ForgotPasswordRequest
import com.emnuvem.data.model.request.forgotThree.ForgotThreeRequest
import com.emnuvem.data.model.request.forgotTwo.ForgotTwoRequest
import com.emnuvem.data.model.response.addCamResponse.MessageResponse
import com.emnuvem.data.model.response.faqs.FaqsResponse
import com.emnuvem.data.model.response.getCameras.CamerasResponse
import com.emnuvem.data.model.response.loginResponse.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Service {

    //login

    @POST("v1/sessions/standard")
    suspend fun login(
        @Body login: Login
    ): LoginResponse

    //recuperar senha

    @POST("v1/noAuth/password/forgot")
    suspend fun forgotOne(
        @Body forgotPasswordRequest: ForgotPasswordRequest
    ): MessageResponse

    @POST("v1/noAuth/password/verify-code")
    suspend fun forgotTwo(
        @Body forgotTwoRequest: ForgotTwoRequest
    ): MessageResponse
    @POST("v1/noAuth/password/reset")
    suspend fun forgotThree(
        @Body forgotThreeRequest: ForgotThreeRequest
    ): MessageResponse

    //cameras

    @POST("v1/mobile/cameras")
    suspend fun addCam(
        @Body addCamRequest: AddCamRequest
    ): MessageResponse

    @GET("v1/mobile/cameras")
    suspend fun getAllCameras(
        @Query("id") id: String? = null,
        @Query("ip") ip: String? = null,
        @Query("name") name: String? = null,
        @Query("description") description: String? = null
    ): CamerasResponse

    @POST("v1/landing-page/register-user")
    suspend fun register(
        @Body user: RegisterModel
    ): MessageResponse

    @GET("v1/noAuth/faqs")
    suspend fun getFaqs(): FaqsResponse

    @POST("v1/noAuth/contactUs")
    suspend fun sendContactUs(
        @Body contactUsRequest: ContactUsRequest
    ): MessageResponse
}