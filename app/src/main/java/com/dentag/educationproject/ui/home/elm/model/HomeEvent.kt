package com.dentag.educationproject.ui.home.elm.model

import com.dentag.educationproject.model.HomeWork
import com.dentag.educationproject.model.Lesson

sealed class HomeEvent {
    sealed class Ui : HomeEvent() {
        object FirstLoad : Ui()
    }

    sealed class Internal : HomeEvent() {
        data class Error(val error: Throwable) : Internal()
        data class DataLoadingSuccessful(
            val lessons: List<Lesson>,
            val homeworks: List<HomeWork>,
            val nextExamTime: Long
        ) : Internal()
    }
}