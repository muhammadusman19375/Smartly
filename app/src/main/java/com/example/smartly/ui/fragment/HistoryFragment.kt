package com.example.smartly.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.smartly.adapter.HistoryAdapter
import com.example.smartly.callback.SimpleCallback
import com.example.smartly.databinding.FragmentHistoryBinding
import com.example.smartly.model.ResultDbModel
import com.example.smartly.ui.viewModel.HistoryFragmentVM
import com.example.smartly.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private val historyFragmentVM: HistoryFragmentVM by viewModels()
    private val historyList: ArrayList<ResultDbModel> = ArrayList()
    private lateinit var adapter: HistoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHistoryBinding.inflate(layoutInflater)
        init()
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        historyFragmentVM.getData()
        binding.tvTitle.text = "History"
        setListeners()
        setObserver()
        setAdapter()
    }

    private fun setListeners() {
        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setObserver() {
        historyFragmentVM.getData.observe(viewLifecycleOwner) { response ->
            if (response.isNotEmpty()) {
                historyList.clear()
                historyList.addAll(response)
                Log.d("TAG", "setObserver Size: ${historyList.size}")
                adapter.notifyDataSetChanged()
            } else binding.tvNoDataFound.visible()
        }
    }

    private fun setAdapter() {
        adapter = HistoryAdapter(requireContext(), historyList, object : SimpleCallback {
            override fun optionClick(position: Int, isSelected: Boolean) {
                historyList[position].isVisible = !isSelected
                adapter.notifyItemChanged(position, HistoryAdapter.HistoryPayLoad.PAYLOAD_VISIBILITY_CHANGE)
            }
        })
        binding.rvHistory.adapter = adapter
    }

}