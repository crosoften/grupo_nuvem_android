package com.dnuv.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnuv.data.model.request.forgotOne.ForgotPasswordRequest
import com.dnuv.data.model.request.forgotThree.ForgotThreeRequest
import com.dnuv.data.model.request.forgotTwo.ForgotTwoRequest
import com.dnuv.data.model.response.addCamResponse.MessageResponse
import com.dnuv.data.model.state.UiState
import com.dnuv.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ForgotViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<MessageResponse>>(UiState.Empty())
    val state: StateFlow<UiState<MessageResponse>> = _state.asStateFlow()

    fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest) {
        viewModelScope.launch {
            repository.forgotPassword(forgotPasswordRequest)
                .fold(
                    onSuccess = {
                        _state.value = UiState.Success(it)
                    },
                    onFailure = {
                        _state.value = UiState.Error(it.message ?: "falha ao solicitar nova senha")
                    }
                )
        }
    }

    fun forgotVerifyCode(forgotTwoRequest: ForgotTwoRequest) {
        viewModelScope.launch {
            repository.forgotVerifyCode(forgotTwoRequest)
                .fold(
                    onSuccess = {
                        _state.value = UiState.Success(it)
                    },
                    onFailure = {
                        _state.value = UiState.Error(it.message ?: "falha ao solicitar nova senha")
                    }
                )
        }
    }

    fun forgotResetPassword(forgotThreeRequest: ForgotThreeRequest) {
        viewModelScope.launch {
            repository.forgotResetPassword(forgotThreeRequest)
                .fold(
                    onSuccess = {
                        _state.value = UiState.Success(it)
                    },
                    onFailure = {
                        _state.value = UiState.Error(it.message ?: "falha ao solicitar nova senha")
                    }
                )
        }
    }
}