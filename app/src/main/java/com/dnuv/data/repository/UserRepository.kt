package com.dnuv.data.repository

import com.dnuv.data.model.response.user.UserResponse
import com.dnuv.data.service.Service


class UserRepository (private val apiService: Service) {

    suspend fun getUser(): UserResponse? {
        return try {
            val response = apiService.getUserData()
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}
