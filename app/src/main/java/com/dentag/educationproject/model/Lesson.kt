package com.dentag.educationproject.model

data class Lesson(
    val name: String,
    val startTimeInMillis: Long,
    val endTimeInMillis: Long,
    val isExtraLesson: Boolean,
    val hasOpenIn: Boolean
)