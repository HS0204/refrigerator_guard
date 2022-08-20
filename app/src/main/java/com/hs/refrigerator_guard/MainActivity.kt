package com.hs.refrigerator_guard

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
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
            Toast.makeText(this, "3초 뒤 게임을 종료합니다", Toast.LENGTH_SHORT).show()
            sleep(3000)
            System.exit(0)
        }

        binding.mainCharacter?.let { Glide.with(this).load(R.drawable.start_main).into(it) }
        binding.explainImgLeft?.let { Glide.with(this).load(R.drawable.start_food1).into(it) }
        binding.explainImgRight?.let { Glide.with(this).load(R.drawable.start_food2).into(it) }
        binding.start1?.let { Glide.with(this).load(R.drawable.start1).into(it) }
        binding.start2?.let { Glide.with(this).load(R.drawable.start6).into(it) }
        binding.start3?.let { Glide.with(this).load(R.drawable.start3).into(it) }
        binding.start4?.let { Glide.with(this).load(R.drawable.start4).into(it) }
        binding.start5?.let { Glide.with(this).load(R.drawable.start2).into(it) }
        binding.start6?.let { Glide.with(this).load(R.drawable.start5).into(it) }

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

    override fun onDestroy() {
        super.onDestroy()
        startMediaPlayer.stop()
        startMediaPlayer.release()
    }

    private fun musicStart() {
        startMediaPlayer = MediaPlayer.create(this, R.raw.start_music)
        startMediaPlayer.start()
        startMediaPlayer.isLooping = true
    }

}