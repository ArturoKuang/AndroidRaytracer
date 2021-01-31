package com.example.raytracer

data class Ray(var origin: Vec3, var direction: Vec3) {

    fun at(t: Float): Point3 {
        return origin + direction*t
    }
}