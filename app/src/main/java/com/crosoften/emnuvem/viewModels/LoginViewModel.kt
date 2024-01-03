package com.crosoften.emnuvem.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.crosoften.emnuvem.model.request.Login
import com.crosoften.emnuvem.model.response.loginResponse.LoginResponse
import com.crosoften.emnuvem.repository.LoginRepository
import com.crosoften.emnuvem.ultils.ResponseParser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel () : ViewModel() {

    val loginRepository = LoginRepository()
    val loginSucess = MutableLiveData<LoginResponse>()
    val loginError = MutableLiveData<String>()

    fun login(login: Login) {
        val request = loginRepository.login(login)
        request.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    loginSucess.postValue(response.body())
                } else {
                    loginError.postValue(ResponseParser.parseError(response))
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {//r
                loginError.postValue(t.message)
            }


        })
    }
}