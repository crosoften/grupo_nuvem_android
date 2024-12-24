package com.emnuvem.data.model.response.register

data class RegisterResponseAddress(
    val city: String,
    val complement: String?,
    val createdAt: String,
    val district: String,
    val id: Int,
    val lat: String?,
    val lng: String?,
    val number: String,
    val state: String,
    val street: String,
    val updatedAt: String,
    val zipCode: String
)