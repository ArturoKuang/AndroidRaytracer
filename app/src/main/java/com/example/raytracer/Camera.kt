package com.example.raytracer

class Camera {
    val aspectRatio: Float = 16.0f / 9.0f
    val imageWidth: Int = 400
    val imageHeight: Int = (imageWidth / aspectRatio).toInt()

    val viewportHeight: Float = 2.0f
    val viewportWidth: Float = viewportHeight * aspectRatio
    val focalLength: Float = 1.0f

    val origin = Point3(.0f, .0f, .0f)
    val horizontal = Vec3(viewportWidth, .0f, .0f)
    val vertical = Vec3(.0f, viewportHeight, .0f)
    val lowerLeftCorner =
        origin -
                horizontal / 2.0f -
                vertical / 2.0f -
                Vec3(.0f, .0f, focalLength)

    fun getRay(u: Float, v: Float): Ray {
        return Ray(origin, lowerLeftCorner + horizontal*u + vertical*v - origin)
    }
}