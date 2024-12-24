package com.emnuvem.ui.fragment.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.emnuvem.R
import com.emnuvem.data.model.CameraModel
import com.emnuvem.data.model.response.getCameras.Camera
import com.emnuvem.data.model.state.UiState
import com.emnuvem.databinding.FragmentLiveCameraBinding
import com.emnuvem.ui.adapters.CameraMosaicAdapter
import com.emnuvem.ui.adapters.CameraPointAdapter
import com.emnuvem.ui.listeners.OnCameraClickListener
import com.emnuvem.ui.viewModel.CamerasViewModel
import com.emnuvem.ultils.extensions.showError
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LiveCameraFragment : Fragment() {
    private var _binding: FragmentLiveCameraBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CameraPointAdapter
    private val viewModel by viewModel<CamerasViewModel>()
    private var play = true
    private lateinit var player: ExoPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLiveCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val ip = arguments?.getString("cameraIP")
//        Log.i("IpCamera", "onViewCreated: $ip")
//        if (ip != null) setupPlayer(ip)

        viewModel.setVideo(arguments?.getString("cameraIP")!!)

        handleState()
        setupToolbar()
        setupMenu()

//        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, false)
//        WindowInsetsControllerCompat(requireActivity().window,
//        requireActivity().window.decorView).isAppearanceLightStatusBars = false
    }

    private fun handleState() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is UiState.Error -> showError(state.message)
                    is UiState.Success -> {
                        setupRecyclerView(state.data.cameras)
                    }

                    else -> {}
                }
            }
        }
    }

    private fun setupPlayer(ip: String) {
        showLoading()
        player = ExoPlayer.Builder(requireContext()).build()
        binding.videoView?.player = player

//        val url = "rtsp://177.62.88.93:554"
//        val url = "rtsp://189.45.45.250:554"
//        val url = "rtsp://201.35.17.41:554"
//        val mediaItem = MediaItem.fromUri(ip.lowercase())
        val mediaItem = MediaItem.fromUri(ip)
        player.setMediaItem(mediaItem)

        player.addListener(object : Player.Listener {
            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)
                Log.e("ExoPlayerLog", "Error - OnPlayerError: ${error.errorCode}")
            }

            override fun onPlayerErrorChanged(error: PlaybackException?) {
                super.onPlayerErrorChanged(error)
                Log.e("ExoPlayerLog", "Error: ${error?.errorCode}")
            }

            override fun onIsLoadingChanged(isLoading: Boolean) {
                super.onIsLoadingChanged(isLoading)
                if (!isLoading) {
                    hideLoading()
                }
            }
        })
        player.prepare()
        player.play()
    }

    private fun showLoading() {
        binding.loading?.visibility = View.VISIBLE
        binding.videoView.visibility = View.INVISIBLE
    }

    private fun hideLoading() {
        binding.loading?.visibility = View.GONE
        binding.videoView.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()

        viewModel.video.observe(viewLifecycleOwner) { value ->
            setupPlayer(value)
        }
        viewModel.loadCameras()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setupRecyclerView(cameras: List<Camera>) {
        val adapter = CameraMosaicAdapter(requireContext())
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = GridLayoutManager(requireContext(), 3)

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
                setupPlayer(item.ip)
            }
        })
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupMenu() {
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
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