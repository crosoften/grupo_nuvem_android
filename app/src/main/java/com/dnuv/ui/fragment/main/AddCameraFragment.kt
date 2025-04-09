package com.dnuv.ui.fragment.main

import android.R
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dnuv.data.model.request.camera.AddCamRequest
import com.dnuv.data.model.state.UiState
import com.dnuv.databinding.FragmentAddCameraBinding
import com.dnuv.ui.viewModel.AddCamViewModel
import com.dnuv.ultils.Preference
import com.dnuv.ultils.extensions.showError
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddCameraFragment : Fragment() {
    private var _binding: FragmentAddCameraBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<AddCamViewModel>()
    private lateinit var preferences: Preference
    private lateinit var fabricantesJson: JSONObject
    private var modelosAtuais: List<String> = emptyList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddCameraBinding.inflate(inflater, container, false)
        preferences = Preference(binding.root.context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        setupToolbar()

        fabricantesJson = loadRtspModelos(requireContext())

        val fabricantes = fabricantesJson.keys().asSequence().toList()

        val fabricanteAdapter = ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, fabricantes)
        binding.fabricanteDropdown.setAdapter(fabricanteAdapter)

        binding.fabricanteDropdown.setOnItemClickListener { _, _, position, _ ->
            val fabricanteSelecionado = fabricantes[position]
            val fabricanteObj = fabricantesJson.getJSONObject(fabricanteSelecionado)

            // Pega os modelos (chaves dentro do fabricante)
            val modelos = fabricanteObj.keys().asSequence()
                .filter { it != "" } // ignora o "" que é o default
                .toList()

            modelosAtuais = modelos // guarda pra usar depois

            val modeloAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, modelos)
            binding.modeloDropdown.setAdapter(modeloAdapter)
        }

        binding.modeloDropdown.setOnItemClickListener { _, _, position, _ ->
            val fabricante = binding.fabricanteDropdown.text.toString()
            val modelo = modelosAtuais[position]

            val fabricanteObj = fabricantesJson.getJSONObject(fabricante)
            val modeloObj = fabricanteObj.getJSONObject(modelo)
            val video = modeloObj.getString("video")

            val rtspUrl = video
                .replace("{|username|", binding.username.text.toString())      // ou binding.username.text
                .replace("{:|password|}", binding.password.text.toString())
                .replace("{|host|}", binding.ipAddress.text.toString())
                .replace("{:|port}", binding.rtspPort.text.toString())

            binding.rtspUrl.setText(rtspUrl)
        }



    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when(state){
                    is UiState.Success -> {
                        findNavController().popBackStack()
                    }
                    is UiState.Error -> {
                        showError(state.message)
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

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.addDeviceButton.setOnClickListener {
            val req = AddCamRequest(
                camera = binding.fabricanteDropdown.text.toString(),
                userId = preferences.getId(),
                name = binding.fabricanteDropdown.text.toString(),
                ip = binding.ipAddress.text.toString(),
                serialNumber = "12345678",
                description = binding.editLocal.text.toString(),
                password = binding.password.text.toString()
            )

            viewModel.addCamera(req)
        }
    }

    private fun loadRtspModelos(context: Context): JSONObject {
        val inputStream = context.assets.open("rtsp_modelos.json")
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val json = String(buffer, Charsets.UTF_8)
        return JSONObject(json)
    }


//    private fun validation(){
//
//        val ip = binding.ipAddress.text.toString()
//        val serie = binding.modeloDropdown.text.toString()
//        val cam = binding.fabricanteDropdown.text.toString()
//        val password = binding.password.text.toString()
//
//        if (ip.isEmpty()){
//           // Toast.makeText(requireContext(), "digite o ip", Toast.LENGTH_SHORT).show()
//            binding.ipAddress.error = "digite o ip"
//        }else{
//            binding.ipAddress.error = null
//        }
//
//
//        if (password.isEmpty()){
//            binding.password.error = "digite sua senha"
//
//        }else{
//            binding.password.error = null
//        }
//
//
//        if ( binding.internIpEdit.error.isNullOrEmpty() && binding.serie.error.isNullOrEmpty() && binding.camera.error.isNullOrEmpty() && binding.password.error.isNullOrEmpty()
//            && binding.currentPassword.error.isNullOrEmpty() &&  binding.name.error.isNullOrEmpty() &&  binding.description.error.isNullOrEmpty() ){
//            viewModel.login(
//                com.crosoften.emnuvem.data.model.request.addCamRequest.AddCamRequest(
//                    cam,
//                    desc,
//                    ip,
//                    nome,
//                    password,
//                    serie,
//                    preferences.getId()
//
//                )
//            )
//        }
//    }
    private fun clearFields() {
        binding.ipAddress.text?.clear()
        binding.rtspPort.text?.clear()
        binding.streamDropdown.text?.clear()
        binding.password.text?.clear()
        binding.modeloDropdown.text?.clear()
        binding.fabricanteDropdown.text?.clear()
    }
}