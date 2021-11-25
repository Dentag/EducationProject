package com.dentag.educationproject.ui.classes.elm.model

sealed class ClassesEffect {
    data class ShowError(val error: Throwable) : ClassesEffect()
}