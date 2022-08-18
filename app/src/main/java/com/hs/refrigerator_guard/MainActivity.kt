package com.hs.refrigerator_guard

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.hs.refrigerator_guard.databinding.ActivityMainBinding
import java.lang.Thread.sleep
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var startMediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.startBtn?.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        binding.endBtn?.setOnClickListener {
            Toast.makeText(this, "3초 뒤 게임을 종료합니다.", Toast.LENGTH_SHORT).show()
            sleep(3000)
            exitProcess(0)
        }

    }

    override fun onStart() {
        super.onStart()
        musicStart()
    }

    override fun onStop() {
        Log.d("TEST", " 스탑")
        super.onStop()
        startMediaPlayer.stop()
        startMediaPlayer.release()
    }

    private fun musicStart() {
        startMediaPlayer = MediaPlayer.create(this, R.raw.start_music)
        startMediaPlayer.start()
        startMediaPlayer.isLooping = true
    }

}