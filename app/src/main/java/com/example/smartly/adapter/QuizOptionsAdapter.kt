package com.example.smartly.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.smartly.R
import com.example.smartly.callback.SimpleCallback
import com.example.smartly.databinding.ListItemQuizOptionsBinding
import com.example.smartly.model.OptionsModel

class QuizOptionsAdapter(private val context: Context, private val optionList: ArrayList<OptionsModel>, private val simpleCallback: SimpleCallback) :
    RecyclerView.Adapter<QuizOptionsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ListItemQuizOptionsBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                simpleCallback.optionClick(position = adapterPosition, isSelected = true)
            }
        }

        fun bind(item: OptionsModel) {
            binding.tvOption.text = item.option
            when (item.isSelected) {
                true -> binding.tvOption.background = ContextCompat.getDrawable(context, R.drawable.bg_round_4_blue6)
                false -> binding.tvOption.background = ContextCompat.getDrawable(context, R.drawable.bg_round_4_white1)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemQuizOptionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return optionList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(optionList[position])
    }

}