package com.emnuvem.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emnuvem.data.model.request.addCamRequest.AddCamRequest
import com.emnuvem.data.model.response.addCamResponse.MessageResponse
import com.emnuvem.data.model.state.UiState
import com.emnuvem.data.repository.CameraRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddCamViewModel (
    private val repository: CameraRepository
) : ViewModel() {
    private val _state = MutableStateFlow<UiState<MessageResponse>>(UiState.Empty())
    val state : StateFlow<UiState<MessageResponse>> = _state.asStateFlow()

    fun addCamera(addCamRequest: AddCamRequest) {
        viewModelScope.launch {
            val result = repository.addCamera(addCamRequest)
            result.fold(
                onSuccess = {
                    _state.value = UiState.Success(it)
                },
                onFailure = { e ->
                    _state.value = UiState.Error(e.message ?: "Erro ao adicionar câmera")
                }
            )
        }
    }
}