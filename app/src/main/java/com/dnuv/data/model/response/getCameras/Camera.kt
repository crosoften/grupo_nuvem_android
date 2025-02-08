package com.dnuv.data.model.response.getCameras

import com.dnuv.data.model.CameraModel

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
) {
    fun toModel() = CameraModel(
        ip = ip,
        name = name,
        address = description,
        picture = ""
    )
}