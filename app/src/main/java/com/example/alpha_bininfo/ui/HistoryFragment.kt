package com.example.alpha_bininfo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alpha_bininfo.databinding.FragmentHistoryBinding
import com.example.alpha_bininfo.presentation.BinViewModel
import com.example.alpha_bininfo.ui.adapter.HistoryAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private val viewModel by viewModel<BinViewModel>()
    private val adapter by lazy { HistoryAdapter(emptyList()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        loadHistory()
    }

    private fun showPlaceholder(){
        if(adapter.itemCount == 0){
            binding.historyRecyclerView.isVisible = false
            binding.placeholder.isVisible = true
        } else {
            binding.historyRecyclerView.isVisible = true
            binding.placeholder.isVisible = false
        }
    }

    private fun setupRecyclerView() {
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.historyRecyclerView.adapter = adapter
    }



    private fun loadHistory() {
        viewModel.loadHistory()
        viewModel.history.observe(viewLifecycleOwner) { history ->
            adapter.updateList(history)
            showPlaceholder()
        }
    }


}