package com.crosoften.emnuvem.model.request

import com.crosoften.emnuvem.retrofit.RetrofitConfig

class ApiRequest {
    fun <S> getService(service: Class<S>): S {
        return RetrofitConfig.createService(service)
    }
}