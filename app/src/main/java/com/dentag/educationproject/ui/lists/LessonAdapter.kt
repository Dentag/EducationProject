package com.dentag.educationproject.ui.lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.dentag.educationproject.databinding.ItemLessonBinding
import com.dentag.educationproject.model.Lesson

class LessonAdapter(
    private val onOpenInClick: () -> Unit
) : ListAdapter<Lesson, LessonViewHolder>(differ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLessonBinding.inflate(inflater, parent, false)
        return LessonViewHolder(binding, onOpenInClick)
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val differ = object : DiffUtil.ItemCallback<Lesson>() {
            override fun areItemsTheSame(oldItem: Lesson, newItem: Lesson): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Lesson, newItem: Lesson): Boolean {
                return oldItem == newItem
            }
        }
    }
}