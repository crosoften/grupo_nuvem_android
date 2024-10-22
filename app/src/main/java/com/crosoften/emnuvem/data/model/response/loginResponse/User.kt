package com.crosoften.emnuvem.data.model.response.loginResponse

data class User(
    val createdAt: String,
    val email: String,
    val id: Int,
    val image: Any,
    val imageKey: Any,
    val name: String,
    val password: String,
    val status: String,
    val type: String,
    val updatedAt: String,
    val userHasPermissions: List<Any>
)