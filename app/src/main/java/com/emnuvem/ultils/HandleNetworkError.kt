package com.emnuvem.ultils

import retrofit2.HttpException

class HandleNetworkError {

    fun handleLogin(e: Throwable): String{
        return when (e) {
            is HttpException -> {
                when (e.code()) {
                    400 -> "Senha deve ter no mínimo 8 caracteres"
                    401 -> "E-mail ou senha inválidas"
                    404 -> "Usuário não encontrado"
                    else -> "Erro de servidor"
                }
            }
            else -> "Erro inesperado"
        }
    }
    fun handleRegister(e: Throwable): String{
        return when (e) {
            is HttpException -> {
                when (e.code()) {
                    400 -> e.message()
                    else -> "Erro de servidor"
                }
            }
            else -> "Erro inesperado"
        }
    }

    fun handleGeneric(e: Throwable): String{
        return when (e) {
            is HttpException -> {
                when (e.code()) {
                    else -> "Erro de servidor"
                }
            }
            else -> "Erro inesperado"
        }
    }
}