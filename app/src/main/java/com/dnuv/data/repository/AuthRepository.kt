package com.dnuv.data.repository

import com.dnuv.data.model.RegisterModel
import com.dnuv.data.model.request.Login
import com.dnuv.data.model.request.forgotOne.ForgotPasswordRequest
import com.dnuv.data.model.request.forgotThree.ForgotThreeRequest
import com.dnuv.data.model.request.forgotTwo.ForgotTwoRequest
import com.dnuv.data.model.response.addCamResponse.MessageResponse
import com.dnuv.data.model.response.loginResponse.LoginResponse
import com.dnuv.data.service.Service

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