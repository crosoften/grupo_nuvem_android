package com.dnuv.ui.viewModel

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnuv.data.model.request.user.UpdateUserRequest
import com.dnuv.data.model.response.user.UserResponse
import com.dnuv.data.repository.UserRepository
import com.dnuv.data.service.Service
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.UUID


class UserViewModel (private val repository: UserRepository, private val api: Service) : ViewModel() {

    private val _userData = MutableLiveData<UserResponse?>()
    val userData: LiveData<UserResponse?> = _userData
    private val _image = MutableLiveData<Bitmap>()
    val image: LiveData<Bitmap> = _image

    fun fetchUserData() {
        viewModelScope.launch {
            _userData.value = repository.getUser()
        }
    }

    suspend fun uploadAndUpdateUserProfile(userRequest: UpdateUserRequest, context: Context) {
        if (_image.value != null) {
            try {
                val imagePart = createImageMultipart(_image.value!!)
                val uploadResponse = api.uploadImage(imagePart)

                if (uploadResponse.isSuccessful) {
                    val imageUrl = uploadResponse.body()?.file
                    val imageKey = uploadResponse.body()?.fileKey

                    if (!imageUrl.isNullOrBlank() && !imageKey.isNullOrBlank()) {
                        val updatedRequest = userRequest.copy(
                            image = imageUrl,
                            imageKey = imageKey
                        )
                        val updateResponse = api.updateUserProfile(updatedRequest)

                        if (updateResponse.isSuccessful) {
                            Toast.makeText(
                                context, "Perfil atualizado com sucesso!", Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                context, "Falha ao atualizar o perfil, verifique a conexão e tente novamente", Toast.LENGTH_SHORT
                            ).show()
                            Log.e(
                                "ProfileUpdate",
                                "Erro ao atualizar perfil: ${updateResponse.errorBody()?.string()}"
                            )
                        }
                    }
                } else {
                    Log.e("ImageUpload", "Erro no upload: ${uploadResponse.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("UploadError", "Erro ao enviar imagem: ${e.message}")
            }
        }

        else {

            val response = api.updateUserProfile(user = userRequest)

            if (response.isSuccessful) {
                Toast.makeText(
                    context, "Perfil atualizado com sucesso!", Toast.LENGTH_SHORT
                ).show()

            } else {
                Toast.makeText(
                    context, "Falha ao atualizar o perfil, verifique a conexão e tente novamente", Toast.LENGTH_SHORT
                ).show()
                Log.d(TAG, "uploadAndUpdateUserProfile: ${response.errorBody()?.string()}")
            }
        }
    }


    suspend fun testUpdatePassword(password: String, context: Context) {
        val request = UpdateUserRequest(
            password = password
            )

        val response = api.updateUserProfile(user = request)

        if (response.isSuccessful) {
            Toast.makeText(
                context, "✅ Atualização bem-sucedida!", Toast.LENGTH_SHORT
            ).show()
        } else {
            println("❌ Erro ao atualizar usuário: ${response.errorBody()?.string()}")
        }
    }

    fun setImageBitmap(image: Bitmap){
        _image.value = image
    }
    private fun createImageMultipart(bitmap: Bitmap): MultipartBody.Part {
        val file = File.createTempFile("upload", ".jpg")
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.close()

        val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("file", file.name, requestFile)
    }


}
