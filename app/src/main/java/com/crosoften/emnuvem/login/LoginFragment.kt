package com.crosoften.emnuvem.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.crosoften.emnuvem.R
import com.crosoften.emnuvem.main.MainActivity
import com.crosoften.emnuvem.databinding.FragmentLoginBinding
import com.crosoften.emnuvem.model.request.Login
import com.crosoften.emnuvem.ultils.Preference
import com.crosoften.emnuvem.ultils.isValidEmail
import com.crosoften.emnuvem.viewModels.LoginViewModel

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginViewModel
    lateinit var preferences : Preference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        preferences = Preference(binding.root.context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        setupLoginButton()
    }

    private fun observe() {
        viewModel.loginError.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        viewModel.loginSucess.observe(viewLifecycleOwner){
            preferences.saveIdUser(it.user.id)
            preferences.saveToken(it.token)
            Toast.makeText(requireContext(), "login realizado com sucesso", Toast.LENGTH_SHORT).show()
            startActivity(Intent(requireContext(), MainActivity::class.java))
               requireActivity().finish()
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupLoginButton() {
        binding.loginButton.setOnClickListener {
            validation()
        }
    }
    private fun validation(){

        val email = binding.email.text.toString()
        val password = binding.password.text.toString()

        if (!email.isValidEmail()){
            binding.email.error = "digite seu email"

        }else{
            binding.email.error = null
        }

        if (password.isEmpty()){
            binding.password.error = "digite sua senha"

        }else{
            binding.password.error = null
        }

        if (binding.email.error.isNullOrEmpty() && binding.password.error.isNullOrEmpty()){
            viewModel.login(
                Login(
                    email,
                    password
                )
            )
        }
    }
}