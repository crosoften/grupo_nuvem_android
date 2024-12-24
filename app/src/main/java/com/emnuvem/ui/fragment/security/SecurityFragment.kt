package com.emnuvem.ui.fragment.security

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.emnuvem.data.model.state.UiState
import com.emnuvem.databinding.FragmentSecurityBinding
import com.emnuvem.ui.adapters.FaqAdapter
import com.emnuvem.ui.viewModel.FaqViewModel
import com.emnuvem.ultils.extensions.showError
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class SecurityFragment : Fragment() {
    private var _binding: FragmentSecurityBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<FaqViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecurityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLoginButton()
        handleState()


    }

    private fun handleState() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is UiState.Empty -> {}
                    is UiState.Error -> showError(state.message)
                    is UiState.Loading -> {}
                    is UiState.Success -> {

                        val adapter = FaqAdapter(state.data.faqs)
                        binding.rvFaq.adapter = adapter
                        binding.rvFaq.layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                    }
                }
            }
        }
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