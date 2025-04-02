package com.dnuv.data.model.request.user

data class UpdateUserRequest(
    val name: String? = null,
    val document: String? = null,
    val phone: String ? = null,
    val email: String ? = null,
    val password: String ? = null,
    val image: String? = null,
    val imageKey: String ?= null

)
