package com.dentag.educationproject.ui.classes.elm.model

import com.dentag.educationproject.model.Lesson

sealed class ClassesEvent {
    sealed class Ui : ClassesEvent() {
        object FirstLoad : Ui()
    }

    sealed class Internal : ClassesEvent() {
        data class Error(val error: Throwable) : Internal()
        data class LessonsLoadSuccessful(val lessons: List<Lesson>) : Internal()
    }
}