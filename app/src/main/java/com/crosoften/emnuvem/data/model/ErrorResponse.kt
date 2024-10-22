package com.crosoften.emnuvem.data.model

data class ErrorResponse(
    val erro: String?,
    val error: List<Error>?,
    val message: String?,
    val code: Int?,
    val errorsList: List<Error>?,
    val errors: String?
)