package com.crosoften.emnuvem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.crosoften.emnuvem.databinding.FragmentRecoverCodeBinding
import com.crosoften.emnuvem.databinding.FragmentRecoverEmailBinding
import com.crosoften.emnuvem.model.request.forgotTwo.ForgotTwoRequest
import com.crosoften.emnuvem.viewModels.ForgotViewModel
import com.google.android.material.textfield.TextInputEditText


class RecoverCodeFragment : Fragment() {
    private var _binding: FragmentRecoverCodeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ForgotViewModel
    var accumulatedCode = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoverCodeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(ForgotViewModel::class.java)
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
        viewModel.error.observe(requireActivity()) { errorMessage ->
            errorMessage.getContentIfNotHandled()?.let { response ->
            }
        }
        viewModel.sucess.observe(requireActivity()) {
            it.getContentIfNotHandled()?.let { response ->
                binding.btnContinue.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                binding.btnContinue.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.primary)
                binding.btnContinue.isEnabled = true
            }
        }
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
                viewModel.forgotTwo(
                    ForgotTwoRequest(code = code
                    )
                )
            }

            viewModel.sucess.observe(viewLifecycleOwner){ response ->
                response?.let {

                }
            }
        }
    }
    private fun setupLoginButton() {
        binding.btnContinue.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        binding.btnContinue.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.white)
        binding.btnContinue.isEnabled = false
        binding.btnContinue.setOnClickListener {
            findNavController().navigate(RecoverCodeFragmentDirections.actionRecoverCodeFragmentToRecoverPassWordFragment(accumulatedCode))
        }
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}