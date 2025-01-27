package com.dnuv.data.model.response.getCameras

data class CamerasResponse(
    val cameras: List<Camera>,
    val count: Int
)