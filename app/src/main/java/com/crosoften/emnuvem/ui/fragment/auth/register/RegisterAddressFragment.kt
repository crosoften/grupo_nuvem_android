package com.crosoften.emnuvem.ui.fragment.auth.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.crosoften.emnuvem.R
import com.crosoften.emnuvem.data.model.state.UiState
import com.crosoften.emnuvem.databinding.FragmentRegisterAdressBinding
import com.crosoften.emnuvem.ultils.extensions.showError
import com.crosoften.emnuvem.viewModel.RegisterViewModel
import kotlinx.coroutines.flow.collect
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

    private fun validate(){
        lifecycleScope.launch {
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