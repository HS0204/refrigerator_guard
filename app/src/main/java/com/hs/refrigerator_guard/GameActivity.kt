package com.hs.refrigerator_guard

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.animation.RotateAnimation
import androidx.appcompat.app.AppCompatActivity
import com.hs.refrigerator_guard.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {

    private val binding by lazy { ActivityGameBinding.inflate(layoutInflater) }

    private lateinit var gameMediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        musicStart()

        var currentDegree = 0f
        var toDegree = 0f

        binding.leftRotateBtn?.setOnClickListener {
            toDegree += 10f

            val rotateAnimation = RotateAnimation(
                currentDegree,
                toDegree,
                RotateAnimation.RELATIVE_TO_SELF,
                .5f,
                RotateAnimation.RELATIVE_TO_SELF,
                .5f
            )
            rotateAnimation.duration = 300
            rotateAnimation.fillAfter = true

            binding.player?.startAnimation(rotateAnimation)

            val location = IntArray(2)
            binding.player?.getLocationOnScreen(location)

            currentDegree += 10f
            Log.d("TEST", "현재 각도는 $currentDegree, 이동할 각도는 $toDegree")
            Log.d("TEST", "좌표 (${location[0]}, ${location[1]})")
        }

        binding.rightRotateBtn?.setOnClickListener {
            toDegree -= 10f

            val rotateAnimation = RotateAnimation(
                currentDegree,
                toDegree,
                RotateAnimation.RELATIVE_TO_SELF,
                .5f,
                RotateAnimation.RELATIVE_TO_SELF,
                .5f
            )
            rotateAnimation.duration = 300
            rotateAnimation.fillAfter = true

            binding.player?.startAnimation(rotateAnimation)

            currentDegree -= 10f
            Log.d("TEST", "현재 각도는 $currentDegree, 이동할 각도는 $toDegree")
            Log.d("TEST", "좌표 (${binding.player?.x}, ${binding.player?.y})")
        }

    }


    override fun onStop() {
        super.onStop()
        gameMediaPlayer.pause()
    }

    override fun onRestart() {
        super.onRestart()
        gameMediaPlayer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        gameMediaPlayer.reset()
        gameMediaPlayer.release()
    }

    private fun musicStart() {
        gameMediaPlayer = MediaPlayer.create(this, R.raw.game_music)
        gameMediaPlayer.start()
        gameMediaPlayer.isLooping = true
    }

    override fun onBackPressed() {
        super.onBackPressed() // 나중에 지우기 뒤로 못 가게
    }
}