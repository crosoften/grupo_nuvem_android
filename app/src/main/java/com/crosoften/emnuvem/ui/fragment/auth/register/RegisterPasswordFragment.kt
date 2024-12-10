package com.crosoften.emnuvem.ui.fragment.auth.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.crosoften.emnuvem.R
import com.crosoften.emnuvem.data.model.request.forgotThree.ForgotThreeRequest
import com.crosoften.emnuvem.data.model.state.UiState
import com.crosoften.emnuvem.databinding.FragmentRegisterPasswordBinding
import com.crosoften.emnuvem.ui.activity.auth.LoginActivity
import com.crosoften.emnuvem.ultils.extensions.notString
import com.crosoften.emnuvem.ultils.extensions.showError
import com.crosoften.emnuvem.ui.viewModel.ForgotViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegisterPasswordFragment : Fragment() {
    private var _binding: FragmentRegisterPasswordBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<ForgotViewModel>()
    private val args: RegisterPasswordFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLoginButton()
        observe()
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when(state){
                    is UiState.Error -> showError(state.message)
                    is UiState.Success -> {
                        Toast.makeText(requireContext(),"Cadastrado com sucesso", Toast.LENGTH_LONG).show()
                        startActivity(Intent(requireContext(), LoginActivity::class.java))
                        requireActivity().finish()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun validation(){

        val password = binding.editPassword.text.toString()
        val passwordConfirm = binding.editConfPass.text.toString()


        if (!password.notString()) {
            binding.editPassword.error = "Campo vazio"
        } else {
            binding.editPassword.error = null
        }

        if (!passwordConfirm.notString()) {
            binding.editConfPass.error = "Campo vazio"
        } else {
            binding.editConfPass.error = null
        }

        if (binding.editPassword.error.isNullOrEmpty() && binding.editConfPass.error.isNullOrEmpty()){
            viewModel.forgotResetPassword(
                ForgotThreeRequest(
                    code = args.code, password, passwordConfirm
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupLoginButton() {
        binding.loginButton.setOnClickListener {
            validation()
        }
        binding.signUpToolbarApp.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}