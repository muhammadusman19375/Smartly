package com.example.smartly.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.smartly.databinding.FragmentScoreBinding

class ScoreFragment : Fragment() {
    private lateinit var binding: FragmentScoreBinding
    private val args: ScoreFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentScoreBinding.inflate(layoutInflater)
        init()
        return binding.root
    }

    private fun init() {
        setListeners()
        setData()
    }

    private fun setListeners() {
        binding.includedTopbar.ivBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.btnPlayAgain.setOnClickListener {
            val action = ScoreFragmentDirections.actionScoreFragmentToHomeFragment2()
            findNavController().navigate(action)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setData() {
        binding.includedTopbar.tvTitle.text = "Score"
        binding.tvEarnedScoreValue.text = args.earnedScore.toString()
        binding.tvTotalScoreValue.text = args.totalScore.toString()
    }
}