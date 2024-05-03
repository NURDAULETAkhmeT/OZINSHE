package com.example.ozinshe.Adapter.DetailAdapter

import android.view.View
import android.widget.ImageButton
import android.widget.RelativeLayout
import androidx.navigation.NavController
import com.example.ozinshe.R
import com.example.ozinshe.YouTubePlayerFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.utils.FadeViewHelper
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.views.YouTubePlayerSeekBar
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.views.YouTubePlayerSeekBarListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker

class CustomPlayerUiController(
    controlsUi: View,
    private val youTubePlayer: YouTubePlayer,
    private val navController: NavController
) : AbstractYouTubePlayerListener() {
    private val playerTracker = YouTubePlayerTracker()
    init {
        youTubePlayer.addListener(playerTracker)
        initViews(controlsUi)
    }

    private fun initViews(view: View) {
        val container = view.findViewById<View>(R.id.container)
        val relativeLayout = view.findViewById<RelativeLayout>(R.id.root)
        val seekBar = view.findViewById<YouTubePlayerSeekBar>(R.id.playerSeekbar)
        val pausePlay = view.findViewById<ImageButton>(R.id.playPauseButton)
        val exiButton = view.findViewById<ImageButton>(R.id.button_x)
        var peremotka10go = view.findViewById<ImageButton>(R.id.prevRightButton)
        youTubePlayer.addListener(seekBar)

        val seekBarOb = object : YouTubePlayerSeekBarListener {
                override fun seekTo(time: Float) {
                youTubePlayer.seekTo(time)
            }
        }

        seekBar.youtubePlayerSeekBarListener = seekBarOb

        pausePlay.setOnClickListener {
            if (playerTracker.state == PlayerConstants.PlayerState.PLAYING) {
                pausePlay.setImageResource(R.drawable.pause_svgrepo_com)
                youTubePlayer.pause()
            } else {
                youTubePlayer.play()
                pausePlay.setImageResource(R.drawable.play_svgrepo_com)
            }
        }

        exiButton.setOnClickListener {
            youTubePlayer.pause()
            navController.popBackStack()
        }




        val fadeViewHelper = FadeViewHelper(container)
        fadeViewHelper.animationDuration = FadeViewHelper.DEFAULT_ANIMATION_DURATION
        fadeViewHelper.fadeOutDelay = FadeViewHelper.DEFAULT_FADE_OUT_DELAY
        youTubePlayer.addListener(fadeViewHelper)

        relativeLayout.setOnClickListener {
            fadeViewHelper.toggleVisibility()
        }

        container.setOnClickListener {
            fadeViewHelper.toggleVisibility()
        }
    }
}
