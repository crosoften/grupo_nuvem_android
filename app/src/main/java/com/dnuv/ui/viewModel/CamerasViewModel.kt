package com.dnuv.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dnuv.data.model.CameraModel
import com.dnuv.data.repository.CameraRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CamerasViewModel(
    private val repository: CameraRepository
) : ViewModel() {

    private val _cameras = MutableStateFlow(emptyList<CameraModel>())
    val cameras = _cameras.asLiveData()

    private val _selectedCameraIP = MutableStateFlow<String?>(null)
    val selectedCameraIP = _selectedCameraIP.asLiveData()

    fun setVideo(ip: String) {
        _selectedCameraIP.update { ip }
    }

    fun loadCameras() {
        viewModelScope.launch {
            val result = repository.getCameras()
            result.fold(
                onSuccess = { response ->
                    _cameras.update { response.cameras.map { it.toModel() } }
//                    _uiState.update {
//                        it.copy(
//                            isLoading = false,
//                            cameras = response.cameras.map { it.toModel() }
//                        )
//                    }
                },
                onFailure = { e ->
//                    _uiState.update {
//                        it.copy(
//                            isLoading = false,
//                            error = "Erro ao carregar câmeras"
//                        )
//                    }
                }
            )
        }
    }
}