package com.crosoften.emnuvem.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.crosoften.emnuvem.R
import com.crosoften.emnuvem.adapters.CameraListAdapter
import com.crosoften.emnuvem.adapters.CameraMosaicAdapter
import com.crosoften.emnuvem.databinding.FragmentCamerasBinding
import com.crosoften.emnuvem.databinding.FragmentCamerasMosaicBinding
import com.crosoften.emnuvem.listeners.OnCameraClickListener
import com.crosoften.emnuvem.mock.mockCameras

class CamerasMosaicFragment : Fragment() {
    private var _binding: FragmentCamerasMosaicBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CameraMosaicAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCamerasMosaicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCamerasRecyclerView()
        setupListButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupCamerasRecyclerView() {
        adapter = CameraMosaicAdapter()
        binding.mosaicRecycler.adapter = adapter
        adapter.updateList(mockCameras())
        adapter.setListener(object : OnCameraClickListener {
            override fun onClick() {
                findNavController().navigate(CamerasMosaicFragmentDirections.actionCamerasMosaicFragmentToLiveCameraFragment())
            }
        })
    }

    private fun setupListButton() {
        binding.listButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}