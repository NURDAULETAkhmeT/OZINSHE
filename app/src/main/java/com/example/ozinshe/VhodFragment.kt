package com.example.ozinshe


import SharedViewModel
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.ozinshe.Adapter.SharedPreferences.UserPreferences
import com.example.ozinshe.Data.Service.RetrofitService
import com.example.ozinshe.Data.LoginRequest.UserAddressPassword
import com.example.ozinshe.Data.LoginRequest.loginRequest
import com.example.ozinshe.databinding.FragmentVhodBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale


class VhodFragment : Fragment() {
    private lateinit var binding: FragmentVhodBinding
    private var navigationHostProvider: NavigationHostProvider? = null
    private val userViewModel: UserAddressPassword by viewModels({ requireParentFragment() })
    private val sharedViewModel: SharedViewModel by activityViewModels()

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVhodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (context is NavigationHostProvider) {
            navigationHostProvider = context as NavigationHostProvider
        } else {
            throw ClassCastException("$context must implement NavigationHostProvider")
        }

        super.onViewCreated(view, savedInstanceState)
        val userPreferences = UserPreferences(requireContext())

        navigationHostProvider?.hideBottomNavigationBar(false)
        navigationHostProvider?.fullScreenMode(true)


        binding.vhodShowPassword.setOnClickListener {
            // Переключить видимость пароля
            binding.vhodEditPassword.transformationMethod = if (binding.vhodEditPassword.transformationMethod == null) {
                binding.vhodShowPassword.setImageResource(R.drawable.vhod_show)  // Изображение для видимого пароля
                PasswordTransformationMethod.getInstance()
            } else {
                binding.vhodShowPassword.setImageResource(R.drawable.vhod_show_open) // Изображение для скрытого пароля
                null
            }
            // Переместить курсор в конец текста
            binding.vhodEditPassword.setSelection(binding.vhodEditPassword.text.length)
        }

        binding.vhodButtonKiru.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val resloginRequest = RetrofitService.apiiService.loginRequest(
                        loginRequest(
                            binding.editTextTextEmailAddress.text.toString(),
                            binding.vhodEditPassword.text.toString()
                        )
                    )
                    if (resloginRequest.isSuccessful) {
                        withContext(Dispatchers.Main) {
                            userViewModel.clearData()
                            userViewModel.tokenType.value = resloginRequest.body()!!.accessToken
                            val bundleToken = bundleOf("token" to resloginRequest.body()!!.accessToken)
                            sharedViewModel.clearData()
                            sharedViewModel.token.value = resloginRequest.body()!!.accessToken
                            userViewModel.login.value = resloginRequest.body()!!.email
                            userPreferences.saveCredentials(binding.editTextTextEmailAddress.text.toString(), binding.vhodEditPassword.text.toString())
                            val dataProfile = RetrofitService.apiiService.profile("Bearer ${resloginRequest.body()!!.accessToken}")
                            if (dataProfile.body()!!.language == "ru") {
                                switchLanguage("ru")
                            } else if (dataProfile.body()!!.language == "en") {
                                switchLanguage("en")
                            } else {
                                switchLanguage("values")
                            }
                            findNavController().navigate(R.id.homeFragment2, bundleToken)
                        }

                    } else {
                        requireActivity().runOnUiThread {
                            // Обработка ошибок
                            binding.vhodTextSkritiOshibka.text = "Қате Формат"
                            binding.vhodTextSkritiOshibka.visibility = View.VISIBLE
                            binding.vhodLinearEmail.setBackgroundResource(R.drawable.shape_vhod_false)
                        }
                    }
                } catch (_: Exception) {}
            }
        }

        binding.vhodTextRegestraciaButton.setOnClickListener{
            findNavController().navigate(R.id.regestraciaFragment)
        }
        binding.vhodImageButton.setOnClickListener{
            findNavController().navigateUp()
        }

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