package com.crosoften.emnuvem.ui.fragment.auth.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.crosoften.emnuvem.R
import com.crosoften.emnuvem.data.model.request.forgotTwo.ForgotTwoRequest
import com.crosoften.emnuvem.data.model.state.UiState
import com.crosoften.emnuvem.databinding.FragmentRegisterCodeBinding
import com.crosoften.emnuvem.ui.fragment.auth.recover.RecoverCodeFragmentDirections
import com.crosoften.emnuvem.ultils.extensions.showError
import com.crosoften.emnuvem.ui.viewModel.ForgotViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterCodeFragment : Fragment() {
    private var _binding: FragmentRegisterCodeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<ForgotViewModel>()
    private var accumulatedCode = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLayout()
        setupLoginButton()
        observe()
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when(state){
                    is UiState.Error -> showError(state.message)
                    is UiState.Success -> {
                        findNavController().navigate(RegisterCodeFragmentDirections.actionRegisterCodeFragmentToRegisterPasswordFragment(accumulatedCode))
                    }
                    else -> {}
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupLayout() {
        val editText1 = binding.validLayout.findViewById<TextInputEditText>(R.id.first_digit)
        val editText2 = binding.validLayout.findViewById<TextInputEditText>(R.id.second_input_digit)
        val editText3 = binding.validLayout.findViewById<TextInputEditText>(R.id.third_input_digit)
        val editText4 = binding.validLayout.findViewById<TextInputEditText>(R.id.fourth_input_digit)

        editText1?.doOnTextChanged { text, start: Int, count: Int, after: Int  ->
            accumulatedCode = text.toString()
        }
        editText2?.doOnTextChanged { text,start: Int, count: Int, after: Int  ->
            accumulatedCode += text.toString()
        }

        editText3?.doOnTextChanged { text,start: Int, count: Int, after: Int  ->
            accumulatedCode += text.toString()
        }

        editText4?.doOnTextChanged { text,start: Int, count: Int, after: Int ->
            accumulatedCode += text.toString()
            if (accumulatedCode.length == 4) {
                val code = accumulatedCode
                viewModel.forgotVerifyCode(
                    ForgotTwoRequest(
                        code = code
                    )
                )
            }
        }
    }

    private fun setupLoginButton() {
        binding.btnContinue.setOnClickListener {
            viewModel.forgotVerifyCode(
                ForgotTwoRequest(
                    code = accumulatedCode
                )
            )
        }
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}