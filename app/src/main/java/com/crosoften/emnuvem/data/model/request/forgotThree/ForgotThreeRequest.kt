package com.crosoften.emnuvem.data.model.request.forgotThree

data class ForgotThreeRequest(
    val code: String,
    val password: String,
    val confirmPassword: String
)