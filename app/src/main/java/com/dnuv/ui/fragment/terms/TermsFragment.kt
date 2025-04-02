package com.dnuv.ui.fragment.terms

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dnuv.R
import com.dnuv.databinding.FragmentTermsBinding
import com.rajat.pdfviewer.util.CacheStrategy
import java.io.File


class TermsFragment : Fragment() {
    private var _binding: FragmentTermsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTermsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLoginButton()
        val pdfFile = copyPdfToCache(view.context, "termos.pdf")

        binding.textTerms.initWithFile(
        file = pdfFile,
            cacheStrategy = CacheStrategy.MAXIMIZE_PERFORMANCE
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupLoginButton() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}

fun copyPdfToCache(context: Context, assetFileName: String): File {
    val file = File(context.cacheDir, assetFileName)
    if (!file.exists()) {
        context.assets.open(assetFileName).use { inputStream ->
            file.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
    }
    return file
}