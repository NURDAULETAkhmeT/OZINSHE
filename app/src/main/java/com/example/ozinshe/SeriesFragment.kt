package com.example.ozinshe

import SezonAdapter
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.ozinshe.Adapter.SeriesAdapter
import com.example.ozinshe.Data.Seasons.Season
import com.example.ozinshe.Data.Service.RetrofitService
import com.example.ozinshe.databinding.FragmentSeriesBinding
import kotlinx.coroutines.launch

class SeriesFragment : Fragment(), SezonAdapter.ItemClickListener,SeriesAdapter.ItemClickListener {
    private lateinit var binding: FragmentSeriesBinding
    private var navigationHostProvider: NavigationHostProvider? = null
    private val adapterSezon by lazy { SezonAdapter(this) }
    private val adapterSeries by lazy { SeriesAdapter(this) }

    // Добавляем переменную для ссылки на RecyclerView
    private var recyclerView: RecyclerView? = null

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
        binding = FragmentSeriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profileExitImageButton.setOnClickListener {
            findNavController().navigateUp()
        }

        // Связываем recyclerView с вашим layout
        recyclerView = binding.sezon

        val id = arguments?.getInt("id", 0)
        val token = arguments?.getString("token", "0")

        navigationHostProvider?.hideBottomNavigationBar(false)
        navigationHostProvider?.fullScreenMode(true)
        if (id != null) {
            loadMovieInfo(token.toString(), id)
        }
    }

    private fun loadMovieInfo(token: String, movieId: Int) {
        lifecycleScope.launch {
            try {
                val seasons = RetrofitService.apiiService.moviesSezon(token, movieId)
                adapterSezon.submitList(seasons)
                recyclerView?.adapter = adapterSezon
                adapterSezon.setSelectedItem(0)// Используем recyclerView
                Log.d("seasons", seasons.toString())
            } catch (e: Exception) {
                Log.d("id", movieId.toString())
                Log.d("token", token)
                Log.d("AAA", e.message.toString())
            }
        }
    }

    override fun onItemClick(id: List<Season>?) {
        id?.forEach { season ->
            adapterSeries.submitList(season.videos)
            binding.seriesRcView.adapter = adapterSeries
        }
    }

    override fun onItemClickSeries(link: String) {
        Toast.makeText(context, link, Toast.LENGTH_SHORT).show()

        // Внутри метода onItemClickSeries в SeriesFragment
        val youTubeFragment = YouTubePlayerFragment()
        val bundle = Bundle().apply { putString("videoLink", link) }

        youTubeFragment.arguments = bundle

        findNavController().navigate(R.id.action_seriesFragment_to_youTubePlayerFragment,bundle)
    }

}
