package com.dentag.educationproject.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dentag.educationproject.ui.classes.ClassesFragment
import com.dentag.educationproject.ui.home.HomeFragment

class MainPagerAdapter(
    parentActivity: AppCompatActivity
) : FragmentStateAdapter(parentActivity) {

    val fragmentNameList = listOf(
        HOME_FRAGMENT_NAME,
        CLASSES_FRAGMENT_NAME
    )

    override fun getItemCount(): Int {
        return fragmentNameList.size
    }

    override fun createFragment(position: Int): Fragment = when (fragmentNameList[position]) {
        HOME_FRAGMENT_NAME -> HomeFragment()
        CLASSES_FRAGMENT_NAME -> ClassesFragment()
        else -> throw IllegalArgumentException("Fragment was not added")
    }

    companion object {
        private const val HOME_FRAGMENT_NAME = "Home"
        private const val CLASSES_FRAGMENT_NAME = "Classes"
    }
}