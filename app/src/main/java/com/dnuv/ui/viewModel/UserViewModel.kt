package com.dnuv.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnuv.data.model.request.user.UpdateUserRequest
import com.dnuv.data.model.response.user.UserResponse
import com.dnuv.data.repository.UserRepository
import com.dnuv.data.service.Service
import kotlinx.coroutines.launch


class UserViewModel (private val repository: UserRepository, private val api: Service) : ViewModel() {

    private val _userData = MutableLiveData<UserResponse?>()
    val userData: LiveData<UserResponse?> = _userData

    fun fetchUserData() {
        viewModelScope.launch {
            _userData.value = repository.getUser()
        }
    }

     suspend fun testUpdateUser(name: String, email: String) {
        val request = UpdateUserRequest(
            name = name,
            email = email,

        )

        val response = api.updateUserProfile(user = request)

        if (response.isSuccessful) {
            println("✅ Atualização bem-sucedida!")
        } else {
            println("❌ Erro ao atualizar usuário: ${response.errorBody()?.string()}")
        }
    }
}
