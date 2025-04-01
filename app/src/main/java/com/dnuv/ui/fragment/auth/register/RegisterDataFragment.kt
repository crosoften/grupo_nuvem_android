package com.dnuv.ui.fragment.auth.register

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.dnuv.R
import com.dnuv.data.model.RegisterModel
import com.dnuv.databinding.FragmentRegisterDataBinding
import com.dnuv.ui.activity.auth.LoginActivity
import com.dnuv.ui.viewModel.RegisterViewModel
import com.dnuv.ultils.MaskEditUtil
import com.dnuv.ultils.extensions.isValidCNPJ
import com.dnuv.ultils.extensions.isValidPhone
import com.dnuv.ultils.extensions.showError
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class RegisterDataFragment : Fragment() {
    private var _binding: FragmentRegisterDataBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by activityViewModel()
    private lateinit var imageLauncher: ActivityResultLauncher<Intent>
    private var isCameraSource: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLoginButton()
        handleImage()
        clickListeners()
        setupMask()
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

    private fun clickListeners() {
        binding.btnChangeImage.setOnClickListener {
            openPhotoDialog()
        }
    }

    private fun validateFields(): Boolean {
        var isValid = true

        val name = binding.editNome.text.toString().trim()
        if (name.isEmpty()) {
            binding.editNome.error = "Nome é obrigatório"
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

        if(!binding.termsCheck.isChecked){
            binding.errorText.visibility = View.VISIBLE
            isValid = false
        }
        else {
            binding.errorText.visibility = View.GONE
        }

        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun validate() {
        if (validateFields()) {
            val register = RegisterModel(
                name = binding.editNome.text.toString(),
                email = binding.editEmail.text.toString(),
                phone = MaskEditUtil.unmask(binding.editTelefone.text.toString()),
                document = MaskEditUtil.unmask(binding.editCnpj.text.toString())
            )
            viewModel.setRegisterData(register)
            findNavController().navigate(R.id.action_registerDataFragment_to_registerAdressFragment)
        }
    }

    private fun setupLoginButton() {
        binding.loginButton.setOnClickListener {
            validate()
        }
        binding.signUpToolbarApp.setNavigationOnClickListener {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
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

    private fun setImageOnImageVIew(image: Bitmap) = Glide.with(requireContext())
        .load(image)
        .into(binding.imageView10)

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