package com.crosoften.emnuvem.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.crosoften.emnuvem.adapters.VideosListAdapter
import com.crosoften.emnuvem.databinding.FragmentVideosBinding
import com.crosoften.emnuvem.mock.mockVideos

class VideosFragment : Fragment() {
    private var _binding: FragmentVideosBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: VideosListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        adapter = VideosListAdapter()
        binding.videosRecyclerview.adapter = adapter
        adapter.updateList(mockVideos())
    }
}