package com.example.smartly.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.smartly.adapter.QuizOptionsAdapter
import com.example.smartly.base.BaseFragment
import com.example.smartly.callback.SimpleCallback
import com.example.smartly.databinding.FragmentQuickModeBinding
import com.example.smartly.model.OptionsModel
import com.example.smartly.model.ResultModel
import com.example.smartly.networking.NetworkResult
import com.example.smartly.ui.viewModel.QuickModeFragmentVM

class QuickModeFragment : BaseFragment() {
    private lateinit var binding: FragmentQuickModeBinding
    private val quickModeFragmentVM: QuickModeFragmentVM by viewModels()
    private val dataList: ArrayList<ResultModel> = ArrayList()
    private val optionsList: ArrayList<OptionsModel> = ArrayList()
    private val shuffleOptionsList: ArrayList<OptionsModel> = ArrayList()
    private lateinit var adapter: QuizOptionsAdapter
    private lateinit var countDownTimer: CountDownTimer
    private var isOptionSelected: Boolean = false
    private var selectedOptionPosition: Int = -1
    private var currentIndex = 0
    private var remainingLife = 3
    private var earnedScore = 0
    private var totalScore = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentQuickModeBinding.inflate(layoutInflater)
        init()
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        quickModeFragmentVM.remainingLife(remainingLife)
        quickModeFragmentVM.getUnlimitedQuestion()
        binding.includedTopbar.tvTitle.text = "Quick Mode"
        setAdapter()
        setListeners()
        setCountDownTimer()
        setObserver()
        observeRemainingLife()
    }

    @SuppressLint("SetTextI18n")
    private fun observeRemainingLife() {
        quickModeFragmentVM.remainingLife.observe(viewLifecycleOwner) { response ->
            binding.tvRemainingLife.text = "Remaining life: $response"
            if (response == 0) {
                binding.lottieQuiz.pauseAnimation()
                val action = QuickModeFragmentDirections.actionQuickModeFragmentToScoreFragment(totalScore, earnedScore)
                findNavController().navigate(action)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setObserver() {
        quickModeFragmentVM.questionsResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideProgress()
                    binding.lottieQuiz.playAnimation()
                    if (response.data!!.response_code == 0) {
                        Log.d("TAG", "Response Result List Size: ${response.data.results.size}")
                        dataList.clear()
                        dataList.addAll(response.data.results)
                        countDownTimer.start()
                        binding.tvQuestion.text = dataList[currentIndex].question
                        goToNextQuestion()
                    }
                }
                is NetworkResult.Loading -> {
                    showProgress()
                }
                is NetworkResult.Error -> {
                    hideProgress()
                    showErrorDialog(response.message!!)
                }
                is NetworkResult.NetworkError -> {
                    hideProgress()
                    showErrorDialog(response.value!!)
                }
                is NetworkResult.ServerError -> {
                    hideProgress()
                    showErrorDialog(response.value!!)
                }
                is NetworkResult.TimeOutError -> {
                    hideProgress()
                    showErrorDialog(response.value!!)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setListeners() {
        binding.includedTopbar.ivBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.btnNext.setOnClickListener {
            if (isOptionSelected) {
                calculateEarnedScore()
                calculateTotalScore()
                isAnswerCorrect()
                countDownTimer.start()
                isOptionSelected = false
                Log.d("TAG", "Correct Answer ${dataList[currentIndex].correct_answer}")
                Log.d("TAG", "User selected answer ${shuffleOptionsList[selectedOptionPosition].option}")
                currentIndex++
                if (remainingLife > 0) {
                    binding.tvQuestion.text = dataList[currentIndex].question
                    goToNextQuestion()
                }
            }
        }
    }

    private fun setCountDownTimer() {
        countDownTimer = object : CountDownTimer(5000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                binding.tvTimer.text = "Remaining time: ${(millisUntilFinished / 1000)}"
            }

            override fun onFinish() {
                if (!isOptionSelected && remainingLife > 0) {
                    Log.d("TAG", "Difficulty Level: ${dataList[currentIndex].difficulty}")
                    calculateTotalScore()

                    remainingLife -= 1
                    quickModeFragmentVM.remainingLife(remainingLife)

                    currentIndex++
                    goToNextQuestion()
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun goToNextQuestion() {
        binding.tvQuestion.text = dataList[currentIndex].question
        optionsList.clear()
        dataList[currentIndex].incorrect_answers.forEach { option ->
            optionsList.add(OptionsModel(option))
        }
        optionsList.add(OptionsModel(dataList[currentIndex].correct_answer))
        shuffleOptionsList.clear()
        shuffleOptionsList.addAll(optionsList.shuffled())
        adapter.notifyDataSetChanged()
        countDownTimer.start()
    }

    private fun calculateEarnedScore() {
        if (currentIndex < dataList.size && dataList[currentIndex].correct_answer == shuffleOptionsList[selectedOptionPosition].option) {
            earnedScore += if (dataList[currentIndex].difficulty.lowercase() == "easy") {
                1
            } else if (dataList[currentIndex].difficulty.lowercase() == "medium") {
                2
            } else {
                3
            }
            Log.d("TAG", "calculateEarnedScore: $earnedScore")
        }
    }

    private fun calculateTotalScore() {
        totalScore += if (dataList[currentIndex].difficulty.lowercase() == "easy") {
            1
        } else if (dataList[currentIndex].difficulty.lowercase() == "medium") {
            2
        } else {
            3
        }
        Log.d("TAG", "calculateTotalScore: $totalScore")
    }

    private fun isAnswerCorrect() {
        if (dataList[currentIndex].question != shuffleOptionsList[selectedOptionPosition].option) {
            if (remainingLife > 0) {
                remainingLife -= 1
                quickModeFragmentVM.remainingLife(remainingLife)
            }
        }
    }

    private fun setAdapter() {
        adapter = QuizOptionsAdapter(requireContext(), shuffleOptionsList, object : SimpleCallback {
            @SuppressLint("NotifyDataSetChanged")
            override fun optionClick(position: Int, isSelected: Boolean) {
                selectedOptionPosition = position
                isOptionSelected = isSelected

                for (item in shuffleOptionsList) {
                    item.isSelected = false
                }

                shuffleOptionsList[position].isSelected = isSelected
                adapter.notifyDataSetChanged()
            }

        })
        binding.rvOptions.adapter = adapter
    }

}