package com.crosoften.emnuvem.data.repository

import com.crosoften.emnuvem.data.model.request.Login
import com.crosoften.emnuvem.data.service.Service

class LoginRepository(
    private val appService: Service
) {
    fun login(login : Login) = appService.login(login)
}