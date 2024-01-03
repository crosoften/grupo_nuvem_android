package com.crosoften.emnuvem.repository

import com.crosoften.emnuvem.model.request.ApiRequest
import com.crosoften.emnuvem.model.request.Login
import com.crosoften.emnuvem.service.Service

class LoginRepository {
    private val appService = ApiRequest().getService(Service::class.java)

    fun login(login : Login) = appService.login(login)
}