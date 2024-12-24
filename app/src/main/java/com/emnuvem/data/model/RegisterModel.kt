package com.emnuvem.data.model

data class RegisterModel(
    val name: String,
    val document: String,
    val phone: String,
    val email: String,
    val zipCode: String? = null,
    val street: String? = null,
    val district: String? = null,
    val number: String? = null,
    val city: String? = null,
    val state: String? = null,
)
