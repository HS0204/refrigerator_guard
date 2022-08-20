package com.hs.refrigerator_guard.UI

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.os.Handler
import android.provider.SyncStateContract.Helpers.update
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceView
import com.hs.refrigerator_guard.windowX
import java.lang.Exception
import kotlin.random.Random

@SuppressLint("ViewConstructor")
class GameView(context: Context): SurfaceView(context), Runnable {

    private lateinit var thread: Thread
    private lateinit var enemyThread: Thread

    private var isPlaying: Boolean = false
    private lateinit var paint: Paint

    private lateinit var background: Background
    private lateinit var shooter: Shooter

    var shooterX: Int = 0
    var shooterY: Int = 0

    var toDegree = 0f

    private lateinit var foods: ArrayList<Food>
    private lateinit var enemies: ArrayList<Enemy>

    val enemyHandler = Handler()
    var rnd = Random

    fun createBackGround(width: Int, height: Int, coordinateX: Int, coordinateY: Int) {
        background = Background(width, height, coordinateX, coordinateY, resources)
    }

    fun createShooter(coordinateX: Int, coordinateY: Int) {
        shooterX = coordinateX
        shooterY = coordinateY
        shooter = Shooter(this, coordinateX, coordinateY, resources, degrees = toDegree)
    }

    fun createFoods() {
        foods = ArrayList<Food>()
    }

    fun createEnemies() {
        enemies = ArrayList<Enemy>()

        Thread {
            enemyHandler.postDelayed(object : Runnable {
                override fun run() {
                    try {
                        newEnemy()
                        enemyHandler.postDelayed(this, 1000)
                    } catch (e: Exception) {
                    }
                }
            }, 0)
        }.start()
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
            createShooter(shooterX, shooterY)
        }
        else {
            Log.d("TEST", "오른쪽")
            toDegree -= 20f
            shooter.toShoot = true
            createShooter(shooterX, shooterY)
        }

        return super.onTouchEvent(event)
    }

    private fun update() {
        shootingFood()
        runningEnemy()
    }

    private fun shootingFood() {
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

    private fun runningEnemy() {
        val trashEnemy = ArrayList<Enemy>()

        for (enemy in enemies) {
            movingEnemy(enemy, enemy.x, enemy.y)
            enemy.speed = rnd.nextInt(5, 10)

            if (enemy.y < 0) {
                trashEnemy.add(enemy)
            }
        }

        for (enemy in trashEnemy) {
            enemies.remove(enemy)
        }
    }

    private fun movingEnemy(enemy: Enemy, currentX: Int, currentY: Int) {
        if (currentY < 800 && currentX < 1500) {
            enemy.y += enemy.speed
        }
        if (currentY >= 800 && currentX < 1700) {
            enemy.x += enemy.speed
        }
        if (currentX >= 1700) {
            enemy.y -= enemy.speed
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

            for (enemy in enemies) {
                canvas.drawBitmap(enemy.enemyImg, enemy.x.toFloat(), enemy.y.toFloat(), paint)
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
        val food = Food(resources)
        food.x = shooter.x
        food.y = shooter.y

        foods.add(food)
    }

    fun newEnemy() {
        val enemy = Enemy(resources)

        enemies.add(enemy)
    }

}