package com.dnuv.ui.fragment.main

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dnuv.R
import com.dnuv.data.model.CameraModel
import com.dnuv.data.model.request.camera.UpdateCameraRequest
import com.dnuv.databinding.FragmentAddCameraBinding
import com.dnuv.databinding.FragmentCamerasBinding
import com.dnuv.ui.viewModel.EditCameraViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditCamera: Fragment() {
    private lateinit var binding: FragmentAddCameraBinding
    private val viewModel by activityViewModel<EditCameraViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddCameraBinding.inflate(layoutInflater)
        binding.toolbarTitle.text = getString(R.string.editar_camera)
        binding.addDeviceButton.text = getString(R.string.editar_camera)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        onCLick()
//        observe()


    }
//    private fun observe(){
//        viewModel.cameraModel.observe(viewLifecycleOwner) { model ->
//            binding.editLocal.setText(model.address)
//            binding.externIpEdit.setText(model.ip)
//            binding.camName.setText(model.name)
//            Log.d(TAG, "onViewCreated: ${model.id}")
//        }
//
//
//    }
//    private fun onCLick(){
//        binding.addDeviceButton.setOnClickListener {
//            val id = viewModel.cameraModel.value?.id ?: return@setOnClickListener
//
//            val updatedCamera = UpdateCameraRequest(
//                ip = binding.externIpEdit.text.toString(),
//                serialNumber = "123456", // Se precisar de serial
//                camera = "Minha câmera", // Se precisar desse campo
//                name = binding.camName.text.toString(),
//                description = binding.editLocal.text.toString()
//            )
//
//            viewModel.updateCamera(id, updatedCamera)
//            viewModel.updateStatus.observe(viewLifecycleOwner) { result ->
//                result.onSuccess {
//                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
//                    findNavController().popBackStack()
//                }.onFailure {
//                    Toast.makeText(requireContext(), "Erro: ${it.message}", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//
//        }
//
//    }
}