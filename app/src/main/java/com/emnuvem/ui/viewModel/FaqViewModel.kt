package com.emnuvem.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emnuvem.data.model.response.faqs.FaqsResponse
import com.emnuvem.data.model.state.UiState
import com.emnuvem.data.repository.CameraRepository
import com.emnuvem.ultils.HandleNetworkError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FaqViewModel(
    private val repository: CameraRepository
): ViewModel() {

    private val _state = MutableStateFlow<UiState<FaqsResponse>>(UiState.Empty())
    val state : StateFlow<UiState<FaqsResponse>> = _state.asStateFlow()

    init {
        loadFaqs()
    }

    private fun loadFaqs(){
        viewModelScope.launch {
            repository.getFaqs()
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