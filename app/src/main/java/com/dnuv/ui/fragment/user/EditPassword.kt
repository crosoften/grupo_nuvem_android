package com.dnuv.ui.fragment.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import com.dnuv.databinding.FragmentEditPasswordBinding
import com.dnuv.ui.viewModel.UserViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditPassword: Fragment() {
    private lateinit var binding: FragmentEditPasswordBinding
    private val viewModel by viewModel<UserViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditPasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        click()

    }
    private fun click() {
        binding.enviarButton.setOnClickListener {
            val password = binding.editPassword.text.toString()
            viewModel.viewModelScope.launch {
                viewModel.testUpdatePassword(password, binding.root.context)
            }
        }
    }
}