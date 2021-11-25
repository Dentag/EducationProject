package com.dentag.educationproject.ui.classes.elm

import com.dentag.educationproject.model.Lesson
import com.dentag.educationproject.repos.FakeRepositoryImpl
import com.dentag.educationproject.repos.SomeRepository
import com.dentag.educationproject.ui.classes.elm.model.ClassesCommand
import com.dentag.educationproject.ui.classes.elm.model.ClassesEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import vivid.money.elmslie.core.store.Actor

class ClassesActor(
    private val repository: SomeRepository = FakeRepositoryImpl(),
    private val ioScheduler: Scheduler = Schedulers.io(),
    private val mainScheduler: Scheduler = AndroidSchedulers.mainThread()
) : Actor<ClassesCommand, ClassesEvent> {
    override fun execute(command: ClassesCommand): Observable<ClassesEvent> = when (command) {
        ClassesCommand.LoadLessons -> repository.getLessons()
            .flatMap { sortLessonsByDate(it) }
            .subscribeOn(ioScheduler)
            .observeOn(mainScheduler)
            .mapEvents(
                { lessons -> ClassesEvent.Internal.LessonsLoadSuccessful(lessons) },
                { error -> ClassesEvent.Internal.Error(error) })
    }

    private fun sortLessonsByDate(lessons: List<Lesson>): Single<List<Lesson>> {
        return Single.fromCallable {
            val result = lessons.sortedBy { it.startTimeInMillis }
            result
        }
    }
}