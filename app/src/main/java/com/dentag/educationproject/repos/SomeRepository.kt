package com.dentag.educationproject.repos

import com.dentag.educationproject.model.HomeWork
import com.dentag.educationproject.model.Lesson
import io.reactivex.rxjava3.core.Single

interface SomeRepository {
    fun getLessons(): Single<List<Lesson>>
    fun getHomeworks(): Single<List<HomeWork>>
    fun getNextExamTime(): Single<Long>
}