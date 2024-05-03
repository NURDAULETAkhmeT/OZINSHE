package com.example.ozinshe.ViewPager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ozinshe.R
import com.example.ozinshe.databinding.FragmentOnboarding10Binding

class Onboarding10Fragment : Fragment() {
    private lateinit var binding: FragmentOnboarding10Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboarding10Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonOnboarding10.setOnClickListener {
            findNavController().navigate(R.id.action_viewPager2Fragment_to_vhodFragment)
        }
    }
}