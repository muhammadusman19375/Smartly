package com.example.smartly.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.smartly.adapter.QuizOptionsAdapter
import com.example.smartly.base.BaseFragment
import com.example.smartly.callback.SimpleCallback
import com.example.smartly.databinding.FragmentQuizBinding
import com.example.smartly.model.OptionsModel
import com.example.smartly.model.ResultDbModel
import com.example.smartly.model.ResultModel
import com.example.smartly.networking.NetworkResult
import com.example.smartly.ui.viewModel.QuizFragmentVM
import com.example.smartly.utils.gone
import com.example.smartly.utils.showSnackBar
import com.example.smartly.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizFragment : BaseFragment() {
    private lateinit var binding: FragmentQuizBinding
    private val args: QuizFragmentArgs by navArgs()
    private val quizFragmentVM: QuizFragmentVM by viewModels()
    private val dataList: ArrayList<ResultModel> = ArrayList()
    private val optionList: ArrayList<OptionsModel> = ArrayList()
    private var shuffleOptionsList: ArrayList<OptionsModel> = ArrayList()
    private lateinit var adapter: QuizOptionsAdapter
    private var currentIndex = 0
    private var isOptionSelected: Boolean = false
    private var selectedOptionPosition: Int = -1
    private var score: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentQuizBinding.inflate(layoutInflater)
        init()
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        binding.includedTopbar.tvTitle.text = "Quiz"
        binding.tvScore.text = "Score: $score"
        quizFragmentVM.getQuestions(args.category, args.difficulty, args.type)
        setAdapter()
        setListeners()
        setObservers()
    }

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    private fun setObservers() {
        quizFragmentVM.questionsResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideProgress()
                    if (response.data!!.response_code == 0) {
                        dataList.clear()
                        dataList.addAll(response.data.results)

                        binding.tvDifficultyLevel.text = "Difficulty level: ${dataList[currentIndex].difficulty}"
                        binding.tvQuestion.text = dataList[currentIndex].question
                        goToNextQuestion()
                    } else {
                        requireActivity().onBackPressedDispatcher.onBackPressed()
                        binding.root.showSnackBar("No data found against this filter, Update filter")
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

    private fun setListeners() {
        binding.includedTopbar.ivBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.btnNext.setOnClickListener {
            if (isOptionSelected) {
                calculateScore()
                saveDataToDB()
                isOptionSelected = false
                currentIndex++
                if (currentIndex < dataList.size) {
                    binding.tvQuestion.text = dataList[currentIndex].question
                    goToNextQuestion()
                } else if (currentIndex == dataList.size) {
                    binding.btnNext.gone()
                    binding.btnSubmit.visible()
                }
            }
        }

        binding.btnSubmit.setOnClickListener {
            val action = QuizFragmentDirections.actionQuizFragmentToScoreFragment(getTotalScore(), score)
            findNavController().navigate(action)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun goToNextQuestion() {
        optionList.clear()
        dataList[currentIndex].incorrect_answers.forEach { option ->
            optionList.add(OptionsModel(option))
        }
        optionList.add(OptionsModel(dataList[currentIndex].correct_answer))
        shuffleOptionsList.clear()
        shuffleOptionsList.addAll(optionList.shuffled())
        adapter.notifyDataSetChanged()
    }

    private fun saveDataToDB() {
        quizFragmentVM.saveData(
            ResultDbModel(
                id = null,
                question = dataList[currentIndex].question,
                correct_answer = dataList[currentIndex].correct_answer,
                user_selected_answer = shuffleOptionsList[selectedOptionPosition].option,
                category = dataList[currentIndex].category,
                earned_score = getEarnedScorePerQuestion().toString()
            )
        )
    }

    private fun getEarnedScorePerQuestion(): Int {
        return if (dataList[currentIndex].difficulty.lowercase() == "easy" &&
            dataList[currentIndex].correct_answer == shuffleOptionsList[selectedOptionPosition].option
        ) {
            1
        } else if (dataList[currentIndex].difficulty.lowercase() == "medium" &&
            dataList[currentIndex].correct_answer == shuffleOptionsList[selectedOptionPosition].option
        ) {
            2
        } else if (dataList[currentIndex].difficulty.lowercase() == "hard" &&
            dataList[currentIndex].correct_answer == shuffleOptionsList[selectedOptionPosition].option
        ) {
            3
        } else {
            0
        }
    }

    private fun getTotalScore(): Int {
        return if (dataList[0].difficulty.lowercase() == "easy") {
            10
        } else if (dataList[0].difficulty.lowercase() == "medium") {
            20
        } else 30
    }

    private fun calculateScore() {
        if (currentIndex < dataList.size && dataList[currentIndex].correct_answer == shuffleOptionsList[selectedOptionPosition].option) {
            score += if (dataList[currentIndex].difficulty.lowercase() == "easy") {
                1
            } else if (dataList[currentIndex].difficulty.lowercase() == "medium") {
                2
            } else {
                3
            }
            binding.tvScore.text = score.toString()
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