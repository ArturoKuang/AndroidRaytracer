package com.example.raytracer

import kotlin.math.sqrt

class Sphere(val center: Point3, val radius: Float) : Hittable {

    override fun hit(r: Ray, tMin: Float, tMax: Float, rec: HitRecord): Boolean {
        val oc = r.origin - center
        val a = r.direction.lengthSqr()
        val halfB = dot(oc, r.direction)
        val c = oc.lengthSqr() - radius * radius
        val discriminant = halfB * halfB - a * c

        if (discriminant < 0)
            return false

        val sqrtd = sqrt(discriminant)

        var root = (-halfB - sqrtd) / a
        if (root < tMin || root > tMax) {
            root = (-halfB + sqrtd) / a
            if (root < tMin || root > tMax) {
                return false
            }
        }

        rec.t = root
        rec.p = r.at(rec.t)
        val outwardNormal: Vec3  = (rec.p - center) / radius
        rec.setFaceNormal(r, outwardNormal)
        return true
    }
}