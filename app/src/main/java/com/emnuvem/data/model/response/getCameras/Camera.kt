package com.emnuvem.data.model.response.getCameras

data class Camera(
    val camera: String,
    val createdAt: String,
    val description: String,
    val id: Int,
    val ip: String,
    val name: String,
    val serialNumber: String,
    val status: String,
    val updatedAt: String
)