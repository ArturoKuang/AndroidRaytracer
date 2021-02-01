package com.example.raytracer

import kotlin.math.tan

class Camera(
    lookFrom: Point3,
    lookAt: Point3,
    vUp: Vec3,
    fov: Float,
    aspectRatio: Float
) {

    private val theta = degreesToRadians(fov)
    private val h = tan(theta / 2f)
    private val viewportHeight: Float = 2f * h
    private val viewportWidth: Float = aspectRatio * viewportHeight

    //orthonormal basis
    private val w = unitVector(lookFrom - lookAt) //forward
    private val u = unitVector(cross(vUp, w)) //side
    private val v = cross(w, u) //up

    private val origin = lookFrom
    private val horizontal = u * viewportWidth
    private val vertical = v * viewportHeight

    private val lowerLeftCorner = origin -
            horizontal / 2.0f -
            vertical / 2.0f - w


    fun getRay(s: Float, t: Float): Ray {
        return Ray(origin, lowerLeftCorner + horizontal * s + vertical * t - origin)
    }
}