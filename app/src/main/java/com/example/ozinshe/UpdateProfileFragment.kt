package com.example.ozinshe

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ozinshe.Adapter.SharedPreferences.UserPreferences
import com.example.ozinshe.Data.LoginRequest.UserAddressPassword
import com.example.ozinshe.Data.ProfileUser.UpdateProfile
import com.example.ozinshe.Data.Service.RetrofitService
import com.example.ozinshe.databinding.FragmentUpdateProfileBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class UpdateProfileFragment : Fragment() {
    private lateinit var binding: FragmentUpdateProfileBinding
    private var navigationHostProvider: NavigationHostProvider? = null
    private val userViewModel: UserAddressPassword by viewModels({ requireParentFragment() })
    private var calendar = Calendar.getInstance()

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
        binding = FragmentUpdateProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userPreferences = UserPreferences(requireContext())

        userViewModel.tokenType.observe(viewLifecycleOwner) { newTokenType ->
            // Обновить UI или выполнить другие действия с новым значением tokenType
            Log.d("VhodFragment", "New TokenType: $newTokenType")

            lifecycleScope.launch {
                try {
                    val dataProfile = RetrofitService.apiiService.profile("Bearer $newTokenType")
                    binding.editTextText2.hint = dataProfile.body()?.name ?: "Пустой"
                    binding.editTextPhone.hint = dataProfile.body()?.phoneNumber ?: "Пустой"
                    binding.editTextTextEmailAddress2.hint = dataProfile.body()!!.user.email
                    binding.editTextDate.hint = dataProfile.body()?.birthDate ?: "Пустой"
                    Log.d("Izmena",dataProfile.body().toString())
                } catch (e:Exception){
                    Log.d("Error", e.toString())
                }
            }
        }

        binding.vhodButtonKiru.setOnClickListener {
            Log.d("token", userViewModel.tokenType.value.toString())
            Log.d("language", userPreferences.getLanguage())
            Log.d("Data",binding.editTextDate.text.toString())
            Log.d("Data",binding.editTextText2.text.toString())
            Log.d("Data",binding.editTextPhone.text.toString())

            lifecycleScope.launch {
                try {
                    val Data = RetrofitService.apiiService.profileUpdate("Bearer ${userViewModel.tokenType.value.toString()}",
                        UpdateProfile(binding.editTextDate.text.toString(),
                            0,
                            userPreferences.getLanguage(),
                            binding.editTextText2.text.toString(),
                            binding.editTextPhone.toString()))

                    Toast.makeText(requireContext(), "$Data", Toast.LENGTH_SHORT).show()
                    Log.d("Izmena","$Data")
                } catch (e: Exception) {
                    Log.d("Error", it.toString())
                }
            }

//            lifecycleScope.launch {
//                runCatching {
//                    RetrofitService.apiiService.profileUpdate("Bearer ${userViewModel.tokenType.value.toString()}",
//                        UpdateProfile(binding.editTextDate.text.toString(),
//                            0,
//                            userPreferences.getLanguage(),
//                            binding.editTextText2.text.toString(),
//                            binding.editTextPhone.toString()))
//                }.onSuccess {
//                    Toast.makeText(requireContext(), "$it", Toast.LENGTH_SHORT).show()
//                    Log.d("Izmena","Ozgerdi")
//                }.onFailure { Log.d("Error", it.toString()) }
//
//            }
        }

        navigationHostProvider?.hideBottomNavigationBar(false)
        navigationHostProvider?.fullScreenMode(true)

        binding.updateExitImageButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.editTextDate.setOnClickListener {
            showDatePicker()
        }
    }

    private fun showDatePicker(){
        val datePickerDialog = DatePickerDialog(requireContext(),{DatePicker, year: Int,monthOfYear: Int, dayOfMonth: Int ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year,monthOfYear,dayOfMonth)
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formattedDate = dateFormat.format(selectedDate.time)
            binding.editTextDate.text = formattedDate
        },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

}