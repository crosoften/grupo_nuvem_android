package com.dnuv.ui.fragment.auth.register

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
import com.dnuv.data.model.request.forgotThree.ForgotThreeRequest
import com.dnuv.data.model.state.UiState
import com.dnuv.databinding.FragmentRegisterPasswordBinding
import com.dnuv.ui.activity.auth.LoginActivity
import com.dnuv.ui.viewModel.ForgotViewModel
import com.dnuv.ultils.extensions.notString
import com.dnuv.ultils.extensions.showError
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
                    is UiState.Error ->{
                        val errorMessage = when {
                            state.message.contains("404", ignoreCase = true) -> "Usuário não encontrado"
                            state.message.contains("400", ignoreCase = true) -> "Requisição inválida"
                            state.message.contains("401", ignoreCase = true) -> "Acesso não autorizado"
                            state.message.contains(
                                "403",
                                ignoreCase = true
                            ) -> "Você não tem permissão para isso"

                            state.message.contains(
                                "500",
                                ignoreCase = true
                            ) -> "Erro interno do servidor. Tente novamente mais tarde"

                            state.message.contains(
                                "timeout",
                                ignoreCase = true
                            ) -> "Tempo de resposta esgotado. Verifique sua conexão"

                            else -> "Ocorreu um erro inesperado"
                        }
                        showError(errorMessage)
                    }
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

        val finalPassword = password == passwordConfirm

        when {
            password.isNotEmpty() -> {
                binding.editPassword.error = null
            }
            passwordConfirm.isNotEmpty() -> {
                binding.editConfPass.error = null
            }
            password == passwordConfirm -> {
                viewModel.forgotResetPassword(
                    ForgotThreeRequest(
                        code = args.code, password, passwordConfirm
                    )
                )
            }
            else -> {
                showError("Verifique se os campos estão preenchidos e tente novamente")
            }
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