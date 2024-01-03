package com.crosoften.emnuvem.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.crosoften.emnuvem.model.request.Login
import com.crosoften.emnuvem.model.request.addCamRequest.AddCamRequest
import com.crosoften.emnuvem.model.response.addCamResponse.AddCamResponse
import com.crosoften.emnuvem.model.response.loginResponse.LoginResponse
import com.crosoften.emnuvem.repository.AddCamRepository
import com.crosoften.emnuvem.repository.LoginRepository
import com.crosoften.emnuvem.ultils.ResponseParser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCamViewModel () : ViewModel() {

    val loginRepository = AddCamRepository()
    val loginSucess = MutableLiveData<AddCamResponse>()
    val loginError = MutableLiveData<String>()

    fun login(addCamRequest: AddCamRequest) {
        val request = loginRepository.addCam(addCamRequest)
        request.enqueue(object : Callback<AddCamResponse> {
            override fun onResponse(call: Call<AddCamResponse>, response: Response<AddCamResponse>) {
                if (response.isSuccessful) {
                    loginSucess.postValue(response.body())
                } else {
                    loginError.postValue(ResponseParser.parseError(response))
                }
            }

            override fun onFailure(call: Call<AddCamResponse>, t: Throwable) {//r
                loginError.postValue(t.message)
            }


        })
    }
}