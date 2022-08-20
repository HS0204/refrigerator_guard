package com.hs.refrigerator_guard

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.hs.refrigerator_guard.databinding.ActivityEndingBinding

var timer: Int = 0
var feedingCount: Int = 0

class EndingActivity : AppCompatActivity() {

    private val binding by lazy { ActivityEndingBinding.inflate(layoutInflater) }

    private lateinit var endMediaPlayer: MediaPlayer
    private lateinit var effectMediaPlayer: MediaPlayer

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.playTime!!.startAnimation(AnimationUtils.loadAnimation(this, R.anim.end_playtime))
        binding.myScore!!.startAnimation(AnimationUtils.loadAnimation(this, R.anim.end_score))
        binding.gameExplain!!.startAnimation(AnimationUtils.loadAnimation(this, R.anim.end_text))
        binding.restartBtn!!.startAnimation(AnimationUtils.loadAnimation(this, R.anim.end_btn))
        binding.endBtn!!.startAnimation(AnimationUtils.loadAnimation(this, R.anim.end_btn))

        binding.playTime!!.text = "${formatDuration(timer)} 동안 냉장고를 지켰어요"
        binding.myScore!!.text = "${feedingCount}마리의 친구에게 간식을 먹여줬어요"

        binding.restartBtn?.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        binding.endBtn?.setOnClickListener {
            Toast.makeText(this, "3초 뒤 게임을 종료합니다", Toast.LENGTH_SHORT).show()
            Thread.sleep(3000)
            ActivityCompat.finishAffinity(this)
            System.exit(0)
        }
    }

    private fun formatDuration (duration: Int): String{
        var min = duration / 60
        val sec = duration % 60

        return String.format("%d분 %d초", min, sec)
    }

    override fun onStart() {
        super.onStart()
        musicStart()
    }

    override fun onStop() {
        super.onStop()
        endMediaPlayer.stop()
        endMediaPlayer.release()
    }

    override fun onDestroy() {
        super.onDestroy()
        endMediaPlayer.stop()
        endMediaPlayer.release()
        effectMediaPlayer.stop()
        effectMediaPlayer.release()
    }

    private fun musicStart() {
        effectMediaPlayer = MediaPlayer.create(this,  R.raw.end_effect)
        effectMediaPlayer.start()

        endMediaPlayer = MediaPlayer.create(this,  R.raw.end_music)
        endMediaPlayer.start()
    }

    override fun onBackPressed() {

    }
}