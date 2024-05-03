package com.example.ozinshe

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ozinshe.Adapter.FavoriteAdapter.FavorieAdapter
import com.example.ozinshe.Data.LoginRequest.UserAddressPassword
import com.example.ozinshe.Data.Service.RetrofitService
import com.example.ozinshe.databinding.FragmentFavoriteBinding
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment(), FavorieAdapter.ItemClickListener {
    private lateinit var binding: FragmentFavoriteBinding
    private var navigationHostProvider: NavigationHostProvider? = null
    private val adapterFavoriteRcView by lazy { FavorieAdapter(this) }
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
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ShimmerLayoutRcView.startShimmer()

        navigationHostProvider?.hideBottomNavigationBar(true)
        navigationHostProvider?.fullScreenMode(false)

        userViewModel.tokenType.observe(viewLifecycleOwner) { newTokenType ->
            lifecycleScope.launch {
                val dataRcView = RetrofitService.apiiService.moviesFavoriteSpisok("Bearer $newTokenType")

                try {
                    adapterFavoriteRcView.submitList(dataRcView)
                    binding.RcView.adapter = adapterFavoriteRcView

                    binding.ShimmerLayoutRcView.stopShimmer()
                    binding.ShimmerLayoutRcView.visibility = View.GONE
                } catch (e: Exception) {

                }
            }
        }
    }

    override fun onItemClick(id: Int) {
        Toast.makeText(context, "место $id", Toast.LENGTH_SHORT).show()
        val bundle = bundleOf("id" to id)
        findNavController().navigate(R.id.action_favoriteFragment2_to_detailFragment, bundle)
    }
}