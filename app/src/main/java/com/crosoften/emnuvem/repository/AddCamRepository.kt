package com.crosoften.emnuvem.repository

import com.crosoften.emnuvem.model.request.ApiRequest
import com.crosoften.emnuvem.model.request.Login
import com.crosoften.emnuvem.model.request.addCamRequest.AddCamRequest
import com.crosoften.emnuvem.service.Service

class AddCamRepository {
    private val appService = ApiRequest().getService(Service::class.java)

    fun addCam(addCamRequest: AddCamRequest) = appService.addCam(addCamRequest)
}