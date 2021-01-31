package com.example.raytracer

data class HitRecord(
    var p: Point3,
    var normal: Vec3,
    var t: Float
)

interface Hittable {
    fun hit(r: Ray, tMin: Float, tMax: Float, rec: HitRecord): Boolean
}