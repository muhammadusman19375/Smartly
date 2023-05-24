package com.example.smartly.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.smartly.R
import com.example.smartly.callback.SimpleCallback
import com.example.smartly.databinding.ListItemHistoryBinding
import com.example.smartly.model.ResultDbModel
import com.example.smartly.utils.gone
import com.example.smartly.utils.visible

class HistoryAdapter(private val context: Context, private val historyList: ArrayList<ResultDbModel>, private val simpleCallback: SimpleCallback) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    enum class HistoryPayLoad {
        PAYLOAD_VISIBILITY_CHANGE
    }

    inner class ViewHolder(private val binding: ListItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.tvQuestion.setOnClickListener {
                simpleCallback.optionClick(position = adapterPosition, isSelected = historyList[adapterPosition].isVisible)
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: ResultDbModel) {
            with(binding) {
                tvQuestion.text = item.question
                tvUserSelectedAnswer.text = "UserSelectedAnswer: ${item.user_selected_answer}"
                tvCorrectAnswer.text = "CorrectAnswer: ${item.correct_answer}"
                tvCategory.text = "Category: ${item.category}"
                tvEarnedScore.text = "EarnedScore: ${item.earned_score}"

                if (item.isVisible) {
                    constraintLayout.visible()
                    ivIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_up_black))
                } else {
                    constraintLayout.gone()
                    ivIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_down_black))
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("TAG", "HistoryListSize: ${historyList.size}")
        holder.bind(historyList[position])
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payload: MutableList<Any>) {
        if (payload.isNotEmpty()) {
            if (payload[0] == HistoryPayLoad.PAYLOAD_VISIBILITY_CHANGE) {
                holder.bind(historyList[position])
            }
        } else {
            super.onBindViewHolder(holder, position, payload)
        }
    }

}