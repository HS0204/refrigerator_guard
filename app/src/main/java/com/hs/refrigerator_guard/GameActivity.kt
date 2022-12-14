package com.hs.refrigerator_guard

import android.graphics.Point
import android.media.MediaPlayer
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.hs.refrigerator_guard.UI.GameView
import com.hs.refrigerator_guard.databinding.ActivityGameBinding

var windowX = 0
var windowY = 0

class GameActivity : AppCompatActivity() {

    private val binding by lazy { ActivityGameBinding.inflate(layoutInflater) }

    private lateinit var gameMediaPlayer: MediaPlayer
    private lateinit var gameView: GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        musicStart()
        getWindowSize()

        gameView = GameView(this)

        feedingCount = 0

        gameView.createBackGround(width = windowX, height = windowY, 0, 0)
        gameView.createShooter(windowX/2 - 60, windowY/2 + 30)
        gameView.createFoods()
        gameView.createEnemies()
        gameView.countPlayTime()

        setContentView(gameView)

    }

    private fun getWindowSize() {
        val point = Point()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        windowManager.defaultDisplay.getSize(point)

        windowX = point.x
        windowY = point.y
    }

    override fun onResume() {
        super.onResume()
        gameView.resume()
    }

    override fun onPause() {
        super.onPause()
        gameView.pause()
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
        finish()
    }

    private fun musicStart() {
        gameMediaPlayer = MediaPlayer.create(this, R.raw.game_music)
        gameMediaPlayer.start()
        gameMediaPlayer.isLooping = true
    }

    override fun onBackPressed() {

    }
}