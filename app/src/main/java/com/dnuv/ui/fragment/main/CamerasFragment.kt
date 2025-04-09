package com.dnuv.ui.fragment.main

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.dnuv.R
import com.dnuv.data.model.CameraModel
import com.dnuv.databinding.FragmentCamerasBinding
import com.dnuv.ui.adapters.CameraListAdapter
import com.dnuv.ui.listeners.OnCameraClickListener
import com.dnuv.ui.viewModel.CamerasViewModel
import com.dnuv.ui.viewModel.EditCameraViewModel
import com.dnuv.ui.viewModel.UserViewModel
import com.dnuv.ultils.Preference
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class CamerasFragment : Fragment() {
    private var _binding: FragmentCamerasBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CameraListAdapter
    private val viewModel by activityViewModel<CamerasViewModel>()
    private val userViewModel by activityViewModel<UserViewModel>()
    private val editCameraViewModel by activityViewModel<EditCameraViewModel>()
    private lateinit var preferences: Preference
    private var originalCameraList: List<CameraModel> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCamerasBinding.inflate(inflater, container, false)
        preferences = Preference(binding.root.context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel.fetchUserData()


        setupSelf()
        setupMosaicButton()
        viewModelObservers()
        pesquisaNomeCamera()
    }

    private fun setupSelf() {
        userViewModel.userData.observe(viewLifecycleOwner, { user ->
            user?.let {
                binding.username.text = it.name
                Log.d(TAG, "setupSelf: ${preferences.getImage()}")
                Glide.with(requireContext())
                    .load(it.image)
                    .placeholder(R.drawable.profile_picture_placeholder)
                    .into(binding.userPicture)
            }


        })

    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCameras()
    }

    private fun viewModelObservers() {
        lifecycleScope.launch {
            viewModel.cameras.observe(viewLifecycleOwner) { cameras ->
                setupCamerasRecyclerView(cameras)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupCamerasRecyclerView(cameras: List<CameraModel>) {
        adapter = CameraListAdapter()

        if (cameras.isEmpty()) {
            binding.camerasRecyclerview.visibility = View.GONE
            binding.tvEmpty.visibility = View.VISIBLE
        } else {
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
                    address = it.address,
                    picture = "",
                    id = it.id
                )
            )
        }
        originalCameraList = list

        adapter.updateList(list)

        adapter.setOnItemClickListener { item, _ ->
            viewModel.setVideo(item.ip)
            findNavController().navigate(
                CamerasFragmentDirections.actionCamerasFragmentToLiveCameraFragment(item.ip)
            )
        }

        adapter.setOnEditClickListener { item, _ ->
            editCameraViewModel.setCameraModel(item)
            findNavController().navigate(R.id.editCameraFragment)
        }

        adapter.setOnDeleteClickListener { item, position ->
            val id = item.id

            AlertDialog.Builder(requireContext())
                .setTitle("Excluir Câmera")
                .setMessage("Tem certeza que deseja excluir esta câmera?")
                .setPositiveButton("Excluir") { _, _ ->
                    editCameraViewModel.deleteCamera(id)

                    editCameraViewModel.deleteStatus.observe(viewLifecycleOwner) { result ->
                        result.onSuccess {
                            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                            val updatedList = adapter.getCurrentList().filter { it.id != id }
                            adapter.updateList(updatedList)
                        }.onFailure {
                            Toast.makeText(requireContext(), "Erro: ${it.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }

    }

    private fun pesquisaNomeCamera(){
        binding.searchBar.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim().lowercase()
                val filteredList = originalCameraList.filter { it.name.lowercase().contains(query) }

                adapter.updateList(filteredList)

                // Se a lista filtrada estiver vazia, mostre uma mensagem
                if (filteredList.isEmpty()) {
                    binding.tvEmpty.text = "Nenhuma câmera encontrada"
                    binding.tvEmpty.visibility = View.VISIBLE
                    binding.camerasRecyclerview.visibility = View.GONE
                } else {
                    binding.tvEmpty.visibility = View.GONE
                    binding.camerasRecyclerview.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupMosaicButton() {
        binding.mosaicButton.setOnClickListener {
            findNavController().navigate(CamerasFragmentDirections.actionCamerasFragmentToCamerasMosaicFragment())
        }
    }
}