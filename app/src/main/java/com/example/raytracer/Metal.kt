package com.example.raytracer

import kotlin.math.pow

class Metal(val albedo: Color3, var fuzz: Float): Material {

    init {
        if(fuzz < 1f)
            fuzz = 1f
    }

    override fun scatter(rIn: Ray, rec: HitRecord): IncidentRay? {
        val reflected = reflect(unitVector(rIn.direction), rec.normal)
        val scattered = Ray(rec.p, reflected + randomInUnitSphere()*fuzz)

        if(dot(scattered.direction, rec.normal) > 0) {
            return IncidentRay(albedo, scattered)
        }

        return null
    }
}