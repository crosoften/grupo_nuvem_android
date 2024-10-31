package com.crosoften.emnuvem.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crosoften.emnuvem.data.model.RegisterModel
import com.crosoften.emnuvem.data.model.request.forgotOne.ForgotPasswordRequest
import com.crosoften.emnuvem.data.model.request.forgotThree.ForgotThreeRequest
import com.crosoften.emnuvem.data.model.request.forgotTwo.ForgotTwoRequest
import com.crosoften.emnuvem.data.model.response.addCamResponse.MessageResponse
import com.crosoften.emnuvem.data.model.state.UiState
import com.crosoften.emnuvem.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repository: AuthRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<MessageResponse>>(UiState.Empty())
    val state: StateFlow<UiState<MessageResponse>> = _state.asStateFlow()

    private val _userData = MutableStateFlow<RegisterModel?>(null)
    val userData: StateFlow<RegisterModel?> = _userData.asStateFlow()

    fun setRegisterData(registerModel: RegisterModel) {
        _userData.value = registerModel
    }

    fun register() {
        viewModelScope.launch {
            _userData.value?.let { user ->
                repository.register(user)
                    .fold(
                        onSuccess = {
                            forgotPassword(
                                ForgotPasswordRequest(
                                    email = user.email
                                )
                            )
                        },
                        onFailure = {
                            _state.value = UiState.Error(it.message ?: "falha ao registrar")
                        }
                    )
            }
        }
    }

    private fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest) {
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

    private fun forgotVerifyCode(forgotTwoRequest: ForgotTwoRequest) {
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

    private fun forgotResetPassword(forgotThreeRequest: ForgotThreeRequest) {
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