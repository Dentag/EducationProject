package com.dentag.educationproject.ui.home.elm.model

import com.dentag.educationproject.model.HomeWork
import com.dentag.educationproject.model.Lesson

data class HomeState(
    val lessons: List<Lesson> = listOf(),
    val homeworks: List<HomeWork> = listOf(),
    val nextExamTime: Long = 0
)