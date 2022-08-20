package com.hs.refrigerator_guard.UI

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import com.hs.refrigerator_guard.R
import kotlin.random.Random

class Enemy internal constructor(res: Resources) {

    // 화면 상 절대좌표 (좌상단 (0,0)에 이미지가 위치함)
    var x: Int = 350
    var y: Int = 500
    var width: Int = 0
    var height: Int = 0

    var speed: Int = 0

    var enemyImg: Bitmap

    val enemyList = arrayListOf<Int>(
        R.drawable.enemy1,
        R.drawable.enemy2,
        R.drawable.enemy3,
        R.drawable.enemy4,
        R.drawable.enemy5,
        R.drawable.enemy6
    )

    init {
        var rnd = Random
        var currentFood = enemyList[rnd.nextInt(0, 6)]

        enemyImg = BitmapFactory.decodeResource(res, currentFood)
        enemyImg = Bitmap.createScaledBitmap(enemyImg, enemyImg.width, enemyImg.height, true)

        this.width = enemyImg.width
        this.height = enemyImg.height
    }

    fun eating(): Rect {
        return Rect()
    }


}