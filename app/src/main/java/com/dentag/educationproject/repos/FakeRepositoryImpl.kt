package com.dentag.educationproject.repos

import com.dentag.educationproject.model.HomeWork
import com.dentag.educationproject.model.Lesson
import io.reactivex.rxjava3.core.Single
import java.util.*

class FakeRepositoryImpl : SomeRepository {
    override fun getLessons(): Single<List<Lesson>> {
        return Single.fromCallable {
            val list = mutableListOf<Lesson>()
            list.add(
                Lesson(
                    "expired",
                    Date().time - 100000,
                    Date().time - 90000,
                    isExtraLesson = true,
                    hasOpenIn = false
                )
            )
            (0..10).forEach {
                val name = "Lesson$it"
                val startTime = Date().time + 1000 * it
                val endTime = startTime + 100000
                list.add(Lesson(name, startTime, endTime, isExtraLesson = false, hasOpenIn = true))
            }
            list
        }
    }

    override fun getHomeworks(): Single<List<HomeWork>> {
        return Single.fromCallable {
            val list = mutableListOf<HomeWork>()
            (0..10).forEach {
                val name = "Lesson$it"
                list.add(
                    HomeWork(
                        name,
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer sed est vel lorem mattis facilisis a a tellus. Nulla augue neque, pharetra eu consectetur sed, finibus placerat magna."
                    )
                )
            }
            list
        }
    }

    override fun getNextExamTime(): Single<Long> {
        return Single.fromCallable {
            Date().time + 100000000
        }
    }
}