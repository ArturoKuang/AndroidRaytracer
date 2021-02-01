package com.example.raytracer

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.random.Random

class RaytraceView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val samplesPerPixel = 50
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val backgroundRect = Rect()
    private val bitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888)
    private val camera = Camera()

    private val world = HittableList()


//    private fun rayColor(r: Ray, aWorld: Hittable, depth: Int): Color3 {
//        if (depth <= 0) {
//            return Color3()
//        }
//
//        val worldHit = aWorld.hit(r, 0.001f, infinity)
//        if (worldHit != null) {
//            //val target: Point3 = worldHit.p + worldHit.normal + randomUnitVector()
//            val target: Point3 = worldHit.p + randomInHemisphere(worldHit.normal)
//            return rayColor((Ray(worldHit.p, target - worldHit.p)), world, depth - 1) * .5f
//        }
//
//        val unitDirection = unitVector(r.direction)
//        val t = .5f * (unitDirection.y + 1.0f)
//        return Color3(0.0f, 0.0f, 0.0f) * (1.0f - t) + Color3(.5f, .7f, 1.0f) * t
//    }

    private fun rayColor(r: Ray, aWorld: Hittable, depth: Int): Color3 {
        if (depth <= 0) {
            return Color3()
        }

        val worldHit = aWorld.hit(r, 0.001f, infinity)
        if (worldHit != null) {
            val incidentRay: IncidentRay? = worldHit.material.scatter(r, worldHit)

            if(incidentRay != null) {
                return incidentRay.attenuation *
                        rayColor(incidentRay.scattered, world, depth-1)
            }

            return Color3(0f, 0f, 0f)
        }

        val unitDirection = unitVector(r.direction)
        val t = .5f * (unitDirection.y + 1.0f)
        return Color3(0.0f, 0.0f, 0.0f) * (1.0f - t) + Color3(.5f, .7f, 1.0f) * t
    }

    private fun randomInHemisphere(normal: Vec3): Vec3 {
        val inUnitSphere = randomInUnitSphere()
        return if (dot(inUnitSphere, normal) > 0f) {
            inUnitSphere
        } else {
            inUnitSphere * -1f
        }
    }

    init {
        val sphere1 = Sphere(Point3(.0f, .0f, -1.0f), 0.5f)
        val sphere2 = Sphere(Point3(.0f, -100.5f, -1.0f), 100.0f)
        val maxDepth = 50

        world.objects.add(sphere1)
        world.objects.add(sphere2)

        for (row in bitmap.height - 1 downTo 0) {
            for (col in 0 until bitmap.width) {
                var pixel = Color3()
                for (s in 0 until samplesPerPixel) {
                    val u: Float = (col.toFloat() + Random.nextFloat()) / (bitmap.width - 1)
                    val v: Float = (row.toFloat() + Random.nextFloat()) / (bitmap.height - 1)

                    val ray = camera.getRay(u, v)
                    pixel = rayColor(ray, world, 50) + pixel
                }

                val pixelColor = color3ToArgb(pixel, samplesPerPixel.toFloat())
                val newRow = bitmap.height - 1 - row
                bitmap.setPixel(col, newRow, pixelColor)
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

        backgroundRect.set(0, 0, width, height / 2)

        canvas.drawBitmap(bitmap, null, backgroundRect, paint)

        //canvas.drawBitmap(bitmap, 0.0f, 0.0f, null)
    }

}