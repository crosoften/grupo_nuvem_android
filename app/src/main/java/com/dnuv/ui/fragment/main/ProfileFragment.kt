package com.dnuv.ui.fragment.main

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.dnuv.R
import com.dnuv.databinding.FragmentProfileBinding
import com.dnuv.ui.activity.auth.LoginActivity
import com.dnuv.ui.viewModel.UserViewModel
import com.dnuv.ultils.Preference
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var preferences: Preference
    private val viewModel by activityViewModel<UserViewModel>()

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

        viewModel.userData.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding.username.text = it.name
                Glide.with(requireContext())
                    .load(it.image)
                    .placeholder(R.drawable.profile_picture_placeholder)
                    .into(binding.profilePicture)
            }


        }

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