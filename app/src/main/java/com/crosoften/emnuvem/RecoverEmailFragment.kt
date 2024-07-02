package com.crosoften.emnuvem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.crosoften.emnuvem.databinding.FragmentRecoverEmailBinding
import com.crosoften.emnuvem.databinding.FragmentRegisterAdressBinding
import com.crosoften.emnuvem.model.request.forgotOne.ForgotOneRequest
import com.crosoften.emnuvem.ultils.isValidEmail
import com.crosoften.emnuvem.viewModels.ForgotViewModel


class RecoverEmailFragment : Fragment() {
    private var _binding: FragmentRecoverEmailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ForgotViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoverEmailBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(ForgotViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                findNavController().navigate(R.id.action_recoverEmailFragment_to_recoverCodeFragment)
            }
        }
    }

    private fun validation(){

        val email = binding.email.text.toString()

        if (!email.isValidEmail()){
            binding.email.error = "Campo vazio"

        }else{
            binding.email.error = null
        }

        if (binding.email.error.isNullOrEmpty()){
            viewModel.forgotOne(
                ForgotOneRequest(
                    email
                )
            )
        }
    }
    private fun setupLoginButton() {
        binding.loginButton.setOnClickListener {
            validation()
        }
        binding.materialToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}