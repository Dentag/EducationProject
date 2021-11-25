package com.dentag.educationproject.ui.lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.dentag.educationproject.databinding.ItemHomeworkBinding
import com.dentag.educationproject.model.HomeWork

class HomeWorkAdapter : ListAdapter<HomeWork, HomeWorkViewHolder>(differ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeWorkViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHomeworkBinding.inflate(inflater, parent, false)
        return HomeWorkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeWorkViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val differ = object : DiffUtil.ItemCallback<HomeWork>() {
            override fun areItemsTheSame(oldItem: HomeWork, newItem: HomeWork): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: HomeWork, newItem: HomeWork): Boolean {
                return oldItem == newItem
            }
        }
    }
}