package com.example.raytracer

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlin.random.Random

class RaytraceView(context: Context, attrs: AttributeSet): View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val backgroundRect = Rect()
    private val bitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888)

    init {
        for (row in 0 until bitmap.height) {
            for(col in 0 until bitmap.width) {
                val a = Random.nextInt(255)
                val r = Random.nextInt(255)
                val g = Random.nextInt(255)
                val b = Random.nextInt(255)

                bitmap.setPixel(col, row, Color.argb(a, r, g, b))
            }
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        backgroundRect.set(0, 0, width, height)

        canvas.drawBitmap(bitmap, null, backgroundRect, null)
    }

}