package com.crosoften.emnuvem.ui.fragment.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.crosoften.emnuvem.R
import com.crosoften.emnuvem.databinding.FragmentProfileBinding
import com.crosoften.emnuvem.ui.activity.auth.LoginActivity

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupExitButton()
        setupMyDataButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupExitButton() {
        binding.exit.setOnClickListener {
        startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }
        binding.helpCenter.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_securityFragment)
        }
        binding.termsOfUse.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_termsFragment)
        }
        binding.privacyPolicy.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_politicFragment)
        }
        binding.contact.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_contactFragment)
        }

    }

    private fun setupMyDataButton() {
        binding.myData.setOnClickListener {
            val fragment = MyDataDialogFragment()
            fragment.show(childFragmentManager, fragment.tag)
        }
    }
}