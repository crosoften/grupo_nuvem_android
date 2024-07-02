package com.crosoften.emnuvem.repository

import com.crosoften.emnuvem.model.request.ApiRequest
import com.crosoften.emnuvem.model.request.Login
import com.crosoften.emnuvem.model.request.forgotOne.ForgotOneRequest
import com.crosoften.emnuvem.model.request.forgotThree.ForgotThreeRequest
import com.crosoften.emnuvem.model.request.forgotTwo.ForgotTwoRequest
import com.crosoften.emnuvem.service.Service

class ForgotRepository {
    private val appService = ApiRequest().getService(Service::class.java)

    fun forgotOne(forgotOneRequest: ForgotOneRequest) = appService.forgotOne(forgotOneRequest)
    fun forgotTwo(forgotTwoRequest: ForgotTwoRequest) = appService.forgotTwo(forgotTwoRequest)
    fun forgotThree(forgotThreeRequest: ForgotThreeRequest) = appService.forgotThree(forgotThreeRequest)
}