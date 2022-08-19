package com.hs.refrigerator_guard.UI

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceView
import com.hs.refrigerator_guard.windowX

@SuppressLint("ViewConstructor")
class GameView(context: Context): SurfaceView(context), Runnable {

    private lateinit var thread: Thread
    private var isPlaying: Boolean = false
    private lateinit var paint: Paint

    private lateinit var background: Background
    private lateinit var shooter: Shooter

    var x: Int = 0
    var y: Int = 0

    var toDegree = 0f

    fun createBackGround(width: Int, height: Int, coordinateX: Int, coordinateY: Int) {
        background = Background()
        background.makeBg(width, height, coordinateX, coordinateY, resources)
    }

    fun createShooter(coordinateX: Int, coordinateY: Int) {
        x = coordinateX
        y = coordinateY
        shooter = Shooter()
        shooter.makeShooter(coordinateX, coordinateY, resources, degrees = toDegree)
    }

    override fun run() {
        while (isPlaying) {
            update()
            draw()
            sleep()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (event!!.x < windowX / 2 )  {
            Log.d("TEST", "왼쪽")
            toDegree += 20f
            createShooter(x, y)
        }
        else {
            Log.d("TEST", "오른쪽")
            toDegree -= 20f
            createShooter(x, y)
        }

        return super.onTouchEvent(event)
    }

    private fun update() {

    }

    private fun draw() {
        if (holder.surface.isValid) {
            paint = Paint()
            val canvas = holder.lockCanvas()

            canvas.drawBitmap(background.bgImg, background.x.toFloat(), background.y.toFloat(), paint)

            canvas.drawBitmap(shooter.shooterImg, shooter.x.toFloat(), shooter.y.toFloat(), paint)

            holder.unlockCanvasAndPost(canvas)
        }

    }

    private fun sleep() {
        try {
            Thread.sleep(15)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }


    fun resume() {
        isPlaying = true
        thread = Thread(this)
        thread.start()
    }

    fun pause() {
        try {
            isPlaying = false
            thread.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

}