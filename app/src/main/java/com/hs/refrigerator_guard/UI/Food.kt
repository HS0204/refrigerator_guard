package com.hs.refrigerator_guard.UI

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.Rect
import com.hs.refrigerator_guard.R
import kotlin.random.Random

class Food internal constructor(res: Resources) {

    // 화면 상 절대좌표 (좌상단 (0,0)에 이미지가 위치함)
    var x: Int = 0
    var y: Int = 0
    var width: Int = 0
    var height: Int = 0

    var foodImg: Bitmap

    val foodList = arrayListOf<Int>(
        R.drawable.food1,
        R.drawable.food2,
        R.drawable.food3,
        R.drawable.food4,
        R.drawable.food5,
        R.drawable.food6,
        R.drawable.food7
    )

    init {
        var rnd = Random
        var currentFood = foodList[rnd.nextInt(0, 7)]

        foodImg = BitmapFactory.decodeResource(res, currentFood)
        foodImg = Bitmap.createScaledBitmap(foodImg, foodImg.width, foodImg.height, true)

        this.width = foodImg.width
        this.height = foodImg.height
    }

    fun getShape(): Rect {
        return Rect(x, y, x+ width, y+height)
    }

}