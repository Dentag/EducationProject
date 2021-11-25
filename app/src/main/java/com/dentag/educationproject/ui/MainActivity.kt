package com.dentag.educationproject.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dentag.educationproject.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViewPager()
    }

    private fun setupViewPager() {
        val fragmentAdapter = MainPagerAdapter(this)
        binding.viewPager.adapter = fragmentAdapter
        binding.viewPager.isUserInputEnabled = false
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = fragmentAdapter.fragmentNameList[position]
        }.attach()
    }
}