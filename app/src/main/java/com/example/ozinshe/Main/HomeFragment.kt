package com.example.ozinshe.Main

import HomeCategoryAge
import HomeJanr
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.ozinshe.Adapter.HomeCategoriMultSerial
import com.example.ozinshe.Adapter.HomeCategoriSitkomdar
import com.example.ozinshe.Adapter.HomeCategoriTelehikayalar
import com.example.ozinshe.Adapter.HomeCategoriTolukMult
import com.example.ozinshe.Adapter.HomeCategoriTrendtegiler
import com.example.ozinshe.Data.Loading.LoadingAdapter
import com.example.ozinshe.Data.LoginRequest.UserAddressPassword
import com.example.ozinshe.Data.Service.RetrofitService
import com.example.ozinshe.NavigationHostProvider
import com.example.ozinshe.R
import com.example.ozinshe.databinding.FragmentHomeBinding
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.coroutines.launch

class HomeFragment : Fragment(), HomeCategoriTolukMult.ItemClickListener,
    HomeCategoriTrendtegiler.ItemClickListener, HomeCategoriTelehikayalar.ItemClickListener,
    HomeCategoriMultSerial.ItemClickListener, HomeCategoriSitkomdar.ItemClickListener, HomeJanr.ItemClickListenerJanr {

    private lateinit var binding: FragmentHomeBinding
    private val adapterJanr by lazy { HomeJanr(this) }
    private val adapterCategoryAge by lazy { HomeCategoryAge() }
    private val adapterCategoryTrendtegiler by lazy { HomeCategoriTrendtegiler(this) }
    private val adapterCategoryTelehikayalar by lazy { HomeCategoriTelehikayalar(this) }
    private val adapterCategoryTolukMult by lazy { HomeCategoriTolukMult(this) }
    private val adapterCategoryMultSerial by lazy { HomeCategoriMultSerial(this) }
    private val adapterCategorySitkomdar by lazy { HomeCategoriSitkomdar(this) }

    private var navigationHostProvider: NavigationHostProvider? = null
    private val userViewModel: UserAddressPassword by viewModels({ requireParentFragment() })

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigationHostProvider = context as? NavigationHostProvider
            ?: throw ClassCastException("$context must implement NavigationHostProvider")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        navigationHostProvider?.apply {
            hideBottomNavigationBar(true)
            fullScreenMode(false)
        }

        var shimmer = binding.homeShimmerLauout

        shimmer.startShimmer()
        binding.ShimmerLayoutGlav.startShimmer()
        binding.homeShimmerLauoutSagan.startShimmer()
        userViewModel.tokenType.observe(viewLifecycleOwner) { newTokenType ->

            lifecycleScope.launch {
                try {
                    val dataGlavJanr = RetrofitService.apiiService.janr("Bearer $newTokenType")
                    val dataGlavMovie = RetrofitService.apiiService.categoryAge("Bearer $newTokenType")
                    val dataCategoryMovies = RetrofitService.apiiService.categoryMainMovies("Bearer $newTokenType")

                    adapterJanr.submitList(dataGlavJanr)
                    adapterCategoryAge.submitList(dataGlavMovie)

                    dataCategoryMovies.forEach { movie ->
                        when (movie.categoryId) {
                            1 -> {
                                adapterCategoryTrendtegiler.submitList(movie.movies)
                                binding.RcViewHomeTrendegiler.adapter = adapterCategoryTrendtegiler
                            }
                            5 -> {
                                adapterCategoryTelehikayalar.submitList(movie.movies)
                                binding.RcViewHomeSaganArnalgan.adapter = adapterCategoryTelehikayalar
                            }
                            8 -> {
                                adapterCategoryTolukMult.submitList(movie.movies)
                                binding.RcViewHomeGlavnoe.adapter = adapterCategoryTolukMult
                            }
                            9 -> {
                                adapterCategoryMultSerial.submitList(movie.movies)
                                binding.RcViewHomeRealityShou.adapter = adapterCategoryMultSerial
                            }
                            31 -> {
                                adapterCategorySitkomdar.submitList(movie.movies)
                                binding.RcViewHomeTelehikaya.adapter = adapterCategorySitkomdar
                            }
                        }
                    }
                    shimmer.stopShimmer()
                    binding.ShimmerLayoutGlav.stopShimmer()
                    binding.homeShimmerLauoutSagan.stopShimmer()

                    shimmer.visibility = View.GONE
                    binding.ShimmerLayoutGlav.visibility = View.GONE
                    binding.homeShimmerLauoutSagan.visibility = View.GONE

                } catch (e: Exception) {
                    Log.d("AAA", e.message.toString())
                }
            }
        }

        binding.apply {
            RcViewHomeJanr.adapter = adapterJanr
            RcViewHomeGot.adapter = adapterCategoryAge
//            RcViewHomeTrendegiler.adapter = adapterCategoryTrendtegiler
//            RcViewHomeSaganArnalgan.adapter = adapterCategoryTelehikayalar
//            RcViewHomeGlavnoe.adapter = adapterCategoryTolukMult
//            RcViewHomeRealityShou.adapter = adapterCategoryMultSerial
//            RcViewHomeTelehikaya.adapter = adapterCategorySitkomdar
        }

        binding.buttomHomeTextViewTrendegiler.setOnClickListener {
            pageCategory(1, getString(R.string.home_trend_textGlav))
        }
        binding.buttonHomeTextViewSaganArnalgan.setOnClickListener {
            pageCategory(5, getString(R.string.home_sizge_textGlav))
        }
        binding.buttonHomeTextViewRealityShou.setOnClickListener {
            pageCategory(9, getString(R.string.search_multserial))
        }
        binding.buttonHomeTextViewTelehikaya.setOnClickListener {
            pageCategory(31, getString(R.string.search_sitkom))
        }
    }

    override fun onItemClick(id: Int) {
        Toast.makeText(context, "место $id", Toast.LENGTH_SHORT).show()
        val bundle = bundleOf("id" to id)
        findNavController().navigate(R.id.action_homeFragment2_to_detailFragment, bundle)
    }

    override fun onItemClickJanr(id: Int) {
        Toast.makeText(context, "место $id", Toast.LENGTH_SHORT).show()
        val bundle = bundleOf("id" to id)
        findNavController().navigate(R.id.action_homeFragment2_to_sanattarFragment, bundle)
    }

    fun pageCategory(categoryId: Int, name: String) {
//        val bundle = bundleOf("categoryId" to categoryId)
        val bundle = Bundle()
        bundle.putInt("categoryId" , categoryId)
        bundle.putString("name", name)
        findNavController().navigate(R.id.action_homeFragment2_to_sanattarFragment, bundle)
//        lifecycleScope.launch {
//            var dataCategoryPage = RetrofitService.apiiService.pageCategory(userViewModel.tokenType.value!!, categoryId)
//
//        }
    }
}
