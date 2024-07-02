package com.crosoften.emnuvem.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.crosoften.emnuvem.databinding.FragmentAddCameraBinding
import com.crosoften.emnuvem.model.request.Login
import com.crosoften.emnuvem.model.request.addCamRequest.AddCamRequest
import com.crosoften.emnuvem.ultils.Preference
import com.crosoften.emnuvem.ultils.isValidEmail
import com.crosoften.emnuvem.viewModels.AddCamViewModel
import com.crosoften.emnuvem.viewModels.LoginViewModel

class AddCameraFragment : Fragment() {
    private var _binding: FragmentAddCameraBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AddCamViewModel
    lateinit var preferences : Preference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddCameraBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[AddCamViewModel::class.java]
        preferences = Preference(binding.root.context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        setupToolbar()
    }

    private fun observe() {
        viewModel.loginError.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        viewModel.loginSucess.observe(viewLifecycleOwner){
            clearFields()
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.addDeviceButton.setOnClickListener {
            validation()
        }
    }
    private fun validation(){

        val ip = binding.internIpEdit.text.toString()
        val serie = binding.serie.text.toString()
        val cam = binding.camera.text.toString()
        val password = binding.password.text.toString()
        val passwordAtual = binding.currentPassword.text.toString()
        val nome = binding.name.text.toString()
        val desc = binding.description.text.toString()

        if (ip.isEmpty()){
           // Toast.makeText(requireContext(), "digite o ip", Toast.LENGTH_SHORT).show()
            binding.internIpEdit.error = "digite o ip"
        }else{
            binding.internIpEdit.error = null
        }

        if (serie.isEmpty()){
           // Toast.makeText(requireContext(), "digite o numero de serie", Toast.LENGTH_SHORT).show()
            binding.serie.error = "digite o numero de serie"
        }else{
            binding.serie.error = null
        }

        if (cam.isEmpty()){

          //  Toast.makeText(requireContext(), "digite a camera", Toast.LENGTH_SHORT).show()
            binding.camera.error = "digite a camera"
        }else{
            binding.camera.error = null
        }

        if (password.isEmpty()){
            binding.password.error = "digite sua senha"

        }else{
            binding.password.error = null
        }

        if (passwordAtual.isEmpty()){
            binding.currentPassword.error = "digite sua senha atual"

        }else{
            binding.currentPassword.error = null
        }

        if (nome.isEmpty()){
           // Toast.makeText(requireContext(), "digite o nome", Toast.LENGTH_SHORT).show()
            binding.name.error = "digite sua senha atual"

        }else{
            binding.name.error = null
        }

        if (desc.isEmpty()){
           // Toast.makeText(requireContext(), "digite uma Descrição", Toast.LENGTH_SHORT).show()
            binding.description.error = "digite uma Descrição"

        }else{
            binding.description.error = null
        }



        if ( binding.internIpEdit.error.isNullOrEmpty() && binding.serie.error.isNullOrEmpty() && binding.camera.error.isNullOrEmpty() && binding.password.error.isNullOrEmpty()
            && binding.currentPassword.error.isNullOrEmpty() &&  binding.name.error.isNullOrEmpty() &&  binding.description.error.isNullOrEmpty() ){
            viewModel.login(
                AddCamRequest(
                    cam ,
                    desc ,
                    ip ,
                    nome ,
                    password ,
                    serie ,
                    preferences.getId()

                )
            )
        }
    }
    private fun clearFields() {
        binding.internIpEdit.text?.clear()
        binding.serie.text?.clear()
        binding.camera.text?.clear()
        binding.password.text?.clear()
        binding.currentPassword.text?.clear()
        binding.name.text?.clear()
        binding.description.text?.clear()
    }
}