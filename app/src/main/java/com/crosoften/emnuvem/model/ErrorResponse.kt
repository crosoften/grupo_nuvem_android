package com.crosoften.emnuvem.model

data class ErrorResponse(
    val erro: String?,
    val error: List<Error>?,
    val message: String?,
    val code: Int?,
    val errorsList: List<Error>?,
    val errors: String?
) {
    data class Error(
        val code: String?,
        val minimum: Int?,
        val type: String?,
        val inclusive: Boolean?,
        val exact: Boolean?,
        val message: String?,
        val path: List<String>?
    )

}