package com.dnuv.ui.fragment.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dnuv.R
import com.dnuv.data.model.state.UiState
import com.dnuv.databinding.FragmentRegisterAdressBinding
import com.dnuv.ui.viewModel.RegisterViewModel
import com.dnuv.ultils.MaskEditUtil
import com.dnuv.ultils.extensions.isValidZipCode
import com.dnuv.ultils.extensions.showError
import com.dnuv.ultils.states
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class RegisterAddressFragment : Fragment() {
    private var _binding: FragmentRegisterAdressBinding? = null
    private val binding get() = _binding!!
    private val viewModel : RegisterViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterAdressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLoginButton()
        observer()
        setupMask()
        stateAdapter()
    }

    private fun stateAdapter() {
        val statesAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu_item, states)
        val autoCompleteTextView = binding.editState
        autoCompleteTextView.setAdapter(statesAdapter)
    }

    private fun setupMask() {
        binding.editZipCode.addTextChangedListener(
            MaskEditUtil.mask(
                binding.editZipCode,
                MaskEditUtil.FORMAT_CEP
            )
        )
    }

    private fun observer() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is UiState.Error -> showError(state.message)
                    is UiState.Success -> {
                        findNavController().navigate(R.id.action_registerAdressFragment_to_registerCodeFragment)
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

    private fun validateAddressInputs(): Boolean {
        val zipCode = binding.editZipCode.text.toString()
        val street = binding.editStreet.text.toString()
        val district = binding.editDistrict.text.toString()
        val number = binding.editNumber.text.toString()
        val city = binding.editCity.text.toString()
        val state = binding.editState.text.toString()

        if (!zipCode.isValidZipCode()) {
            binding.editZipCode.error = "CEP inválido"
            return false
        }

        if (state.length > 2) {
            binding.editState.error = "UF deve conter apenas 2 digitos"
            return false
        }

        if (street.isBlank() || district.isBlank() || number.isBlank() || city.isBlank() || state.isBlank()) {
            return false
        }
        return true
    }

    private fun validate(){
        lifecycleScope.launch {
            val validate = validateAddressInputs()

            if(validate){
                val registerModel = viewModel.userData.first()?.copy(
                    zipCode = binding.editZipCode.text.toString(),
                    street = binding.editStreet.text.toString(),
                    district = binding.editDistrict.text.toString(),
                    number = binding.editNumber.text.toString(),
                    city = binding.editCity.text.toString(),
                    state = binding.editState.text.toString()
                )

                if (registerModel != null) {
                    viewModel.setRegisterData(registerModel)
                    viewModel.register()
                }
            }else showError("Preencha todos os campos corretamente")
        }
    }

    private fun setupLoginButton() {
        binding.loginButton.setOnClickListener {
            validate()
        }
        binding.signUpToolbarApp.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}