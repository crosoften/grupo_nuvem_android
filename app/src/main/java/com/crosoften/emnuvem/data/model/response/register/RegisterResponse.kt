package com.crosoften.emnuvem.data.model.response.register

data class RegisterResponse(
    val registerResponseAddress: RegisterResponseAddress,
    val cameras: List<Any>,
    val createdAt: String,
    val document: String,
    val email: String,
    val id: Int,
    val image: String?,
    val imageKey: String?,
    val name: String,
    val phone: String,
    val status: String,
    val type: String,
    val updatedAt: String
)