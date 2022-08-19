package com.hs.refrigerator_guard.UI

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.hs.refrigerator_guard.R

class Background internal constructor(width:Int, height: Int, coordinateX: Int, coordinateY: Int, res: Resources){

    // 화면 상 절대좌표 (좌상단 (0,0)에 이미지가 위치함)
    var x: Int = 0
    var y: Int = 0

    var bgImg: Bitmap

    init {
        bgImg = BitmapFactory.decodeResource(res, R.drawable.background)
        bgImg = Bitmap.createScaledBitmap(bgImg, width, height, false)

        this.x = coordinateX
        this.y = coordinateY
    }

}