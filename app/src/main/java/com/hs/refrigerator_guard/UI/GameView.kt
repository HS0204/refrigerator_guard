package com.hs.refrigerator_guard.UI

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.graphics.Rect
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceView
import com.hs.refrigerator_guard.EndingActivity
import com.hs.refrigerator_guard.feedingCount
import com.hs.refrigerator_guard.timer
import com.hs.refrigerator_guard.windowX
import java.lang.Exception
import kotlin.random.Random

@SuppressLint("ViewConstructor")
class GameView(context: Context): SurfaceView(context), Runnable {

    private lateinit var thread: Thread

    private var isPlaying: Boolean = false
    private var isGameOver: Boolean = false
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

    var target: Enemy = Enemy(resources)
    val trashFood = ArrayList<Food>()
    val trashEnemy = ArrayList<Enemy>()

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

        enemyHandler.postDelayed(object : Runnable {
            override fun run() {
                try {
                    newEnemy()
                    /*** 적군 리스폰 속도 ***/
                    if (timer < 10)
                        enemyHandler.postDelayed(this, 1300)
                    if (timer in 10..19)
                        enemyHandler.postDelayed(this, 1000)
                    if (timer in 20..29)
                        enemyHandler.postDelayed(this, 700)
                    if (timer >= 30)
                        enemyHandler.postDelayed(this, 500)
                } catch (e: Exception) {
                    }
            }
        }, 0)
    }

    val timerHandler = Handler()

    fun countPlayTime() {
        Thread{
            timer = 0

            timerHandler.postDelayed(object : Runnable {
                override fun run() {
                    try {
                        timer++
                        Log.d("TEST","게임 진행 시간: $timer")
                        timerHandler.postDelayed(this, 1000)
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

        if (!isPlaying) {
            val intent = Intent(context, EndingActivity::class.java)
            context.startActivity(intent)
            timerHandler.removeMessages(0)
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
        shootingFood(enemies)
        runningEnemy()
        remove()
    }

    private fun shootingFood(enemies: ArrayList<Enemy>) {
        if (enemies.size > 0)
            target = enemies[0]

        for (food in foods) {
            food.speed = 5 /*** 슈팅 속도 ***/
            //food.speed = rnd.nextInt(5, 7) /*** 슈팅 속도 ***/

            if (food.x > windowX)
                trashFood.add(food)

            if (food.x > target.x)
                food.x -= food.speed
            if (food.x < target.x)
                food.x += food.speed

            if (food.y > target.y)
                food.y -= food.speed
            if (food.y < target.y)
                food.y += food.speed
        }
    }

    private fun runningEnemy() {
        for (enemy in enemies) {
            enemy.speed = 10 /*** 적군 속도 ***/
            //enemy.speed = rnd.nextInt(10, 13) /*** 적군 속도 ***/
            movingEnemy(enemy, enemy.x, enemy.y)

            if (enemy.y < 0)
                trashEnemy.add(enemy)

            eating(enemy)
            if (Rect.intersects(enemy.getShape(), Rect(1600, 300, 1800, 500))) {
                isGameOver = true
            }
        }
    }

    private fun movingEnemy(enemy: Enemy, currentX: Int, currentY: Int) {
        if (currentY < 0)
            trashEnemy.add(enemy)

        if (currentY < 800 && currentX < 1500)
            enemy.y += enemy.speed
        if (currentY >= 800 && currentX < 1700)
            enemy.x += enemy.speed
        if (currentX >= 1700)
            enemy.y -= enemy.speed
    }

    private fun eating(enemy: Enemy) {
        for (food in foods) {
            if (Rect.intersects(enemy.getShape(), food.getShape())) {
                feedingCount++
                trashFood.add(food)
                trashEnemy.add(enemy)
            }
        }
    }

    private fun remove() {
        foods.removeAll(trashFood)
        enemies.removeAll(trashEnemy)
        /*
        for (food in trashFood)
            foods.remove(food)

        for (enemy in trashEnemy)
            enemies.remove(enemy)
         */
    }

    private fun draw() {
        if (holder.surface.isValid) {
            paint = Paint()
            val canvas = holder.lockCanvas()

            canvas.drawBitmap(background.bgImg, background.x.toFloat(), background.y.toFloat(), paint)

            if (isGameOver) {
                isPlaying = false
            }

            canvas.drawBitmap(shooter.shooting(), shooter.x.toFloat(), shooter.y.toFloat(), paint)

            for (food in foods)
                canvas.drawBitmap(food.foodImg, food.x.toFloat(), food.y.toFloat(), paint)

            for (enemy in enemies)
                canvas.drawBitmap(enemy.enemyImg, enemy.x.toFloat(), enemy.y.toFloat(), paint)

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