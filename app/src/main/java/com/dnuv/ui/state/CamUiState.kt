package com.dnuv.ui.state

import com.dnuv.data.model.CameraModel

data class CamUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val cameras: List<CameraModel> = emptyList(),
    val currentCameraIP: String? = null,
)