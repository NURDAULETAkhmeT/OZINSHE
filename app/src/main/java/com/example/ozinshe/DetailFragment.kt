package com.example.ozinshe

import SharedViewModel
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.ozinshe.Adapter.ScreenshotsAdapter
import com.example.ozinshe.Adapter.UksasAdapter
import com.example.ozinshe.Data.CategoryMainMovies.Movy
import com.example.ozinshe.Data.Favorite.movieIdFavorite
import com.example.ozinshe.Data.FavoriteGET.FavoriteSpisok
import com.example.ozinshe.Data.IDMovies.IDMovies
import com.example.ozinshe.Data.Seasons.SeasonsList
import com.example.ozinshe.Data.Service.RetrofitService
import com.example.ozinshe.databinding.FragmentDetailBinding
import kotlinx.coroutines.launch
import retrofit2.Response

@Suppress("UNREACHABLE_CODE")
class DetailFragment : Fragment(), ScreenshotsAdapter.ItemClickListener, UksasAdapter.ItemClickListener {
    private lateinit var binding: FragmentDetailBinding
    private var navigationHostProvider: NavigationHostProvider? = null
    private val adapterScreenshot by lazy { ScreenshotsAdapter(this) }
    private val adapterUksas by lazy { UksasAdapter(this) }
    private lateinit var sharedViewModel: SharedViewModel
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
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.detailShimmerLayoutView.startShimmer()
        binding.detailShimmerLayoutViewTextGlav.startShimmer()
        binding.detailShimmerLayoutViewTextDescription.startShimmer()
        binding.detailShimmerLayoutViewTextGlavdes.startShimmer()
        binding.detailShimmerLayoutViewTextRegisor.startShimmer()
        binding.detailShimmerLayoutViewTextProduser.startShimmer()

        binding.textViewLinearText.visibility = View.INVISIBLE
        binding.skriti.visibility = View.INVISIBLE
        binding.regisor.visibility = View.INVISIBLE
        binding.Produser.visibility = View.INVISIBLE


        navigationHostProvider?.hideBottomNavigationBar(false)
        navigationHostProvider?.fullScreenMode(true)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        binding.detailExutButton.setOnClickListener {
            findNavController().navigateUp()
        }

//        val mToken = arguments?.getString("token", "")
        val mToken = sharedViewModel.token.value
        val mid = arguments?.getInt("id", 0)
        if (mid != null && mid > 0) {
            Log.d("mid", mid.toString())
            Log.d("mToken", mToken.toString())
            loadMovieInfo("Bearer ${mToken.toString()}",mid)
        } else {
            // Обработка случая, когда id не найден или неверен
        }
        binding.favoriteButton.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val spisokfavorite = RetrofitService.apiiService.moviesFavoriteSpisok("Bearer ${mToken.toString()}")
                    Log.d("SpisokFavorite", spisokfavorite.toString())

                    // Поиск фильма в списке избранного
                    val isMovieInFavorite = spisokfavorite.any { it.id == mid }

                    if (isMovieInFavorite) {
                        // Если фильм уже в избранном, удаляем его
                        val movieIdFavoriteToDelete = movieIdFavorite(mid!!)
                        val favoriteDelete = RetrofitService.apiiService.moviesFavoriteDelete("Bearer ${mToken.toString()}", movieIdFavoriteToDelete)
                        val drawableDelete = ContextCompat.getDrawable(requireContext(), R.drawable.home_detail_favorite_button)
                        binding.favoriteButton.setImageDrawable(drawableDelete)
                        Log.d("Delete", favoriteDelete.toString())
                    } else {
                        // Если фильма нет в избранном, добавляем его
                        val movieIdFavoriteToAdd = movieIdFavorite(mid!!)
                        val favoritePlus = RetrofitService.apiiService.moviesFavoritePlus("Bearer ${mToken.toString()}", movieIdFavoriteToAdd)
                        val drawableTrue = ContextCompat.getDrawable(requireContext(), R.drawable.home_detail_favorite_button_true)
                        binding.favoriteButton.setImageDrawable(drawableTrue)
                        Log.d("plus", favoritePlus.toString())
                    }
                } catch (e: Exception) {
                    Log.d("token", mToken.toString())
                    Log.d("id", mid.toString())
                    Log.e("FavoriteButton", "Error: ${e.message}")
                }
            }
        }
        val originalHeight = 104.dpToPx()
        var isViewSkrVisible = true

        binding.toligrrak.setOnClickListener {
            if (isViewSkrVisible) {
                // Скрываем ViewSkr и изменяем текст кнопки
                binding.ViewSkr.visibility = View.GONE
                binding.toligrrak.text = getString(R.string.detail_toligrak_jabu)
            } else {
                // Показываем ViewSkr и изменяем текст кнопки
                binding.ViewSkr.visibility = View.VISIBLE
                binding.toligrrak.text = getString(R.string.detail_toligirak)
            }

            // Инвертируем флаг видимости
            isViewSkrVisible = !isViewSkrVisible

            // Обновляем максимальную высоту и применяем maxLines и ellipsize
            binding.description.maxHeight = if (isViewSkrVisible) originalHeight else Int.MAX_VALUE
            binding.description.maxLines = if (isViewSkrVisible) 5 else Int.MAX_VALUE
            binding.description.ellipsize = if (isViewSkrVisible) TextUtils.TruncateAt.END else null
        }




    }

    private fun handleMovieInfo(response: Response<IDMovies>, detailUksas: List<Movy>) {
        if (response.isSuccessful) {
            val movieInfo = response.body()
            if (movieInfo != null) {
                // Обработка информации о фильме
                Glide.with(this).load(movieInfo.poster.link).into(binding.image)
                binding.textViewglav.text = movieInfo.name
                binding.year.text = movieInfo.year.toString()
                binding.description.text = movieInfo.description
                binding.moviesType.text = movieInfo.movieType
                binding.regisorText.text = movieInfo.director
                binding.produserText.text = movieInfo.producer

                adapterScreenshot.submitList(movieInfo.screenshots)
                binding.skrinRcView.adapter = adapterScreenshot

                adapterUksas.submitList(detailUksas)
                Log.d("res",detailUksas.toString())
                binding.uksasRcView.adapter = adapterUksas
            } else {
                // Обработка случая, когда тело ответа равно null
            }
        } else {
            // Обработка ошибки
        }
    }
    private fun searshClickFavorite(favoriteSpisok: FavoriteSpisok, id: Int) {
        for (favorite in favoriteSpisok) {
            if (favorite.id == id) {
                val drawableTrue = ContextCompat.getDrawable(requireContext(), R.drawable.home_detail_favorite_button_true)
                binding.favoriteButton.setImageDrawable(drawableTrue)
                break
                Log.d("True",drawableTrue.toString())
            } else if (favorite.id != id) {
                val drawableDelete = ContextCompat.getDrawable(requireContext(), R.drawable.home_detail_favorite_button)
                binding.favoriteButton.setImageDrawable(drawableDelete)
                Log.d("False",drawableDelete.toString())
            }
        }
    }

    private fun buttonPlay(seasons: SeasonsList, response: Response<IDMovies>,token: String,id: Int) {
        if (response.isSuccessful) {
            val bundle = bundleOf("id" to id,"token" to token)
            if (seasons?.seasons!!.isEmpty()) {
                binding.description.text = "${response.body()!!.video.link}"

                val youTubeFragment = YouTubePlayerFragment()
                val bundle = Bundle().apply { putString("videoLink", response.body()!!.video.link) }

                youTubeFragment.arguments = bundle

                findNavController().navigate(R.id.youTubePlayerFragment, bundle)
            } else {
                findNavController().navigate(R.id.action_detailFragment_to_seriesFragment, bundle)
            }
        }
    }


    private fun loadMovieInfo(token: String, movieId: Int) {
        lifecycleScope.launch {
            try {
                val response = RetrofitService.apiiService.getMovieInfo(token,movieId)
                val detailUksas = RetrofitService.apiiService.similarMovies(token,movieId)
                val seasons = RetrofitService.apiiService.moviesSezon(token,movieId)
                val favorite = RetrofitService.apiiService.moviesFavoriteSpisok(token)
                handleMovieInfo(response, detailUksas)
                searshClickFavorite(favorite, movieId)

                binding.detailShimmerLayoutView.stopShimmer()
                binding.detailShimmerLayoutViewTextGlav.stopShimmer()
                binding.detailShimmerLayoutViewTextDescription.stopShimmer()
                binding.detailShimmerLayoutViewTextGlavdes.stopShimmer()
                binding.detailShimmerLayoutViewTextRegisor.stopShimmer()

                binding.detailShimmerLayoutView.visibility = View.GONE
                binding.detailShimmerLayoutViewTextGlav.visibility = View.GONE
                binding.detailShimmerLayoutViewTextDescription.visibility =View.GONE
                binding.detailShimmerLayoutViewTextGlavdes.visibility =View.GONE
                binding.detailShimmerLayoutViewTextRegisor.visibility = View.GONE
                binding.detailShimmerLayoutViewTextProduser.visibility = View.GONE

                binding.textViewLinearText.visibility = View.VISIBLE
                binding.skriti.visibility = View.VISIBLE
                binding.regisor.visibility = View.VISIBLE
                binding.Produser.visibility = View.VISIBLE

                binding.playVideo.setOnClickListener {
                    buttonPlay(seasons,response,token,movieId)
                }
                Log.d("seasons", seasons.toString())
            } catch (e: Exception) {
                Log.d("AAA",e.message.toString())
            }
        }
    }
    override fun onItemClick(id: Int) {
//        Toast.makeText(context,"место $id", Toast.LENGTH_SHORT).show()
//        val mToken = arguments?.getString("token", "0")
//        val bundle = bundleOf("id" to id, "token" to mToken)
//        findNavController().navigate(R.id.action_homeFragment2_to_detailFragment, bundle)
    }

    private fun Int.dpToPx(): Int {
        val scale = resources.displayMetrics.density
        return (this * scale + 0.5f).toInt()
    }
}
