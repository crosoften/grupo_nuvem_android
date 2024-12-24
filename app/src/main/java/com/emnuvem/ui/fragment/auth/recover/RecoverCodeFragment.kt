package com.emnuvem.ui.fragment.auth.recover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.emnuvem.R
import com.emnuvem.data.model.request.forgotTwo.ForgotTwoRequest
import com.emnuvem.data.model.state.UiState
import com.emnuvem.databinding.FragmentRecoverCodeBinding
import com.emnuvem.ui.viewModel.ForgotViewModel
import com.emnuvem.ultils.extensions.showError
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.emnuvem.ultils.DigitValidCustomLayout

class RecoverCodeFragment : Fragment() {
    private var _binding: FragmentRecoverCodeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<ForgotViewModel>()
    private var accumulatedCode = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoverCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLayout()
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
                        findNavController().navigate(RecoverCodeFragmentDirections.actionRecoverCodeFragmentToRecoverPassWordFragment(accumulatedCode))
                    }
                    else -> {}
                }
            }
        }
    }

    private fun setupLayout() {
        binding.validLayout
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
        binding.btnContinue.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        binding.btnContinue.backgroundTintList = ContextCompat.getColorStateList(requireContext(),
            R.color.white
        )
        binding.btnContinue.isEnabled = false
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