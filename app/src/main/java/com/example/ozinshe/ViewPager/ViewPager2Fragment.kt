package com.example.ozinshe.ViewPager

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ozinshe.NavigationHostProvider
import com.example.ozinshe.R
import com.example.ozinshe.databinding.FragmentViewPager2Binding
import com.google.android.material.tabs.TabLayoutMediator

class ViewPager2Fragment : Fragment() {
    private lateinit var binding: FragmentViewPager2Binding
    private var navigationHostProvider: NavigationHostProvider? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NavigationHostProvider) {
            navigationHostProvider = context
        } else {
            throw ClassCastException("$context must implement NavigationHostProvider")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewPager2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigationHostProvider?.hideBottomNavigationBar(false)
        navigationHostProvider?.fullScreenMode(true)

        val adapter = MyFragmentPagerAdapter(this)
        binding.viewPager2.adapter = adapter

        TabLayoutMediator(binding.viewPager2TabLayout, binding.viewPager2){tab, position ->
            tab.setIcon(R.drawable.rectangle_310)
        }.attach()
    }
}