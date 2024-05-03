package com.example.ozinshe

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ozinshe.Adapter.SharedPreferences.UserPreferences
import com.example.ozinshe.Data.LoginRequest.UserAddressPassword
import com.example.ozinshe.Data.ProfileUser.Language
import com.example.ozinshe.Data.Service.RetrofitService
import com.example.ozinshe.databinding.BottomLanguageLayoutBinding
import com.example.ozinshe.databinding.FragmentProfileBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch
import java.util.Locale


@Suppress("DEPRECATION")
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private var navigationHostProvider: NavigationHostProvider? = null
    private val userViewModel: UserAddressPassword by viewModels({ requireParentFragment() })
    private lateinit var bindingShape: BottomLanguageLayoutBinding

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
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.detailShimmerLayoutViewTextName.startShimmer()
        binding.profileTextGmail.visibility = View.INVISIBLE

        val userPreferences = UserPreferences(requireContext())

        navigationHostProvider?.hideBottomNavigationBar(true)
        navigationHostProvider?.fullScreenMode(false)

//        binding.profileExitImageButton.setOnClickListener{
//            findNavController().navigateUp()
//        }

        binding.profileJekederekterCons.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment2_to_updateProfileFragment)
        }


        binding.LanguageProfile.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(requireContext())
            val viewBottom = layoutInflater.inflate(R.layout.bottom_language_layout, null)
            bottomSheetDialog.setContentView(viewBottom)

            bindingShape = BottomLanguageLayoutBinding.bind(viewBottom)

            bindingShape.englishButton.setOnClickListener {
                userPreferences.saveLanguage("en")
                switchLanguage("en")
                binding.languageText.text = "English"
                bottomSheetDialog.dismiss()
                lifecycleScope.launch {
                        val dataProfileLanguage = RetrofitService.apiiService.profileLanguage("Bearer ${userViewModel.tokenType.value}", Language("en"))
                        Log.d("INFOOOOOOOOO",dataProfileLanguage.toString())
                }
            }

            bindingShape.russianButton.setOnClickListener {
                userPreferences.saveLanguage("ru")
                switchLanguage("ru")
                bottomSheetDialog.dismiss()
                binding.languageText.text = "Русский"
                lifecycleScope.launch {
                        val dataProfileLanguage = RetrofitService.apiiService.profileLanguage("Bearer ${userViewModel.tokenType.value}",Language("ru"))
                        Log.d("INFOOOOOOOOO",dataProfileLanguage.toString())
                    }
            }

            bindingShape.kazakhButton.setOnClickListener {
                userPreferences.saveLanguage("values")
                switchLanguage("values")
                bottomSheetDialog.dismiss()
                binding.languageText.text = "Қазақша"
                lifecycleScope.launch {
                    try {
                        val dataProfileLanguage = RetrofitService.apiiService.profileLanguage("Bearer ${userViewModel.tokenType.value}", Language("values"))
                        Log.d("INFOOOOOOOOO",dataProfileLanguage.toString())}
                    catch (e:Exception){
                        Log.d("INFOOOOOOOOOEROR",e.message.toString())
                    }
                }
            }

            bottomSheetDialog.show()
        }


        binding.logOutOfYourAccount.setOnClickListener{
            userPreferences.clearPreferences()
            findNavController().navigate(R.id.action_profileFragment2_to_viewPager2Fragment)
        }


        userViewModel.tokenType.observe(viewLifecycleOwner) { newTokenType ->
            // Обновить UI или выполнить другие действия с новым значением tokenType
            Log.d("VhodFragment", "New TokenType: $newTokenType")

            lifecycleScope.launch {
                try {
                    val dataProfile = RetrofitService.apiiService.profile("Bearer $newTokenType")
                    binding.profileTextGmail.text = dataProfile.body()!!.user.email

                    binding.detailShimmerLayoutViewTextName.stopShimmer()
                    binding.profileTextGmail.visibility = View.VISIBLE
                    binding.detailShimmerLayoutViewTextName.visibility = View.GONE

                    val savedNightMode = userPreferences.getNightMode()
                    if (savedNightMode != null) {
                        binding.SwitchNight.isChecked = savedNightMode
                    }

                    if (dataProfile.body()!!.language == "en") {
                        binding.languageText.text = "English"
                    }
                    else if(dataProfile.body()!!.language == "ru"){
                        binding.languageText.text = "Русский"
                    }
                    else {
                        binding.languageText.text = "Қазақша"
                    }

                    Log.d("AAA","resultat: ${dataProfile.body()!!.language}")
                } catch (e: Exception){
                    Log.d("AAA",e.message.toString())
                }
            }
        }

        var isRecreating = false

        binding.SwitchNight.setOnCheckedChangeListener { _, isChecked ->
            // Устанавливайте тему в зависимости от состояния Switch
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            userPreferences.saveNightMode(isChecked)

            // Запрещаем пересоздание фрагмента
            setRetainInstance(true)

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

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        // Пересоздаем фрагмент при изменении конфигурации, если setRetainInstance(false)
        setRetainInstance(false)
    }
}

