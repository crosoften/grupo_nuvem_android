package com.crosoften.emnuvem.ui.fragment.auth.register

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.crosoften.emnuvem.R
import com.crosoften.emnuvem.data.model.RegisterModel
import com.crosoften.emnuvem.databinding.FragmentRegisterDataBinding
import com.crosoften.emnuvem.ui.activity.auth.LoginActivity
import com.crosoften.emnuvem.viewModel.CamerasViewModel
import com.crosoften.emnuvem.viewModel.RegisterViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegisterDataFragment : Fragment() {
    private var _binding: FragmentRegisterDataBinding? = null
    private val binding get() = _binding!!
    private val viewModel : RegisterViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLoginButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun validate(){
        val register = RegisterModel(
            name = binding.editNome.text.toString(),
            email = binding.editEmail.text.toString(),
            phone = binding.editTelefone.text.toString(),
            document = binding.editCnpj.text.toString()
        )
        viewModel.setRegisterData(register)
        findNavController().navigate(R.id.action_registerDataFragment_to_registerAdressFragment)
    }

    private fun setupLoginButton() {
        binding.loginButton.setOnClickListener {
            validate()
        }
        binding.signUpToolbarApp.setNavigationOnClickListener {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }
    }
}