package com.hs.refrigerator_guard.UI

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Log
import com.hs.refrigerator_guard.R

class Shooter internal constructor(gameView: GameView, coordinateX: Int, coordinateY: Int, res: Resources, degrees: Float) {

    // 화면 상 절대좌표 (좌상단 (0,0)에 이미지가 위치함)
    var x: Int = 0
    var y: Int = 0

    var shooterImg: Bitmap

    var toShoot:Boolean = true
    private val gameView = gameView

    init {
        shooterImg = BitmapFactory.decodeResource(res, R.drawable.shooter)
        shooterImg = Bitmap.createBitmap(shooterImg, 0, 0, shooterImg.width, shooterImg.height, Matrix().apply { postRotate(degrees) }, true)

        this.x = coordinateX
        this.y = coordinateY
    }

    fun shooting(): Bitmap {
        if (toShoot) {
            toShoot = false
            gameView.newFood()
            return shooterImg
        }
        return shooterImg
    }

}