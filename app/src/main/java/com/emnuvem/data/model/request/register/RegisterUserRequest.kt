package com.emnuvem.data.model.request.register

data class RegisterUserRequest(
    val name: String,
    val document: String,
    val phone: String,
    val email: String,
    val zipCode: String,
    val street: String,
    val district: String,
    val number: String,
    val city: String,
    val state: String
)