package com.dnuv.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.dnuv.data.model.CameraModel
import com.dnuv.databinding.FragmentCamerasMosaicBinding
import com.dnuv.ui.adapters.CameraMosaicAdapter
import com.dnuv.ui.listeners.OnCameraClickListener
import com.dnuv.ui.viewModel.CamerasViewModel

class CamerasMosaicFragment : Fragment() {
    private var _binding: FragmentCamerasMosaicBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CameraMosaicAdapter
    private val viewModel by activityViewModels<CamerasViewModel>()

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

        viewModel.cameras.observe(viewLifecycleOwner) { list ->
            adapter.updateList(list)
        }

        // Carrega as câmeras se ainda não tiverem sido carregadas
        viewModel.loadCameras()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupCamerasRecyclerView() {
        adapter = CameraMosaicAdapter(requireContext())
        binding.mosaicRecycler.adapter = adapter
        adapter.updateList(emptyList())
        adapter.setListener(object : OnCameraClickListener {
            override fun onClick(item: CameraModel, position: Int) {
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