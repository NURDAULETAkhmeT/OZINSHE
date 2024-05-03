package com.example.ozinshe


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ozinshe.Data.Service.RetrofitService
import com.example.ozinshe.Data.LoginRequest.loginRequest
import com.example.ozinshe.databinding.FragmentRegestraciaBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RegestraciaFragment : Fragment() {
    private lateinit var binding: FragmentRegestraciaBinding
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
        binding = FragmentRegestraciaBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigationHostProvider?.hideBottomNavigationBar(false)
        navigationHostProvider?.fullScreenMode(true)


        binding.regestrasiaShowPassword.setOnClickListener {
            // Переключить видимость пароля
            binding.regestrasiaEditPassword.transformationMethod = if (binding.regestrasiaEditPassword.transformationMethod == null) {
                binding.regestrasiaShowPassword.setImageResource(R.drawable.vhod_show)  // Изображение для видимого пароля
                PasswordTransformationMethod.getInstance()
            } else {
                binding.regestrasiaShowPassword.setImageResource(R.drawable.vhod_show_open) // Изображение для скрытого пароля
                null
            }
            // Переместить курсор в конец текста
            binding.regestrasiaEditPassword.setSelection(binding.regestrasiaEditPassword.text.length)
        }

        binding.regestrasiaShowPassword1.setOnClickListener {
            // Переключить видимость пароля
            binding.regestrasiaEditPassword1.transformationMethod = if (binding.regestrasiaEditPassword1.transformationMethod == null) {
                binding.regestrasiaShowPassword1.setImageResource(R.drawable.vhod_show)  // Изображение для видимого пароля
                PasswordTransformationMethod.getInstance()
            } else {
                binding.regestrasiaShowPassword1.setImageResource(R.drawable.vhod_show_open) // Изображение для скрытого пароля
                null
            }
            // Переместить курсор в конец текста
            binding.regestrasiaEditPassword1.setSelection(binding.regestrasiaEditPassword1.text.length)
        }


        binding.regestrasiaTextVhodButton.setOnClickListener{
            findNavController().navigate(R.id.vhodFragment)
        }
        binding.regestrasiaImageButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.regestrasiaButtonKiru.setOnClickListener{
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val resloginRequest = RetrofitService.apiiService.signUpRequest(
                            loginRequest(
                                binding.regestrasiaEditTextTextEmailAddress.text.toString(),
                                binding.regestrasiaEditPassword1.text.toString()
                            )
                        )
                        if (resloginRequest.isSuccessful && binding.regestrasiaEditPassword1.text.toString() == binding.regestrasiaEditPassword.text.toString()) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, "Пайдаланушы ашылды", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.vhodFragment)
                            }

                        } else {
                            requireActivity().runOnUiThread {
                                // Обработка ошибок
                                binding.regestrasiaTextSkritiOshibka.text = "Мұндай email-ы бар пайдаланушы тіркелген"
                                binding.regestrasiaTextSkritiOshibka.visibility = View.VISIBLE
                                binding.regestrasiaLinearEmail.setBackgroundResource(R.drawable.shape_vhod_false)
                            }
                        }
                    } catch (_: Exception) {}
                }

        }
    }
}