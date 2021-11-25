package com.dentag.educationproject.ui.lists

import androidx.recyclerview.widget.RecyclerView
import com.dentag.educationproject.databinding.ItemHomeworkBinding
import com.dentag.educationproject.model.HomeWork

class HomeWorkViewHolder(private val binding: ItemHomeworkBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(homework: HomeWork) {
        binding.itemHomeworkName.text = homework.lessonName
        binding.itemHomeworkDesc.text = homework.desc
    }
}