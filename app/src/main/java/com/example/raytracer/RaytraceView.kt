package com.example.raytracer

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.random.Random

private const val MAXDEPTHCALLS = 50

class RaytraceView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val samplesPerPixel = 50
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val backgroundRect = Rect()

    private val lookFrom = Point3(13f, 2f, 3f)
    private val lookAt = Point3(0f, 0f, 0f)
    private val up = Point3(0f, 1f, 0f)

    //private val distToFocus = (lookFrom - lookAt).length()
    private val distToFocus = 10f
    private val aperture = 0.1f
    private val aspectRatio = 3f / 2f
    private val fov = 20f

    private val camera = Camera(
        lookFrom,
        lookAt,
        up,
        fov,
        aspectRatio,
        aperture,
        distToFocus
    )

    private val imageWidth: Int = 1250
    private val imageHeight: Int = (imageWidth / aspectRatio).toInt()

    private val world = HittableList()
    private val bitmap = Bitmap.createBitmap(
        imageWidth,
        imageHeight,
        Bitmap.Config.ARGB_8888
    )

    private fun rayColor(r: Ray, aWorld: Hittable, depth: Int): Color3 {
        if (depth <= 0) {
            return Color3()
        }

        val worldHit = aWorld.hit(r, 0.001f, infinity)
        if (worldHit != null) {
            val incidentRay: IncidentRay? = worldHit.material.scatter(r, worldHit)

            if (incidentRay != null) {
                return incidentRay.attenuation *
                        rayColor(incidentRay.scattered, world, depth - 1)
            }

            return Color3(0f, 0f, 0f)
        }

        val unitDirection = unitVector(r.direction)
        val t = .5f * (unitDirection.y + 1.0f)
        return Color3(0.0f, 0.0f, 0.0f) * (1.0f - t) + Color3(.5f, .7f, 1.0f) * t
    }

    init {
//        val materialGround = Lambertian(Color3(.8f, .8f, 0f))
//        val materialCenter = Lambertian(Color3(.1f, .2f, .5f))
//        val materialLeft = Dielectric(1.5f)
//        val materialRight = Metal(Color3(.8f, .6f, .2f), 0f)
//
//
//        val sphere1 = Sphere(Point3(0f, -100.5f, -1.0f), 100.0f, materialGround)
//        val sphere2 = Sphere(Point3(0f, 0f, -1.0f), 0.5f, materialCenter)
//        val sphere3 = Sphere(Point3(-1.0f, 0f, -1.0f), -.45f, materialLeft)
//        val sphere4 = Sphere(Point3(1.0f, 0f, -1.0f), 0.5f, materialRight)
//
//        world.objects.add(sphere1)
//        world.objects.add(sphere2)
//        world.objects.add(sphere3)
//        world.objects.add(sphere4)

        randomScene()

        for (row in bitmap.height - 1 downTo 0) {
            for (col in 0 until bitmap.width) {
                var pixel = Color3()
                for (s in 0 until samplesPerPixel) {
                    val u: Float = (col.toFloat() + Random.nextFloat()) / (bitmap.width - 1)
                    val v: Float = (row.toFloat() + Random.nextFloat()) / (bitmap.height - 1)

                    val ray = camera.getRay(u, v)
                    pixel = rayColor(ray, world, MAXDEPTHCALLS) + pixel
                }

                val pixelColor = color3ToArgb(pixel, samplesPerPixel.toFloat())
                val newRow = bitmap.height - 1 - row
                bitmap.setPixel(col, newRow, pixelColor)
            }
        }
    }

    private fun randomScene() {
        val groundMaterial = Lambertian(Color3(.5f, .5f, .5f))
        val groundObject = Sphere(Point3(0f, -1000f, 0f), 1000f, groundMaterial)
        world.objects.add(groundObject)

        for (i in -11 until 11) {
            for (j in -11 until 11) {
                val chooseMat = Random.nextFloat()
                val center =
                    Point3(i + Random.nextFloat() * .9f, .2f, j + .9f * Random.nextFloat())

                if ((center - Point3(4f, .2f, 0f)).length() > .9f) {
                    var sphereMaterial: Material?

                    when {
                        chooseMat < 0.8 -> {
                            // diffuse
                            val albedo: Vec3 = random() * random()
                            sphereMaterial = Lambertian(albedo)
                            world.objects.add(Sphere(center, .2f, sphereMaterial))
                        }
                        chooseMat < 0.95 -> {
                            // metal
                            val albedo: Vec3 = random(.5f, 1f)
                            val fuzz = Random.nextFloat(0f, .5f)
                            sphereMaterial = Metal(albedo, fuzz)
                            world.objects.add(Sphere(center, .2f, sphereMaterial))
                        }
                        else -> {
                            // glass
                            sphereMaterial = Dielectric(1.5f)
                            world.objects.add(Sphere(center, .2f, sphereMaterial))
                        }
                    }
                }
            }
        }


        val material1 = Dielectric(1.5f)
        world.objects.add(Sphere(Point3(0f, 1f, 0f), 1.0f, material1));

        val material2 = Lambertian(Color3(0.4f, 0.2f, 0.1f));
        world.objects.add(Sphere(Point3(-4f, 1f, 0f), 1.0f, material2));

        val material3 = Metal(Color3(0.7f, 0.6f, 0.5f), 0.0f);
        world.objects.add(Sphere(Point3(4f, 1f, 0f), 1.0f, material3));

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