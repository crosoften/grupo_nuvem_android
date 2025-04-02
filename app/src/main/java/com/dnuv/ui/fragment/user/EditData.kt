package com.dnuv.ui.fragment.user

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.dnuv.R
import com.dnuv.data.model.RegisterModel
import com.dnuv.data.model.request.user.UpdateUserRequest
import com.dnuv.data.service.Service
import com.dnuv.databinding.FragmentEditDataBinding
import com.dnuv.ui.activity.auth.LoginActivity
import com.dnuv.ui.viewModel.UserViewModel
import com.dnuv.ultils.MaskEditUtil
import com.dnuv.ultils.extensions.isValidCNPJ
import com.dnuv.ultils.extensions.isValidPhone
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditData: Fragment() {
    private lateinit var binding: FragmentEditDataBinding

    private val viewModel: UserViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditDataBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchUserData()

        viewModel.userData.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding.editName.setText(it.name)
                binding.editEmail.setText(it.email)

            }
        }
        setupMask()
        setupLoginButton()
    }
    private fun setupMask() {
        binding.editCnpj.addTextChangedListener(
            MaskEditUtil.mask(
                binding.editCnpj,
                MaskEditUtil.FORMAT_CNPJ
            )
        )

        binding.editTelefone.addTextChangedListener(
            MaskEditUtil.mask(
                binding.editTelefone,
                MaskEditUtil.FORMAT_PHONE
            )
        )
    }
    private fun validateFields(): Boolean {
        var isValid = true

        val name = binding.editName.text.toString().trim()
        if (name.isEmpty()) {
            binding.editName.error = "Nome é obrigatório"
            isValid = false
        }

        val email = binding.editEmail.text.toString().trim()
        if (email.isEmpty()) {
            binding.editEmail.error = "Email é obrigatório"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editEmail.error = "Email inválido"
            isValid = false
        }

        val phone = binding.editTelefone.text.toString().trim()
        if (phone.isEmpty()) {
            binding.editTelefone.error = "Telefone é obrigatório"
            isValid = false
        } else if (!MaskEditUtil.unmask(phone).isValidPhone()) {
            binding.editTelefone.error = "Telefone inválido (deve conter 10 ou 11 dígitos)"
            isValid = false
        }

        val document = binding.editCnpj.text.toString().trim()
        if (document.isEmpty()) {
            binding.editCnpj.error = "CNPJ é obrigatório"
            isValid = false
        } else if (!document.isValidCNPJ()) {
            binding.editCnpj.error = "CNPJ inválido"
            isValid = false
        }

        return isValid
    }
    private fun validate() {
        if (validateFields()) {
            viewModel.viewModelScope.launch {
                viewModel.testUpdateUser(binding.editName.text.toString(), binding.editEmail.text.toString())
            }
        }
    }
    private fun setupLoginButton() {
        binding.enviarButton.setOnClickListener {
            validate()
        }
    }



}