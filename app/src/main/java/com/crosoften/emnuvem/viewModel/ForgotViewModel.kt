package com.crosoften.emnuvem.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.crosoften.emnuvem.data.model.request.forgotOne.ForgotOneRequest
import com.crosoften.emnuvem.data.model.request.forgotThree.ForgotThreeRequest
import com.crosoften.emnuvem.data.model.request.forgotTwo.ForgotTwoRequest
import com.crosoften.emnuvem.data.model.response.addCamResponse.AddCamResponse
import com.crosoften.emnuvem.data.repository.ForgotRepository
import com.crosoften.emnuvem.ultils.Event
import com.crosoften.emnuvem.ultils.ResponseParser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotViewModel (
    private val repository: ForgotRepository
) : ViewModel() {
    val sucess: LiveData<Event<AddCamResponse?>> get() = _sucess
    private val _sucess = MutableLiveData<Event<AddCamResponse?>>()

    val error: LiveData<Event<String?>> get() = _error
    private val _error = MutableLiveData<Event<String?>>()

    fun forgotOne(forgotOneRequest: ForgotOneRequest) {
        val request = repository.forgotOne(forgotOneRequest)
        request.enqueue(object : Callback<AddCamResponse> {
            override fun onResponse(
                call: Call<AddCamResponse>,
                response: Response<AddCamResponse>
            ) {
                if (response.isSuccessful) {
                    _sucess.value = Event(response.body())
                } else {
                    _error.value = Event(ResponseParser.parseError(response))
                }
            }

            override fun onFailure(call: Call<AddCamResponse>, t: Throwable) {
                _error.value = Event(t.message)
            }
        })
    }

    fun forgotTwo(forgotTwoRequest: ForgotTwoRequest) {
        val request = repository.forgotTwo(forgotTwoRequest)
        request.enqueue(object : Callback<AddCamResponse> {
            override fun onResponse(
                call: Call<AddCamResponse>,
                response: Response<AddCamResponse>
            ) {
                if (response.isSuccessful) {
                    _sucess.value = Event(response.body())
                } else {
                    _error.value = Event(ResponseParser.parseError(response))
                }
            }

            override fun onFailure(call: Call<AddCamResponse>, t: Throwable) {
                _error.value = Event(t.message)
            }
        })
    }

    fun forgotThree(forgotThreeRequest: ForgotThreeRequest) {
        val request = repository.forgotThree(forgotThreeRequest)
        request.enqueue(object : Callback<AddCamResponse> {
            override fun onResponse(
                call: Call<AddCamResponse>,
                response: Response<AddCamResponse>
            ) {
                if (response.isSuccessful) {
                    _sucess.value = Event(response.body())
                } else {
                    _error.value = Event(ResponseParser.parseError(response))
                }
            }

            override fun onFailure(call: Call<AddCamResponse>, t: Throwable) {
                _error.value = Event(t.message)
            }
        })
    }
}