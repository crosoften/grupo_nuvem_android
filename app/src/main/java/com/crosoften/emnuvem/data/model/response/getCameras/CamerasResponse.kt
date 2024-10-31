package com.crosoften.emnuvem.data.model.response.getCameras

data class CamerasResponse(
    val cameras: List<Camera>,
    val count: Int
)