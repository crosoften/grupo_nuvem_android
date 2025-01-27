package com.dnuv.ui.fragment.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dnuv.R
import com.dnuv.data.model.request.Login
import com.dnuv.data.model.state.UiState
import com.dnuv.databinding.FragmentLoginBinding
import com.dnuv.ui.activity.auth.RegisterActivity
import com.dnuv.ui.activity.main.MainActivity
import com.dnuv.ui.viewModel.LoginViewModel
import com.dnuv.ultils.Preference
import com.dnuv.ultils.extensions.isValidEmail
import com.dnuv.ultils.extensions.showError
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<LoginViewModel>()
    private lateinit var preferences: Preference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        preferences = Preference(binding.root.context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uiStateHandler()
        setupLoginButton()
    }

    private fun uiStateHandler() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is UiState.Empty -> {
                        showLoading(false)
                    }

                    is UiState.Error -> {
                        showLoading(false)
                        showError(state.message)
                    }

                    is UiState.Loading -> {
                        showLoading(true)
                    }

                    is UiState.Success -> {
                        preferences.saveIdUser(state.data.user.id)
                        preferences.saveToken(state.data.token)
                        preferences.saveUserName(state.data.user.name)
                        preferences.saveUserEmail(state.data.user.email)

                        if(state.data.user.image != null){
                            preferences.saveUserImage(state.data.user.image)
                        }
                        startActivity(Intent(requireContext(), MainActivity::class.java))
                        requireActivity().finish()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(show: Boolean) {
//        if (show) {
//            TODO()
//        } else {
//            TODO()
//        }
    }

    private fun setupLoginButton() {
        binding.loginButton.setOnClickListener {

            if (fieldsValidation()) {
                viewModel.login(
                    Login(
                        binding.email.text.toString(),
                        binding.password.text.toString()
                    )
                )
            }

        }
        binding.registerButton.setOnClickListener {
            startActivity(Intent(requireContext(), RegisterActivity::class.java))
            requireActivity().finish()
        }
        binding.forgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_recoverEmailFragment)
        }
    }

    private fun fieldsValidation(): Boolean {
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        return emailValidation(email) && passwordValidation(password)
    }

    private fun passwordValidation(password: String): Boolean {
        return if (password.isEmpty()) {
            binding.passwordLayout.error = "Senha não pode estar em branco"
            false
        } else {
            binding.passwordLayout.error = null
            true
        }
    }

    private fun emailValidation(email: String): Boolean {
        return if (!email.isValidEmail()) {
            binding.emailLayout.error = "Digite seu email"
            false
        } else {
            binding.emailLayout.error = null
            true
        }
    }
}