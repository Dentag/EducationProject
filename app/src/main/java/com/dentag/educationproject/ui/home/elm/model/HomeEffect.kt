package com.dentag.educationproject.ui.home.elm.model

sealed class HomeEffect {
    data class ShowError(val error: Throwable) : HomeEffect()
}