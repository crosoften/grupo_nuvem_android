package com.crosoften.emnuvem.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.util.UnstableApi
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.crosoften.emnuvem.R
import com.crosoften.emnuvem.data.model.CameraModel
import com.crosoften.emnuvem.data.model.response.getCameras.Camera
import com.crosoften.emnuvem.data.model.state.UiState
import com.crosoften.emnuvem.databinding.FragmentCamerasBinding
import com.crosoften.emnuvem.ui.adapters.CameraListAdapter
import com.crosoften.emnuvem.ui.listeners.OnCameraClickListener
import com.crosoften.emnuvem.ultils.Preference
import com.crosoften.emnuvem.ultils.extensions.showError
import com.crosoften.emnuvem.viewModel.CamerasViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CamerasFragment : Fragment() {
    private var _binding: FragmentCamerasBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CameraListAdapter
    private val viewModel by viewModel<CamerasViewModel>()
    private lateinit var preferences : Preference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCamerasBinding.inflate(inflater, container, false)
        preferences = Preference(binding.root.context)
        return binding.root
    }

    @OptIn(UnstableApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSelf()
        setupMosaicButton()
        viewModelObservers()
    }

    private fun setupSelf(){
        binding.username.text = preferences.getName()
        Glide.with(requireContext())
            .load(preferences.getImage())
            .placeholder(R.drawable.profile_picture_placeholder)
            .into(binding.userPicture)
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCameras()
    }

    private fun viewModelObservers() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is UiState.Error -> {
                        showError(state.message)
                    }

                    is UiState.Success -> {
                        setupCamerasRecyclerView(state.data.cameras)
                    }

                    else -> {}
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupCamerasRecyclerView(cameras: List<Camera>) {
        adapter = CameraListAdapter()

        if(cameras.isEmpty()){
            binding.camerasRecyclerview.visibility = View.GONE
            binding.tvEmpty.visibility = View.VISIBLE
        }else{
            binding.camerasRecyclerview.visibility = View.VISIBLE
            binding.tvEmpty.visibility = View.GONE
        }

        binding.camerasRecyclerview.adapter = adapter

        val list = mutableListOf<CameraModel>()
        cameras.forEach {
            list.add(
                CameraModel(
                    ip = it.ip,
                    name = it.name,
                    address = it.description,
                    picture = ""
                )
            )
        }

        adapter.updateList(list)
        adapter.setListener(object : OnCameraClickListener {
            override fun onClick(item: CameraModel) {
                findNavController().navigate(
                    CamerasFragmentDirections.actionCamerasFragmentToLiveCameraFragment(
                        item.ip
                    )
                )
            }
        })
    }

    private fun setupMosaicButton() {
        binding.mosaicButton.setOnClickListener {
            findNavController().navigate(CamerasFragmentDirections.actionCamerasFragmentToCamerasMosaicFragment())
        }
    }
}