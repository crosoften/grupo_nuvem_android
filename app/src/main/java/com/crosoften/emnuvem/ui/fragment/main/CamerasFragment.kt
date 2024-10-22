package com.crosoften.emnuvem.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.crosoften.emnuvem.ui.adapters.CameraListAdapter
import com.crosoften.emnuvem.databinding.FragmentCamerasBinding
import com.crosoften.emnuvem.ui.listeners.OnCameraClickListener

class CamerasFragment : Fragment() {
    private var _binding: FragmentCamerasBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CameraListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCamerasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCamerasRecyclerView()
        setupMosaicButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupCamerasRecyclerView() {
        adapter = CameraListAdapter()
        binding.camerasRecyclerview.adapter = adapter
        adapter.updateList(emptyList())
        adapter.setListener(object : OnCameraClickListener {
            override fun onClick() {
                findNavController().navigate(CamerasFragmentDirections.actionCamerasFragmentToLiveCameraFragment())
            }
        })
    }

    private fun setupMosaicButton() {
        binding.mosaicButton.setOnClickListener {
            findNavController().navigate(CamerasFragmentDirections.actionCamerasFragmentToCamerasMosaicFragment())
        }
    }
}