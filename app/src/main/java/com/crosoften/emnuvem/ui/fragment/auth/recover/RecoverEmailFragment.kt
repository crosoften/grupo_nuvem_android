package com.crosoften.emnuvem.ui.fragment.auth.recover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.crosoften.emnuvem.R
import com.crosoften.emnuvem.data.model.request.forgotOne.ForgotPasswordRequest
import com.crosoften.emnuvem.data.model.state.UiState
import com.crosoften.emnuvem.databinding.FragmentRecoverEmailBinding
import com.crosoften.emnuvem.ultils.extensions.isValidEmail
import com.crosoften.emnuvem.ultils.extensions.showError
import com.crosoften.emnuvem.ui.viewModel.ForgotViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class RecoverEmailFragment : Fragment() {
    private var _binding: FragmentRecoverEmailBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<ForgotViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoverEmailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLoginButton()
        observe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when(state){
                    is UiState.Error -> showError(state.message)
                    is UiState.Success -> {
                        findNavController().navigate(R.id.action_recoverEmailFragment_to_recoverCodeFragment)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun validation() {

        val email = binding.email.text.toString()

        if (!email.isValidEmail()) {
            binding.email.error = "Campo vazio"

        } else {
            binding.email.error = null
        }

        if (binding.email.error.isNullOrEmpty()) {
            viewModel.forgotPassword(
                ForgotPasswordRequest(
                    email
                )
            )
        }
    }

    private fun setupLoginButton() {
        binding.loginButton.setOnClickListener {
            validation()
        }
        binding.materialToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}