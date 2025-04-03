package com.dnuv.ui.fragment.user

import android.Manifest
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.dnuv.R
import com.dnuv.data.model.RegisterModel
import com.dnuv.data.model.request.user.UpdateUserRequest
import com.dnuv.data.service.Service
import com.dnuv.databinding.FragmentEditDataBinding
import com.dnuv.ui.activity.auth.LoginActivity
import com.dnuv.ui.viewModel.UserViewModel
import com.dnuv.ultils.MaskEditUtil
import com.dnuv.ultils.Preference
import com.dnuv.ultils.extensions.isValidCNPJ
import com.dnuv.ultils.extensions.isValidPhone
import com.dnuv.ultils.extensions.showError
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditData: Fragment() {
    private lateinit var binding: FragmentEditDataBinding
    private val viewModel: UserViewModel by viewModel()

    private lateinit var imageLauncher: ActivityResultLauncher<Intent>
    private var isCameraSource: Boolean = false

    private lateinit var preferences: Preference


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditDataBinding.inflate(layoutInflater)
        preferences = Preference(binding.root.context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchUserData()

        viewModel.userData.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding.editName.setText(it.name)
                binding.editEmail.setText(it.email)
                Glide.with(requireContext())
                    .load(it.image)
                    .into(binding.imageView10)

                Log.d(TAG, "onViewCreated: ${preferences.getImage()}")
            }
        }
        setupMask()
        setupLoginButton()
        handleImage()
        clickListeners()
    }
    private fun setupMask() {
        binding.editCnpj.addTextChangedListener(
            MaskEditUtil.mask(
                binding.editCnpj,
                MaskEditUtil.FORMAT_CNPJ
            )
        )

        binding.editTelefone.addTextChangedListener(
            MaskEditUtil.mask(
                binding.editTelefone,
                MaskEditUtil.FORMAT_PHONE
            )
        )
    }
    private fun validateFields(): Boolean {
        var isValid = true

        val name = binding.editName.text.toString().trim()
        if (name.isEmpty()) {
            binding.editName.error = "Nome é obrigatório"
            isValid = false
        }

        val email = binding.editEmail.text.toString().trim()
        if (email.isEmpty()) {
            binding.editEmail.error = "Email é obrigatório"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editEmail.error = "Email inválido"
            isValid = false
        }

        val phone = binding.editTelefone.text.toString().trim()
        if (phone.isEmpty()) {
            binding.editTelefone.error = "Telefone é obrigatório"
            isValid = false
        } else if (!MaskEditUtil.unmask(phone).isValidPhone()) {
            binding.editTelefone.error = "Telefone inválido (deve conter 10 ou 11 dígitos)"
            isValid = false
        }

        val document = binding.editCnpj.text.toString().trim()
        if (document.isEmpty()) {
            binding.editCnpj.error = "CNPJ é obrigatório"
            isValid = false
        } else if (!document.isValidCNPJ()) {
            binding.editCnpj.error = "CNPJ inválido"
            isValid = false
        }

        return isValid
    }
    private fun validate() {
        if (validateFields()) {
            viewModel.viewModelScope.launch {
                val userRequest = UpdateUserRequest(
                    name = binding.editName.text.toString(),
                    email = binding.editEmail.text.toString(),
                )
                viewModel.uploadAndUpdateUserProfile(userRequest, binding.root.context)
            }
        }
    }
    private fun setupLoginButton() {
        binding.enviarButton.setOnClickListener {
            validate()
        }
    }

    private fun clickListeners() {
        binding.btnChangeImage.setOnClickListener {
            openPhotoDialog()
        }
    }
    private fun openPhotoDialog() {
        val options = arrayOf<CharSequence>("Abrir Galeria", "Usar Câmera")

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Escolha uma opção")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> {
                        openGallery()
                    }

                    1 -> {
                        openCamera()
                    }
                }
            }
            .show()
    }

    private fun handleImage() {
        imageLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val imageBitmap: Bitmap? = if (isCameraSource) {
                        result.data?.extras?.get("data") as? Bitmap
                    } else {
                        val imageUri = result.data?.data
                        imageUri?.let {
                            MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, it)
                        }
                    }
                    imageBitmap?.let { image ->

                        // IMAGEM
//                        viewModel.setImage(image)
                        setImageOnImageVIew(image)
                    }
                } else {
                    showError("Erro ao adicionar imagem")
                }
            }
    }

    private fun setImageOnImageVIew(image: Bitmap){
        Glide.with(requireContext())
            .load(image)
            .into(binding.imageView10)
        viewModel.setImageBitmap(image)
    }

    private fun openCamera() {
        isCameraSource = true

        val permission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        )
        if (permission == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            imageLauncher.launch(intent)
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                1001
            )
        }
    }

    private fun openGallery() {
        isCameraSource = false
        val galleryIntent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        imageLauncher.launch(galleryIntent)
    }



}