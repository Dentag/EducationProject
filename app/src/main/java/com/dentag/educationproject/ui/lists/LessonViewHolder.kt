package com.dentag.educationproject.ui.lists

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.dentag.educationproject.R
import com.dentag.educationproject.databinding.ItemLessonBinding
import com.dentag.educationproject.model.Lesson
import java.text.SimpleDateFormat
import java.util.*

class LessonViewHolder(
    private val binding: ItemLessonBinding,
    private val onOpenInClick: () -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(lesson: Lesson) {
        binding.itemLessonName.text = lesson.name
        val startAt = convertToString(lesson.startTimeInMillis)
        val endAt = convertToString(lesson.endTimeInMillis)
        val lessonTime = "$startAt - $endAt"
        binding.itemLessonTime.text = lessonTime
        binding.itemLessonCard.setCardBackgroundColor(
            getCardColor(
                lesson.isExtraLesson,
                binding.root.context
            )
        )
        binding.itemLessonOpenInIcon.isVisible = lesson.hasOpenIn
        binding.itemLessonOpenInIcon.setOnClickListener {
            onOpenInClick()
        }
    }

    private fun getCardColor(isExtraLesson: Boolean, context: Context): Int {
        val colorId = if (isExtraLesson) {
            R.color.light_green
        } else {
            R.color.dark_gray
        }
        return ContextCompat.getColor(context, colorId)
    }

    private fun convertToString(timeInMillis: Long): String {
        val date = Date(timeInMillis)
        return SimpleDateFormat("HH:mm", Locale("RU")).format(date)
    }
}