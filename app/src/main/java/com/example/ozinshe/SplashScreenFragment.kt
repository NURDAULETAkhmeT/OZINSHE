package com.example.ozinshe

import SharedViewModel
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ozinshe.Adapter.SharedPreferences.UserPreferences
import com.example.ozinshe.Data.LoginRequest.UserAddressPassword
import com.example.ozinshe.Data.LoginRequest.loginRequest
import com.example.ozinshe.Data.Service.RetrofitService
import com.example.ozinshe.databinding.FragmentSplashScreenBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale


@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment() {
    private lateinit var binding: FragmentSplashScreenBinding
    private var navigationHostProvider: NavigationHostProvider? = null
    private val userViewModel: UserAddressPassword by viewModels({ requireParentFragment() })
    private val sharedViewModel: SharedViewModel by activityViewModels()

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
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigationHostProvider?.hideBottomNavigationBar(false)
        navigationHostProvider?.fullScreenMode(true)


        Handler(Looper.getMainLooper()).postDelayed({
            val userPreferences = UserPreferences(requireContext())

            val savedUsername = userPreferences.getUsername()
            val savedPassword = userPreferences.getPassword()

            if (!savedUsername.isNullOrEmpty() && !savedPassword.isNullOrEmpty()) {
                lifecycleScope.launch {
                    val resloginRequest = RetrofitService.apiiService.loginRequest(loginRequest(savedUsername,savedPassword))

                    if (resloginRequest.isSuccessful) {
                        withContext(Dispatchers.Main) {
                            userViewModel.tokenType.value = resloginRequest.body()!!.accessToken
                            val bundleToken = bundleOf("token" to resloginRequest.body()!!.accessToken)
                            sharedViewModel.token.value = resloginRequest.body()!!.accessToken
                            userViewModel.login.value = resloginRequest.body()!!.email
                            switchLanguage(userPreferences.getLanguage())

                            val isNightModeEnabled = userPreferences.getNightMode()

                            // Установка режима ночи
                            if (isNightModeEnabled != null) {
                                if (isNightModeEnabled) {
                                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                                } else {
                                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                                }
                            }


                            findNavController().navigate(R.id.homeFragment2, bundleToken)
                        }
                    }
                }
            } else {
                findNavController().navigate(R.id.action_splashScreenFragment2_to_viewPager2Fragment)
            }
        }, 1500) // 5000 миллисекунд = 5 секунд


    }

    private fun switchLanguage(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)

        requireContext().resources.updateConfiguration(config, requireContext().resources.displayMetrics)
        activity?.recreate()
    }
}