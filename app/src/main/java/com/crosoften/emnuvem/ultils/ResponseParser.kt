package com.crosoften.emnuvem.ultils

import com.crosoften.emnuvem.data.model.ErrorResponse
import com.google.gson.Gson
import retrofit2.Response
import java.io.IOException

object ResponseParser {
    fun parseError(response: Response<*>): String {
        val gson = Gson()
        val error: String = try {
            response.errorBody()?.string() ?: "Erro desconhecido"
        } catch (e: IOException) {
            return "Erro de leitura do response"
        }

        val errorResponse: ErrorResponse? = try {
            gson.fromJson(error, ErrorResponse::class.java)
        } catch (e: Exception) {
            return "Erro desconhecido"
        }

        // Verifique se há uma mensagem de erro no campo "error".
        val errorField = errorResponse?.errors
        if (!errorField.isNullOrBlank()) {
            return errorField
        }

        // Se não houver uma mensagem de erro em "error", retorne "Erro desconhecido".
        return "Erro desconhecido"
    }
}