package com.dnuv.data.model.request.camera

data class UpdateCameraRequest(
    val ip: String,
    val serialNumber: String,
    val camera: String,
    val name: String,
    val description: String
)

