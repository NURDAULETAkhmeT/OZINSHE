
//    package com.example.ozinshe
//
//    import android.content.Context
//    import android.content.pm.ActivityInfo
//    import android.os.Bundle
//    import android.view.LayoutInflater
//    import android.view.View
//    import android.view.ViewGroup
//    import androidx.core.view.updateLayoutParams
//    import androidx.fragment.app.Fragment
//    import androidx.navigation.findNavController
//    import androidx.viewpager2.widget.MarginPageTransformer
//    import com.example.ozinshe.databinding.FragmentYouTubePlayerBinding
//    import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
//    import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
//    import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
//    import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
//    import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
//    import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
//    import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController
//
//    class YouTubePlayerFragment : Fragment() {
//        private lateinit var binding: FragmentYouTubePlayerBinding
//        private var navigationHostProvider: NavigationHostProvider? = null
//        private lateinit var youTubePlayerView: YouTubePlayerView
//
//        override fun onAttach(context: Context) {
//            super.onAttach(context)
//            if (context is NavigationHostProvider) {
//                navigationHostProvider = context
//            } else {
//                throw ClassCastException("$context must implement NavigationHostProvider")
//            }
//        }
//
//        override fun onCreateView(
//            inflater: LayoutInflater,
//            container: ViewGroup?,
//            savedInstanceState: Bundle?
//        ): View {
//            binding = FragmentYouTubePlayerBinding.inflate(inflater, container, false)
//            youTubePlayerView = binding.youtubePlayerView
//            youTubePlayerView.enableAutomaticInitialization = false
//            lifecycle.addObserver(youTubePlayerView)
//            return binding.root
//        }
//
//        override fun onCreate(savedInstanceState: Bundle?) {
//            super.onCreate(savedInstanceState)
//            // Установите ориентацию экрана в горизонтальный режим
//            requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
//        }
//
//        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//            super.onViewCreated(view, savedInstanceState)
//
//            navigationHostProvider?.hideBottomNavigationBar(false)
//            navigationHostProvider?.fullScreenMode(true)
//
//            val videoLink = arguments?.getString("videoLink", "")
//
//            playerYouTube(videoLink.toString())
//        }
//
//        private fun playerYouTube(movieLink: String) {
//            val youTubePlayerView = binding.youtubePlayerView
//
//            lifecycle.addObserver(youTubePlayerView)
//
//            val listener: YouTubePlayerListener = object : AbstractYouTubePlayerListener() {
//                override fun onReady(youTubePlayer: YouTubePlayer) {
//
//
//                    // using pre-made custom ui
//                    val defaultPlayerUiController = DefaultPlayerUiController(
//                        youTubePlayerView, youTubePlayer
//                    )
//                    defaultPlayerUiController.rootView.findViewById<View>(com.pierfrancescosoffritti.androidyoutubeplayer.R.id.drop_shadow_top)
//                        .apply {
//                            setBackgroundResource(R.drawable.button_x)
//                            updateLayoutParams {
//                                width = 40
//                                height = 40
//                            }
//                            setOnClickListener {
//                                findNavController().popBackStack()
//                            }
//                        }
////                        .apply {
////                            setBackgroundResource(R.drawable.prev_left_10)
////                        }
//
//                    youTubePlayerView.setCustomPlayerUi(defaultPlayerUiController.rootView)
//                    defaultPlayerUiController.showYouTubeButton(false)
//                    defaultPlayerUiController.showFullscreenButton(false)
//
//                    defaultPlayerUiController.rootView.findViewById<com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.views.YouTubePlayerSeekBar>(
//                        com.pierfrancescosoffritti.androidyoutubeplayer.R.id.youtube_player_seekbar
//                    ).setColor(
//                        resources.getColor(R.color.colorVideo, null)
//                    )
//
//                    youTubePlayer.loadOrCueVideo(lifecycle, movieLink, 0f)
//
//                    fun Int.toPx(): Int {
//                        val scale = resources.displayMetrics.density
//                        return (this * scale + 0.5f).toInt()
//                    }
//                }
//            }
//
//            // disable web ui
//            val options: IFramePlayerOptions = IFramePlayerOptions.Builder().controls(0).build()
//
//            youTubePlayerView.initialize(listener, options)
//
//        }
//
//        override fun onDestroy() {
//            super.onDestroy()
//            // Восстановите ориентацию экрана к исходному состоянию
//            requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
//        }
//    }
//
//}