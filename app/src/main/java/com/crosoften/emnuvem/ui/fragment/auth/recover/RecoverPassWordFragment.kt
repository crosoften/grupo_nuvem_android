package com.crosoften.emnuvem.ui.fragment.auth.recover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.crosoften.emnuvem.R
import com.crosoften.emnuvem.data.model.request.forgotThree.ForgotThreeRequest
import com.crosoften.emnuvem.databinding.FragmentRecoverPassWordBinding
import com.crosoften.emnuvem.ultils.notString
import com.crosoften.emnuvem.viewModel.ForgotViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class RecoverPassWordFragment : Fragment() {
    private var _binding: FragmentRecoverPassWordBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<ForgotViewModel>()
    private val args: RecoverPassWordFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoverPassWordBinding.inflate(inflater, container, false)
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
                Toast.makeText(requireContext(),"Cadastrado com sucesso", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.loginFragment)
            }
        }
    }

    private fun validation(){

        val password = binding.editPassword.text.toString()
        val passwordConfirm = binding.editConfPass.text.toString()


        if (!password.notString()) {
            binding.editPassword.error = "Campo vazio"
        } else {
            binding.editPassword.error = null
        }

        if (!passwordConfirm.notString()) {
            binding.editConfPass.error = "Campo vazio"
        } else {
            binding.editConfPass.error = null
        }

        if (binding.editPassword.error.isNullOrEmpty() && binding.editConfPass.error.isNullOrEmpty()){
            viewModel.forgotThree(
                ForgotThreeRequest(
                    code = args.code, password, passwordConfirm
                )
            )
        }
    }

    private fun setupLoginButton() {
        binding.loginButton.setOnClickListener {
            validation()
        }
        binding.signUpToolbarApp.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}