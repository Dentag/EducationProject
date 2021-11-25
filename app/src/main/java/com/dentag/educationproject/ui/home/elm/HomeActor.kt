package com.dentag.educationproject.ui.home.elm

import com.dentag.educationproject.model.Lesson
import com.dentag.educationproject.repos.FakeRepositoryImpl
import com.dentag.educationproject.repos.SomeRepository
import com.dentag.educationproject.ui.home.elm.model.HomeCommand
import com.dentag.educationproject.ui.home.elm.model.HomeEvent
import com.dentag.educationproject.ui.home.elm.model.HomeState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import vivid.money.elmslie.core.store.Actor

class HomeActor(
    private val repository: SomeRepository = FakeRepositoryImpl(),
    private val ioScheduler: Scheduler = Schedulers.io(),
    private val mainScheduler: Scheduler = AndroidSchedulers.mainThread()
) : Actor<HomeCommand, HomeEvent> {
    override fun execute(command: HomeCommand): Observable<HomeEvent> = when (command) {
        HomeCommand.LoadValue -> Single.zip(
            repository.getLessons()
                .flatMap { sortLessonsByDate(it) },
            repository.getHomeworks(),
            repository.getNextExamTime()
        ) { lessons, homeworks, nextExamTime ->
            HomeState(lessons, homeworks, nextExamTime)
        }
            .subscribeOn(ioScheduler)
            .observeOn(mainScheduler)
            .mapEvents(
                {
                    HomeEvent.Internal.DataLoadingSuccessful(
                        it.lessons,
                        it.homeworks,
                        it.nextExamTime
                    )
                },
                { error -> HomeEvent.Internal.Error(error) }
            )
    }

    private fun sortLessonsByDate(lessons: List<Lesson>): Single<List<Lesson>> {
        return Single.fromCallable {
            val result = lessons.sortedBy { it.startTimeInMillis }
            result
        }
    }
}