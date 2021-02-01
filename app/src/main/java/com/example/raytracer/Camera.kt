package com.example.raytracer

import kotlin.math.tan

class Camera(
    lookFrom: Point3,
    lookAt: Point3,
    vUp: Vec3,
    fov: Float,
    aspectRatio: Float,
    aperture: Float,
    focusDist: Float
) {

    private val theta: Float = degreesToRadians(fov)
    private val h: Float = tan(theta / 2f)
    private val viewportHeight: Float = 2f * h
    private val viewportWidth: Float = aspectRatio * viewportHeight

    //orthonormal basis
    private val w: Vec3 = unitVector(lookFrom - lookAt) //forward
    private val u: Vec3 = unitVector(cross(vUp, w)) //side
    private val v: Vec3 = cross(w, u) //up

    private val origin: Vec3 = lookFrom
    private val horizontal: Vec3 = u * viewportWidth * focusDist
    private val vertical: Vec3 = v * viewportHeight * focusDist

    private val lowerLeftCorner = origin -
            horizontal / 2.0f -
            vertical / 2.0f -
            w * focusDist

    private val lensRadius = aperture / 2f

    fun getRay(s: Float, t: Float): Ray {
        val rd = randomInUnitDisk() * lensRadius
        val offset = u * rd.x + v * rd.y

        return Ray(
            origin + offset,
            lowerLeftCorner + horizontal * s + vertical * t - origin - offset
        )
    }
}