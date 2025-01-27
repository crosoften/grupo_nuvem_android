package com.dnuv.ui.fragment.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.dnuv.R
import com.dnuv.databinding.FragmentProfileBinding
import com.dnuv.ui.activity.auth.LoginActivity
import com.dnuv.ultils.Preference

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var preferences: Preference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        preferences = Preference(binding.root.context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.username.text = preferences.getName()
        Glide.with(requireContext())
            .load(preferences.getImage())
            .placeholder(R.drawable.profile_picture_placeholder)
            .into(binding.profilePicture)

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
            preferences.logout()
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