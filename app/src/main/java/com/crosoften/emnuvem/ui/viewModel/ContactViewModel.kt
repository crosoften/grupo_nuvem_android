package com.crosoften.emnuvem.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crosoften.emnuvem.data.model.request.contactus.ContactUsRequest
import com.crosoften.emnuvem.data.model.response.addCamResponse.MessageResponse
import com.crosoften.emnuvem.data.model.state.UiState
import com.crosoften.emnuvem.data.repository.CameraRepository
import com.crosoften.emnuvem.ultils.HandleNetworkError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ContactViewModel(
    private val repository: CameraRepository
): ViewModel() {
    private val _state = MutableStateFlow<UiState<MessageResponse>>(UiState.Empty())
    val state : StateFlow<UiState<MessageResponse>> = _state.asStateFlow()


    fun contact(contact: ContactUsRequest){
        viewModelScope.launch {
            repository.sendContactUs(contact)
                .fold(
                    onSuccess = {
                        _state.value = UiState.Success(it)
                    },
                    onFailure = {
                        _state.value = UiState.Error(HandleNetworkError().handleGeneric(it))
                    }
                )
        }
    }
}