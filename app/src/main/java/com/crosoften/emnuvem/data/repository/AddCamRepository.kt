package com.crosoften.emnuvem.data.repository

import com.crosoften.emnuvem.data.model.request.addCamRequest.AddCamRequest
import com.crosoften.emnuvem.data.service.Service

class AddCamRepository(
    private val appService: Service
) {
    fun addCam(addCamRequest: AddCamRequest) = appService.addCam(addCamRequest)
}