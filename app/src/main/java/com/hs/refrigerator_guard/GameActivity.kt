package com.hs.refrigerator_guard

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hs.refrigerator_guard.databinding.ActivityGameBinding
import com.hs.refrigerator_guard.databinding.ActivityMainBinding

class GameActivity : AppCompatActivity() {

    private val binding by lazy { ActivityGameBinding.inflate(layoutInflater) }

    private lateinit var gameMediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        musicStart()
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