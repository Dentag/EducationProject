package com.dentag.educationproject.ui.classes.elm.model

import com.dentag.educationproject.model.Lesson

data class ClassesState(
    val lessons: List<Lesson> = listOf()
)