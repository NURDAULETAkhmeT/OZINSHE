package com.example.ozinshe

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ozinshe.Adapter.DetailAdapter.CustomPlayerUiController
import com.example.ozinshe.databinding.FragmentYouTubePlayerBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


class YouTubePlayerFragment : Fragment() {
    private lateinit var binding: FragmentYouTubePlayerBinding
    private var navigationHostProvider: NavigationHostProvider? = null
    private lateinit var youTubePlayerView: YouTubePlayerView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NavigationHostProvider) {
            navigationHostProvider = context
        } else {
            throw ClassCastException("$context должен реализовывать NavigationHostProvider")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentYouTubePlayerBinding.inflate(inflater, container, false)
        youTubePlayerView = binding.youtubePlayerView
        youTubePlayerView.enableAutomaticInitialization = false
        lifecycle.addObserver(youTubePlayerView)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Установить ориентацию экрана в горизонтальный режим
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        navigationHostProvider?.hideBottomNavigationBar(false)
        navigationHostProvider?.fullScreenMode(true)

        val videoLink = arguments?.getString("videoLink", "")

        youTubePlayerView.apply {
            lifecycle.addObserver(this)


            val customPlayerUi = inflateCustomPlayerUi(R.layout.custom_controls_layout)

            val listener = object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    val customPlayerUiController = CustomPlayerUiController(
                        customPlayerUi,
                        youTubePlayer,
                        findNavController(),
                    )

                    youTubePlayer.addListener(customPlayerUiController)
                    youTubePlayer.cueVideo(videoLink.toString(), 0f)
                }
            }

            // Отключить веб-интерфейс
            val options = IFramePlayerOptions.Builder().controls(0).build()
            initialize(listener, options)

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Восстановить ориентацию экрана в исходное состояние
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }
}
