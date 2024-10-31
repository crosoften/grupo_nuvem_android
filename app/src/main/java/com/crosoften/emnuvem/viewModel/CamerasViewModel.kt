package com.crosoften.emnuvem.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crosoften.emnuvem.data.model.response.getCameras.CamerasResponse
import com.crosoften.emnuvem.data.model.state.UiState
import com.crosoften.emnuvem.data.repository.CameraRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CamerasViewModel (
    private val repository: CameraRepository
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<CamerasResponse>>(UiState.Empty())
    val state : StateFlow<UiState<CamerasResponse>> = _state.asStateFlow()

    fun loadCameras() {
        viewModelScope.launch {
            val result = repository.getCameras()
            result.fold(
                onSuccess = {
                    _state.value = UiState.Success(it)
                },
                onFailure = { e ->
                    _state.value = UiState.Error(e.message ?: "Erro ao carregar câmeras")
                }
            )
        }
    }

    fun getCamera(
        id: String
    ) {
        viewModelScope.launch {
            val result = repository.getCameras(
                id = id
            )
            result.fold(
                onSuccess = {
                    _state.value = UiState.Success(it)
                },
                onFailure = { e ->
                    _state.value = UiState.Error(e.message ?: "Erro ao carregar câmeras")
                }
            )
        }
    }
}