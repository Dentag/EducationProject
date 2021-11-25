package com.dentag.educationproject.ui.classes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dentag.educationproject.databinding.FragmentClassesBinding
import com.dentag.educationproject.openSkype
import com.dentag.educationproject.ui.classes.elm.ClassesActor
import com.dentag.educationproject.ui.classes.elm.ClassesReducer
import com.dentag.educationproject.ui.classes.elm.model.ClassesEffect
import com.dentag.educationproject.ui.classes.elm.model.ClassesEvent
import com.dentag.educationproject.ui.classes.elm.model.ClassesState
import com.dentag.educationproject.ui.lists.LessonAdapter
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.core.store.ElmStore
import vivid.money.elmslie.core.store.Store

class ClassesFragment : ElmFragment<ClassesEvent, ClassesEffect, ClassesState>() {
    private var _binding: FragmentClassesBinding? = null
    private val binding get() = _binding!!
    private var lessonsAdapter: LessonAdapter? = null

    override val initEvent: ClassesEvent = ClassesEvent.Ui.FirstLoad

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentClassesBinding.inflate(inflater, container, false).also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    private fun initRecycler() {
        lessonsAdapter = LessonAdapter() {
            openSkype(requireContext())
        }
        val lessonLayoutManager = LinearLayoutManager(requireContext())
        with(binding.fragmentClassesRV) {
            adapter = lessonsAdapter
            layoutManager = lessonLayoutManager
            val dividerItemDecoration =
                DividerItemDecoration(requireContext(), lessonLayoutManager.orientation)
            addItemDecoration(dividerItemDecoration)
        }
    }

    override fun createStore(): Store<ClassesEvent, ClassesEffect, ClassesState> {
        return ElmStore(
            initialState = ClassesState(),
            reducer = ClassesReducer(),
            actor = ClassesActor()
        )
    }

    override fun render(state: ClassesState) {
        lessonsAdapter?.submitList(state.lessons)
    }

    override fun handleEffect(effect: ClassesEffect) = when (effect) {
        is ClassesEffect.ShowError -> renderError(effect.error)
    }


    private fun renderError(error: Throwable) {
        Toast.makeText(requireContext(), error.localizedMessage, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lessonsAdapter = null
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}