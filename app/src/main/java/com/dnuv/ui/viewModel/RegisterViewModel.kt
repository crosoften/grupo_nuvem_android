package com.dnuv.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnuv.data.model.RegisterModel
import com.dnuv.data.model.request.forgotOne.ForgotPasswordRequest
import com.dnuv.data.model.request.forgotThree.ForgotThreeRequest
import com.dnuv.data.model.request.forgotTwo.ForgotTwoRequest
import com.dnuv.data.model.response.addCamResponse.MessageResponse
import com.dnuv.data.model.state.UiState
import com.dnuv.data.repository.AuthRepository
import com.dnuv.ultils.HandleNetworkError
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
                            _state.value = UiState.Error(HandleNetworkError().handleRegister(it))
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
                        _state.value = UiState.Error(HandleNetworkError().handleRegister(it))
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
                        _state.value = UiState.Error(HandleNetworkError().handleRegister(it))
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
                        _state.value = UiState.Error(HandleNetworkError().handleRegister(it))
                    }
                )
        }
    }
}