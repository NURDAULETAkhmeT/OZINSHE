package com.example.ozinshe.ViewPager
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyFragmentPagerAdapter(fragmentActivity: ViewPager2Fragment) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 3 // Количество фрагментов
    }

    override fun createFragment(position: Int): Fragment {
        // Создайте и верните фрагмент для каждой страницы
        return when (position) {
            0 -> Onboarding8Fragment()
            1 -> Onboarding9Fragment()
            2 -> Onboarding10Fragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}
