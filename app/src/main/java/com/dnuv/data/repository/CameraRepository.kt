package com.dnuv.data.repository

import com.dnuv.data.model.request.addCamRequest.AddCamRequest
import com.dnuv.data.model.request.contactus.ContactUsRequest
import com.dnuv.data.model.response.addCamResponse.MessageResponse
import com.dnuv.data.model.response.faqs.FaqsResponse
import com.dnuv.data.model.response.getCameras.CamerasResponse
import com.dnuv.data.service.Service

class CameraRepository(
    private val appService: Service
) {
    suspend fun addCamera(addCamRequest: AddCamRequest) : Result<MessageResponse>{
        return try {
            val result = appService.addCam(addCamRequest)
            Result.success(result)
        }catch (e: Exception){
            Result.failure(e)
        }
    }


    suspend fun getCameras(
        id: String? = null,
        ip: String? = null,
        name: String? = null,
        description: String? = null
    ) : Result<CamerasResponse>{
        return try {
            val result = appService.getAllCameras(id, ip, name, description)
            Result.success(result)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun getFaqs(): Result<FaqsResponse>{
        return try {
            val result = appService.getFaqs()
            Result.success(result)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun sendContactUs(contact: ContactUsRequest): Result<MessageResponse>{
        return try {
            val result = appService.sendContactUs(contact)
            Result.success(result)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

}