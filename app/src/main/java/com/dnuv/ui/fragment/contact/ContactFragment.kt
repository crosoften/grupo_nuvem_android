package com.dnuv.ui.fragment.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.dnuv.R
import com.dnuv.data.model.request.contactus.ContactUsRequest
import com.dnuv.data.model.state.UiState
import com.dnuv.databinding.FragmentContactBinding
import com.dnuv.ui.viewModel.ContactViewModel
import com.dnuv.ui.viewModel.UserViewModel
import com.dnuv.ultils.Preference
import com.dnuv.ultils.extensions.showError
import com.dnuv.ultils.extensions.showSnackBar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ContactFragment : Fragment() {
    private lateinit var binding: FragmentContactBinding
    private lateinit var preferences: Preference
    private val userViewModel by activityViewModel<UserViewModel>()
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
        userViewModel.userData.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding.editName.setText(it.name)
                binding.editEmail.setText(it.email)
            }


        }
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