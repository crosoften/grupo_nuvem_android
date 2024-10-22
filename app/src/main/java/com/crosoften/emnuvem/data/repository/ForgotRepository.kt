package com.crosoften.emnuvem.data.repository

import com.crosoften.emnuvem.data.model.request.forgotOne.ForgotOneRequest
import com.crosoften.emnuvem.data.model.request.forgotThree.ForgotThreeRequest
import com.crosoften.emnuvem.data.model.request.forgotTwo.ForgotTwoRequest
import com.crosoften.emnuvem.data.service.Service

class ForgotRepository(
    private val appService: Service
) {
    fun forgotOne(forgotOneRequest: ForgotOneRequest) = appService.forgotOne(forgotOneRequest)
    fun forgotTwo(forgotTwoRequest: ForgotTwoRequest) = appService.forgotTwo(forgotTwoRequest)
    fun forgotThree(forgotThreeRequest: ForgotThreeRequest) = appService.forgotThree(forgotThreeRequest)
}