package com.example.raytracer

data class HitRecord(
    var p: Point3,
    var normal: Vec3,
    var t: Float,
    var frontFace: Boolean
) {
    fun setFaceNormal(r: Ray, outwardNormal: Vec3) {
        frontFace = dot(r.direction, outwardNormal) < 0
        normal = if(frontFace) {
            outwardNormal
        } else {
            outwardNormal*-1.0f
        }
    }
}

interface Hittable {
    fun hit(r: Ray, tMin: Float, tMax: Float, rec: HitRecord): Boolean
}