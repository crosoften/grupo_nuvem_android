package com.emnuvem.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emnuvem.data.model.request.Login
import com.emnuvem.data.model.response.loginResponse.LoginResponse
import com.emnuvem.data.model.state.UiState
import com.emnuvem.data.repository.AuthRepository
import com.emnuvem.ultils.HandleNetworkError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel (
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<LoginResponse>>(UiState.Empty())
    val uiState : StateFlow<UiState<LoginResponse>> = _uiState.asStateFlow()

    fun login(login: Login) {
        _uiState.value = UiState.Loading()
        viewModelScope.launch {
            val result = authRepository.login(login)
            _uiState.value = result.fold(
                onSuccess = { response ->
                    UiState.Success(response)
                },
                onFailure = { e ->
                    UiState.Error(HandleNetworkError().handleLogin(e))
                }
            )
        }
    }
}