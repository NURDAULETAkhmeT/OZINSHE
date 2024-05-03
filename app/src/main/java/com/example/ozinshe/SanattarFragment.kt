package com.example.ozinshe

import SharedViewModel
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ozinshe.Adapter.FavoriteAdapter.FavorieAdapter
import com.example.ozinshe.Adapter.FavoriteAdapter.FavorieAdapterCategory
import com.example.ozinshe.Data.LoginRequest.UserAddressPassword
import com.example.ozinshe.Data.Service.RetrofitService
import com.example.ozinshe.databinding.FragmentSanattarBinding
import kotlinx.coroutines.launch

class SanattarFragment : Fragment(),FavorieAdapterCategory.ItemClickListener {
    private lateinit var binding: FragmentSanattarBinding
    private var navigationHostProvider: NavigationHostProvider? = null
    private val adapterFavoriteRcView by lazy { FavorieAdapterCategory(this) }
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
        binding = FragmentSanattarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.detailShimmerLayoutViewTextGlav.startShimmer()
        binding.ShimmerLayoutRcView.startShimmer()



        val categoryId = arguments?.getInt("categoryId", 0)
        val name = arguments?.getString("name", "Sanat")

        navigationHostProvider?.hideBottomNavigationBar(false)
        navigationHostProvider?.fullScreenMode(true)

        binding.exitButtonSanat.setOnClickListener {
            findNavController().navigateUp()
        }
        lifecycleScope.launch {
            try {
                var dataCategoryPage = RetrofitService.apiiService.pageCategory("Bearer ${userViewModel.tokenType.value.toString()}", categoryId!!)

                adapterFavoriteRcView.submitList(dataCategoryPage.content)
                binding.RcView.adapter = adapterFavoriteRcView

                binding.profileTextProfile.text = name.toString()


                binding.detailShimmerLayoutViewTextGlav.stopShimmer()
                binding.ShimmerLayoutRcView.stopShimmer()
                binding.detailShimmerLayoutViewTextGlav.visibility = View.GONE
                binding.ShimmerLayoutRcView.visibility = View.GONE
            }catch (e:Exception){
                Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onItemClick(id: Int) {
        Toast.makeText(context, "место $id", Toast.LENGTH_SHORT).show()
        val bundle = bundleOf("id" to id)
        findNavController().navigate(R.id.action_sanattarFragment_to_detailFragment, bundle)
    }

}