package com.crosoften.emnuvem.ui.fragment.main

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.crosoften.emnuvem.databinding.DialogFragmentMyDataBinding

class MyDataDialogFragment : DialogFragment() {
    private var _binding: DialogFragmentMyDataBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFragmentMyDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Para o correto funcionamento das bordas arredondadas
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.editData.setOnClickListener {
            Toast.makeText(requireContext(), "Em breve...", Toast.LENGTH_SHORT).show()
        }
        binding.changePassword.setOnClickListener {
            Toast.makeText(requireContext(), "Em breve...", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}