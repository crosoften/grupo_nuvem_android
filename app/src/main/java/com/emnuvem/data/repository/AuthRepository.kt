package com.emnuvem.data.repository

import com.emnuvem.data.model.RegisterModel
import com.emnuvem.data.model.request.Login
import com.emnuvem.data.model.request.forgotOne.ForgotPasswordRequest
import com.emnuvem.data.model.request.forgotThree.ForgotThreeRequest
import com.emnuvem.data.model.request.forgotTwo.ForgotTwoRequest
import com.emnuvem.data.model.response.addCamResponse.MessageResponse
import com.emnuvem.data.model.response.loginResponse.LoginResponse
import com.emnuvem.data.service.Service

class AuthRepository(
    private val appService: Service
) {
    suspend fun login(login: Login): Result<LoginResponse> {
        return try {
            Result.success(appService.login(login))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest): Result<MessageResponse> {
        return try {
            val result = appService.forgotOne(forgotPasswordRequest)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun forgotVerifyCode(verifyCodeRequest: ForgotTwoRequest): Result<MessageResponse> {
        return try {
            val result = appService.forgotTwo(verifyCodeRequest)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun forgotResetPassword(forgotPasswordRequest: ForgotThreeRequest): Result<MessageResponse> {
        return try {
            val result = appService.forgotThree(forgotPasswordRequest)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(registerRequest: RegisterModel): Result<MessageResponse> {
        return try {
            val result = appService.register(registerRequest)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}