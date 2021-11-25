package com.dentag.educationproject.ui.classes.elm

import com.dentag.educationproject.ui.classes.elm.model.ClassesCommand
import com.dentag.educationproject.ui.classes.elm.model.ClassesEffect
import com.dentag.educationproject.ui.classes.elm.model.ClassesEvent
import com.dentag.educationproject.ui.classes.elm.model.ClassesState
import vivid.money.elmslie.core.store.dsl_reducer.DslReducer

class ClassesReducer : DslReducer<ClassesEvent, ClassesState, ClassesEffect, ClassesCommand>() {
    override fun Result.reduce(event: ClassesEvent) = when (event) {
        is ClassesEvent.Internal.Error -> {
            effects { +ClassesEffect.ShowError(event.error) }
        }
        is ClassesEvent.Internal.LessonsLoadSuccessful -> {
            state { copy(lessons = event.lessons) }
        }
        ClassesEvent.Ui.FirstLoad -> {
            commands { +ClassesCommand.LoadLessons }
        }
    }
}