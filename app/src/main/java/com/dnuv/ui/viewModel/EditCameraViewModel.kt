package com.dnuv.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnuv.data.model.CameraModel
import com.dnuv.data.model.request.camera.UpdateCameraRequest
import com.dnuv.data.repository.CameraRepository
import kotlinx.coroutines.launch

class EditCameraViewModel(private val repository: CameraRepository): ViewModel() {
    private val _cameraModel = MutableLiveData<CameraModel>()
    val cameraModel: LiveData<CameraModel> get() = _cameraModel

    private val _updateStatus = MutableLiveData<Result<String>>()
    val updateStatus: LiveData<Result<String>> get() = _updateStatus

    private val _deleteStatus = MutableLiveData<Result<String>>()
    val deleteStatus: LiveData<Result<String>> get() = _deleteStatus


    fun setCameraModel(cameraModel: CameraModel) {
        _cameraModel.value = cameraModel
    }

    fun updateCamera(id: String, updatedCamera: UpdateCameraRequest) {
        viewModelScope.launch {
            try {
                val response = repository.updateCamera(id, updatedCamera)
                if (response.isSuccessful) {
                    _updateStatus.value = Result.success("Câmera atualizada com sucesso!")
                } else {
                    _updateStatus.value = Result.failure(Exception("Erro ao atualizar câmera"))
                }
            } catch (e: Exception) {
                _updateStatus.value = Result.failure(e)
            }
        }
    }

    fun deleteCamera(id: String) {
        viewModelScope.launch {
            try {
                val response = repository.deleteCamera(id)
                if (response.isSuccessful) {
                    _deleteStatus.value = Result.success("Câmera excluída com sucesso!")
                } else {
                    _deleteStatus.value = Result.failure(Exception("Erro ao excluir câmera"))
                }
            } catch (e: Exception) {
                _deleteStatus.value = Result.failure(e)
            }
        }
    }

}