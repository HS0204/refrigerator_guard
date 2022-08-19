package com.hs.refrigerator_guard.UI

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceView
import com.hs.refrigerator_guard.windowX
import kotlin.math.log

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

    private lateinit var foods: ArrayList<Food>

    fun createBackGround(width: Int, height: Int, coordinateX: Int, coordinateY: Int) {
        background = Background(width, height, coordinateX, coordinateY, resources)
    }

    fun createShooter(coordinateX: Int, coordinateY: Int) {
        x = coordinateX
        y = coordinateY
        shooter = Shooter(this, coordinateX, coordinateY, resources, degrees = toDegree)
    }

    fun createFoods() {
        foods = ArrayList<Food>()
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
            shooter.toShoot = true
            createShooter(x, y)
        }
        else {
            Log.d("TEST", "오른쪽")
            toDegree -= 20f
            shooter.toShoot = true
            createShooter(x, y)
        }

        return super.onTouchEvent(event)
    }

    private fun update() {
        val trash = ArrayList<Food>()

        for (food in foods) {
            if (food.x > windowX) {
                trash.add(food)
            }

            food.x += 20
        }

        for (food in trash) {
            foods.remove(food)
        }
    }

    private fun draw() {
        if (holder.surface.isValid) {
            paint = Paint()
            val canvas = holder.lockCanvas()

            canvas.drawBitmap(background.bgImg, background.x.toFloat(), background.y.toFloat(), paint)

            canvas.drawBitmap(shooter.shooting(), shooter.x.toFloat(), shooter.y.toFloat(), paint)

            for (food in foods) {
                canvas.drawBitmap(food.foodImg, food.x.toFloat(), food.y.toFloat(), paint)
            }

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

    fun newFood() {
        val food = Food(resources, 0f)
        food.x = shooter.x + 30
        food.y = shooter.y + 20

        foods.add(food)
    }

}