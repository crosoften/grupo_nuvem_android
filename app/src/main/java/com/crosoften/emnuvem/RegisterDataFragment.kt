package com.crosoften.emnuvem

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.crosoften.emnuvem.databinding.FragmentLoginBinding
import com.crosoften.emnuvem.databinding.FragmentRegisterDataBinding
import com.crosoften.emnuvem.login.LoginActivity
import com.crosoften.emnuvem.main.MainActivity
import com.crosoften.emnuvem.model.request.Login
import com.crosoften.emnuvem.ultils.Preference
import com.crosoften.emnuvem.ultils.isValidEmail
import com.crosoften.emnuvem.viewModels.LoginViewModel


class RegisterDataFragment : Fragment() {
    private var _binding: FragmentRegisterDataBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLoginButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupLoginButton() {
        binding.loginButton.setOnClickListener {
            findNavController().navigate(R.id.action_registerDataFragment_to_registerAdressFragment)
        }
        binding.signUpToolbarApp.setNavigationOnClickListener {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }
    }
}