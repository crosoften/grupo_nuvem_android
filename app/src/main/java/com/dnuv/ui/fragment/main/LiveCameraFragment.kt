package com.dnuv.ui.fragment.main

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.dnuv.R
import com.dnuv.data.model.CameraModel
import com.dnuv.databinding.FragmentLiveCameraBinding
import com.dnuv.ui.adapters.CameraMosaicAdapter
import com.dnuv.ui.listeners.OnCameraClickListener
import com.dnuv.ui.viewModel.CamerasViewModel
import com.dnuv.ultils.Constants.CAM_PWD
import com.dnuv.ultils.Constants.CAM_USER
import ir.am3n.rtsp.client.data.SdpInfo
import ir.am3n.rtsp.client.interfaces.RtspStatusListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class LiveCameraFragment : Fragment() {
    private var _binding: FragmentLiveCameraBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModel<CamerasViewModel>()
    private val adapter by lazy { CameraMosaicAdapter(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLiveCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    override fun onDestroyView() {
        binding.svVideo?.stop()
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = GridLayoutManager(requireContext(), 3)

        viewModel.cameras.observe(viewLifecycleOwner) { cameras ->
            setupRecyclerView(cameras)
        }
        viewModel.selectedCameraIP.observe(viewLifecycleOwner) { ip ->
            setupPlayer(ip)
        }
    }

    private fun setupUi() {
        setupMenu()
        setupToolbar()
    }

    private fun setupRecyclerView(cameras: List<CameraModel>) {
        val list = mutableListOf<CameraModel>()
        cameras.forEach {
            list.add(
                CameraModel(
                    ip = it.ip,
                    name = it.name,
                    address = it.address,
                    picture = "",
                    id = it.id
                )
            )
        }

        adapter.updateList(list)
        adapter.setListener(object : OnCameraClickListener {
            override fun onClick(item: CameraModel, position: Int) {
                viewModel.setVideo(item.ip)
            }
        })
    }

    private fun setupPlayer(ip: String?) {
        CoroutineScope(Dispatchers.Main).launch {
            with(binding) {
                if (svVideo == null) return@launch

                if (ip == null) {
                    svVideo.stop()
                    return@launch
                }

                /**
                 * Se remover o delay (400), o tempo de execução faz com que
                 * o metodo init() seja chamado antes do metodo stop(),
                 * quebrando a singularidade do streaming e fazendo com que
                 * o app acumule streams abertas em simultâneo a cada troca de câmeras,
                 * levando a perda de performance e consequente crash
                 *
                 * Deve ter um jeito muito mais correto, lógico e idiomático de
                 * tratar a troca de câmeras, mas pra hoje é o que temos
                 */
                if (svVideo.isStarted()) {
                    svVideo.stop()
                    delay(400)
                }

                val uri = Uri.parse(ip)

                svVideo.init(uri, CAM_USER, CAM_PWD)
                svVideo.setStatusListener(object : RtspStatusListener {
                    override fun onConnecting() {
                        logRtsp("$uri: Connecting")
                        binding.loading?.visibility = View.VISIBLE
                        binding.svVideo?.visibility = View.INVISIBLE
                    }

                    override fun onConnected(sdpInfo: SdpInfo) {
                        logRtsp("$uri: Connected")
                        binding.loading?.visibility = View.GONE
                        binding.svVideo?.visibility = View.VISIBLE
                    }

                    override fun onFirstFrameRendered() {}

                    override fun onDisconnecting() {
                        logRtsp("$uri: Disconnecting")
                    }

                    override fun onDisconnected() {
                        logRtsp("$uri: Disconnected")
                    }

                    override fun onUnauthorized() {
                        logRtsp("$uri: Unauthorized")
                        binding.svVideo?.visibility = View.INVISIBLE
                    }

                    override fun onFailed(message: String?) {
                        logRtsp("$uri: RTSP failed with message: $message")
                        binding.svVideo?.visibility = View.INVISIBLE
                    }
                })

                svVideo.start(playVideo = true, playAudio = true)
            }
        }
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

    private fun logRtsp(message: String) {
        Log.e(LiveCameraFragment::class.simpleName, message)
    }
}