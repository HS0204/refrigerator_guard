package com.hs.refrigerator_guard.UI

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import com.hs.refrigerator_guard.R
import kotlin.random.Random

class Food internal constructor(res: Resources, degrees: Float) {

    // 화면 상 절대좌표 (좌상단 (0,0)에 이미지가 위치함)
    var x: Int = 0
    var y: Int = 0

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
        var currentFood = foodList[rnd.nextInt(0, 6)]

        foodImg = BitmapFactory.decodeResource(res, currentFood)
        foodImg = Bitmap.createBitmap(foodImg, 0, 0, foodImg.width, foodImg.height, Matrix().apply { postRotate(degrees) }, true)
    }


}