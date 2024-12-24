package com.emnuvem.ui.fragment.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.emnuvem.data.model.request.contactus.ContactUsRequest
import com.emnuvem.data.model.state.UiState
import com.emnuvem.databinding.FragmentContactBinding
import com.emnuvem.ui.viewModel.ContactViewModel
import com.emnuvem.ultils.Preference
import com.emnuvem.ultils.extensions.showError
import com.emnuvem.ultils.extensions.showSnackBar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class ContactFragment : Fragment() {
    private lateinit var binding: FragmentContactBinding
    private lateinit var preferences: Preference
    private val viewModel by viewModel<ContactViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactBinding.inflate(inflater, container, false)
        preferences = Preference(binding.root.context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        setupView()

        lifecycleScope.launch {
            viewModel.state.collect{ state ->
                when(state){
                    is UiState.Empty -> {}
                    is UiState.Error -> showError(state.message)
                    is UiState.Loading -> {}
                    is UiState.Success -> {
                        showSnackBar(state.data.message)
                    }
                }
            }
        }
    }

    private fun setupView() {
        binding.editName.setText(preferences.getName())
        binding.editEmail.setText(preferences.getEmail())
    }

    private fun setupClickListeners() {
        binding.enviarButton.setOnClickListener {
            if(binding.editDescription.text.toString().isEmpty()){
                binding.editDescription.error = "Por favor, insira uma descrição"
                return@setOnClickListener
            }
            viewModel.contact(
                ContactUsRequest(
                    name = binding.editName.text.toString(),
                    email = binding.editEmail.text.toString(),
                    message = binding.editDescription.text.toString(),
                    phone = "00000000000",
                )
            )
        }
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}