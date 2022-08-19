package com.hs.refrigerator_guard.UI

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Log
import android.view.animation.RotateAnimation
import com.hs.refrigerator_guard.R

class Shooter {

    // 화면 상 절대좌표 (좌상단 (0,0)에 이미지가 위치함)
    var x: Int = 0
    var y: Int = 0

    lateinit var shooterImg: Bitmap

    fun makeShooter(coordinateX: Int, coordinateY: Int, res: Resources, degrees: Float) {
        shooterImg = BitmapFactory.decodeResource(res, R.drawable.shooter)
        shooterImg = Bitmap.createBitmap(shooterImg, 0, 0, shooterImg.width, shooterImg.height, Matrix().apply { postRotate(degrees) }, true)
        //shooterImg = Bitmap.createScaledBitmap(shooterImg, width, height, , false)

        this.x = coordinateX
        this.y = coordinateY

    }

}