package com.example.raytracer

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import timber.log.Timber
import kotlin.math.sqrt
import kotlin.random.Random

class RaytraceView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val backgroundRect = Rect()
    private val bitmap = Bitmap.createBitmap(1200, 700, Bitmap.Config.ARGB_8888)

    private val aspectRatio: Float = 16.0f / 9.0f
    private val imageWidth: Int = 400
    private val imageHeight: Int = (imageWidth / aspectRatio).toInt()

    private val viewportHeight: Float = 2.0f
    private val viewportWidth: Float = viewportHeight * aspectRatio
    private val focalLength: Float = 1.0f

    private val origin = Point3(.0f, .0f, .0f)
    private val horizontal = Vec3(viewportWidth, .0f, .0f)
    private val vertical = Vec3(.0f, viewportHeight, .0f)
    private val lowerLeftCorner =
            origin -
            horizontal / 2.0f -
            vertical / 2.0f -
            Vec3(.0f, .0f, focalLength)


    private fun rayColor(r: Ray): Color3 {
        var t: Float = hitSphere(Point3(.0f, .0f, -1.0f), 0.5f, r)
        if(t > 0.0) {
            var n: Vec3 = unitVector(r.at(t) - Vec3(0.0f, 0.0f, -1.0f))
            n = Vec3(n.x, -n.y, n.z)
            return Color3(n.x+1, n.y+1, n.z+1)*0.5f
        }

        val unitDirection = unitVector(r.direction)
        t = .5f*(unitDirection.y + 1.0f)
        return  Color3(0.0f, 0.0f, 0.0f)*(1.0f-t) + Color3(.5f, .7f, 1.0f)*t
    }

    private fun hitSphere(center: Point3, radius: Float, r: Ray): Float {
        val oc = r.origin - center
        val a = r.direction.lengthSqr()
        val halfB = dot(oc, r.direction)
        val c = oc.lengthSqr() - radius*radius
        val discriminant = halfB*halfB - a*c

        return if(discriminant < 0) {
            -1.0f
        } else {
            (-halfB - sqrt(discriminant)) / (a)
        }
    }

    init {
        for (row in 0 until bitmap.height) {
            for (col in 0 until bitmap.width) {
                val u: Float = col.toFloat() / (bitmap.width-1)
                val v: Float = row.toFloat() / (bitmap.height-1)
                val r = Ray(origin, lowerLeftCorner + horizontal*u + vertical*v - origin)

                val pixel = color3ToArgb(rayColor(r))
                bitmap.setPixel(col, row, pixel)
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

        //canvas.drawBitmap(bitmap, null, backgroundRect, null)

        canvas.drawBitmap(bitmap, 0.0f, 0.0f, null)
    }

}