package com.crosoften.emnuvem

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.crosoften.emnuvem.databinding.FragmentRegisterDataBinding
import com.crosoften.emnuvem.databinding.FragmentRegisterPasswordBinding
import com.crosoften.emnuvem.login.LoginActivity


class RegisterPasswordFragment : Fragment() {
    private var _binding: FragmentRegisterPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterPasswordBinding.inflate(inflater, container, false)
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

            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }
        binding.signUpToolbarApp.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}