package com.emnuvem.data.repository

import com.emnuvem.data.model.request.addCamRequest.AddCamRequest
import com.emnuvem.data.model.request.contactus.ContactUsRequest
import com.emnuvem.data.model.response.addCamResponse.MessageResponse
import com.emnuvem.data.model.response.faqs.FaqsResponse
import com.emnuvem.data.model.response.getCameras.CamerasResponse
import com.emnuvem.data.service.Service

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