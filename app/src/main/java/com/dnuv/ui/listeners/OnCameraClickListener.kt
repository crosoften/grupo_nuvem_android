package com.dnuv.ui.listeners

import com.dnuv.data.model.CameraModel

interface OnCameraClickListener {
    fun onClick(item: CameraModel, position: Int)
}