package com.crosoften.emnuvem.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.crosoften.emnuvem.R
import com.crosoften.emnuvem.adapters.CameraPointAdapter
import com.crosoften.emnuvem.databinding.FragmentLiveCameraBinding
import com.crosoften.emnuvem.mock.mockPoints


class LiveCameraFragment : Fragment() {
    private var _binding: FragmentLiveCameraBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CameraPointAdapter
    private var play = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLiveCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupToolbar()
        setupPlayButton()
        setupMenu()
        //Mock
        Glide.with(requireContext())
            .load("https://i.imgur.com/pwM4PRr.jpg")
            .optionalCenterCrop()
            .into(binding.liveCamera)
    }

    override fun onResume() {
        super.onResume()
        setupStatusBar()
    }

    override fun onPause() {
        super.onPause()
        resetStatusBar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupStatusBar() {
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        WindowInsetsControllerCompat(requireActivity().window, requireActivity().window.decorView)
            .isAppearanceLightStatusBars = false
    }

    private fun resetStatusBar() {
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        WindowInsetsControllerCompat(requireActivity().window, requireActivity().window.decorView)
            .isAppearanceLightStatusBars = true
    }

    private fun setupRecyclerView() {
        adapter = CameraPointAdapter()
        binding.recycler.adapter = adapter
        adapter.updateList(mockPoints())
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupPlayButton() {
        binding.playIcon.setOnClickListener {
            play = !play
            if (play) {
                binding.playIcon.setImageResource(R.drawable.play_icon)
            } else {
                binding.playIcon.setImageResource(R.drawable.pause_icon)
            }
        }
    }

    private fun setupMenu() {
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.save_video -> {
                    //Todo()
                    Toast.makeText(requireContext(), "Em breve...", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.screenshot -> {
                    //Todo()
                    Toast.makeText(requireContext(), "Em breve...", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> {
                    false
                }
            }
        }
    }
}