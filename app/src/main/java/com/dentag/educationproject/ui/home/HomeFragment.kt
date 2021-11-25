package com.dentag.educationproject.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.dentag.educationproject.databinding.FragmentHomeBinding
import com.dentag.educationproject.model.Lesson
import com.dentag.educationproject.ui.home.elm.HomeActor
import com.dentag.educationproject.ui.home.elm.HomeReducer
import com.dentag.educationproject.ui.home.elm.model.HomeEffect
import com.dentag.educationproject.ui.home.elm.model.HomeEvent
import com.dentag.educationproject.ui.home.elm.model.HomeState
import com.dentag.educationproject.ui.lists.HomeWorkAdapter
import com.dentag.educationproject.ui.lists.LessonAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.core.store.ElmStore
import vivid.money.elmslie.core.store.Store
import java.util.*
import java.util.concurrent.TimeUnit

class HomeFragment : ElmFragment<HomeEvent, HomeEffect, HomeState>() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override val initEvent: HomeEvent = HomeEvent.Ui.FirstLoad
    private var isTimerStarted: Boolean = false
    private var lessonAdapter: LessonAdapter? = null
    private var homeworkAdapter: HomeWorkAdapter? = null
    private val compositeDisposable = CompositeDisposable()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentHomeBinding
        .inflate(inflater, container, false)
        .also { _binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapters()
    }

    private fun initAdapters() {
        lessonAdapter = LessonAdapter() {
        }
        homeworkAdapter = HomeWorkAdapter()
        val lessonLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val homeworkLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        with(binding.fragmentHomeClassesRV) {
            layoutManager = lessonLayoutManager
            adapter = lessonAdapter
            PagerSnapHelper().attachToRecyclerView(this)
            val dividerItemDecoration =
                DividerItemDecoration(requireContext(), lessonLayoutManager.orientation)
            addItemDecoration(dividerItemDecoration)
        }
        with(binding.fragmentHomeHomeworkRV) {
            layoutManager = homeworkLayoutManager
            adapter = homeworkAdapter
            PagerSnapHelper().attachToRecyclerView(this)
            val dividerItemDecoration =
                DividerItemDecoration(requireContext(), homeworkLayoutManager.orientation)
            addItemDecoration(dividerItemDecoration)
        }
    }

    override fun createStore(): Store<HomeEvent, HomeEffect, HomeState> {
        return ElmStore(
            initialState = HomeState(),
            reducer = HomeReducer(),
            actor = HomeActor()
        )
    }

    override fun render(state: HomeState) {
        startTimer()
        submitLessonsWithScroll(state.lessons)
        homeworkAdapter?.submitList(state.homeworks)
    }

    override fun handleEffect(effect: HomeEffect) = when (effect) {
        is HomeEffect.ShowError -> renderError(effect.error)
    }

    private fun submitLessonsWithScroll(lessons: List<Lesson>) {
        val currentTime = Date().time
        val nearest = lessons.filter { it.endTimeInMillis - currentTime > 0 }
            .minByOrNull { it.endTimeInMillis }
        val index = lessons.indexOf(nearest)
        lessonAdapter?.submitList(lessons) {
            if (index > 0) {
                binding.fragmentHomeClassesRV.scrollToPosition(index)
            }
        }
    }

    private fun startTimer() {
        if (!isTimerStarted) {
            isTimerStarted = true
            Observable.interval(UPDATE_PERIOD_MILLIS, TimeUnit.MILLISECONDS, Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    updateTimer()
                }, {
                    renderError(it)
                }).addTo(compositeDisposable)
        }
    }

    private fun updateTimer() {
        val currentTime = Date().time
        val timeLeft = store.currentState.nextExamTime - currentTime
        val minutes = (timeLeft / (1000 * 60) % 60)
        val hours = (timeLeft / (1000 * 60 * 60) % 24)
        val days = timeLeft / (1000 * 60 * 60 * 24)
        binding.fragmentHomeTimer.itemTimerDaysCounter.text = days.toString()
        binding.fragmentHomeTimer.itemTimerHoursCounter.text = hours.toString()
        binding.fragmentHomeTimer.itemTimerMinutesCounter.text = minutes.toString()
    }

    private fun renderError(error: Throwable) {
        Toast.makeText(requireContext(), error.localizedMessage, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        homeworkAdapter = null
        lessonAdapter = null
        compositeDisposable.dispose()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val UPDATE_PERIOD_MILLIS = 1000L
    }
}