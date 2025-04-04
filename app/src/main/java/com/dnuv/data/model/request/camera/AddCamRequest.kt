package com.dnuv.data.model.request.camera

data class AddCamRequest(
    val camera: String,
    val description: String,
    val ip: String,
    val name: String,
    val password: String,
    val serialNumber: String,
    val userId: Int
)