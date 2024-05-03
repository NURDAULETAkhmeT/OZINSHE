package com.example.ozinshe

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ozinshe.Adapter.SearchAdapter
import com.example.ozinshe.Data.LoginRequest.UserAddressPassword
import com.example.ozinshe.Data.Service.RetrofitService
import com.example.ozinshe.databinding.FragmentSearchBinding
import kotlinx.coroutines.launch

class SearchFragment : Fragment(), SearchAdapter.ItemClickListener {
    private lateinit var binding: FragmentSearchBinding
    private var navigationHostProvider: NavigationHostProvider? = null
    private val adapterSearchRcView by lazy { SearchAdapter(this) }
    private val userViewModel: UserAddressPassword by viewModels({ requireParentFragment() })

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
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchCons.isVisible = false

        navigationHostProvider?.hideBottomNavigationBar(true)
        navigationHostProvider?.fullScreenMode(false)

        binding.profileExitImageButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.editTextText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
                // Не используется
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                // Не используется
            }

            @SuppressLint("ResourceType")
            override fun afterTextChanged(editable: Editable?) {
                val searchText = editable.toString()
                // После изменения текста проверяем условие и скрываем/отображаем элементы
                if (editable.isNullOrEmpty()) {
                    // Если текст в EditText пустой или null, показываем sanattarLayout
                    binding.sanattar.isVisible = true
                    binding.editTextText.isVisible = true
                    binding.searchCons.isVisible = false

                    val isNightMode = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES

                    if (isNightMode) {
                        binding.editTextText.setBackgroundResource(R.drawable.shape_vhod_night)
                        binding.searchImage.setImageResource(R.drawable.search_hight)
                    } else {
                        binding.editTextText.setBackgroundResource(R.drawable.shape_vhod)
                        binding.searchImage.setImageResource(R.drawable.search)
                    }
                } else {
                    // Если есть текст, скрываем sanattarLayout
                    binding.sanattar.isVisible = false
                    binding.editTextText.isVisible = true
                    binding.searchCons.isVisible = true
                    binding.editTextText.setBackgroundResource(R.drawable.shape_vhod_true)
                    binding.searchImage.setImageResource(R.drawable.search_outline)

                    lifecycleScope.launch {
                        val searchInfo = RetrofitService.apiiService.moviesSearch("Bearer ${userViewModel.tokenType.value}",searchText)
                        adapterSearchRcView.submitList(searchInfo)
                        binding.RcView.adapter = adapterSearchRcView
                    }


                }
            }
        })


        binding.textView7.setOnClickListener {
            pageCategory(1, getString(R.string.home_trend_textGlav))
        }
        binding.textView9.setOnClickListener {
            pageCategory(5, getString(R.string.home_sizge_textGlav))
        }
        binding.textView10.setOnClickListener {
            pageCategory(31, getString(R.string.search_sitkom))
        }
        binding.texotview12.setOnClickListener {
            pageCategory(9, getString(R.string.search_multserial))
        }
        binding.textView13.setOnClickListener {
            pageCategory(8, getString(R.string.home_reality_text))
        }
    }

    override fun onItemClick(id: Int) {
        Toast.makeText(context, "место $id", Toast.LENGTH_SHORT).show()
        val bundle = bundleOf("id" to id)
        findNavController().navigate(R.id.detailFragment, bundle)
    }

    fun pageCategory(categoryId: Int, name: String) {
        val bundle = Bundle()
        bundle.putInt("categoryId" , categoryId)
        bundle.putString("name", name)
        findNavController().navigate(R.id.action_searchFragment2_to_sanattarFragment, bundle)
    }

}

