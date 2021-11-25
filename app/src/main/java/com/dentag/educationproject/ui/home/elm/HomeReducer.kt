package com.dentag.educationproject.ui.home.elm

import com.dentag.educationproject.ui.home.elm.model.HomeCommand
import com.dentag.educationproject.ui.home.elm.model.HomeEffect
import com.dentag.educationproject.ui.home.elm.model.HomeEvent
import com.dentag.educationproject.ui.home.elm.model.HomeState
import vivid.money.elmslie.core.store.dsl_reducer.DslReducer

class HomeReducer : DslReducer<HomeEvent, HomeState, HomeEffect, HomeCommand>() {
    override fun Result.reduce(event: HomeEvent) = when (event) {
        is HomeEvent.Internal.DataLoadingSuccessful -> {
            state {
                copy(
                    lessons = event.lessons,
                    homeworks = event.homeworks,
                    nextExamTime = event.nextExamTime
                )
            }
        }
        is HomeEvent.Internal.Error -> {
            effects { +HomeEffect.ShowError(event.error) }
        }
        HomeEvent.Ui.FirstLoad -> {
            commands { +HomeCommand.LoadValue }
        }
    }
}