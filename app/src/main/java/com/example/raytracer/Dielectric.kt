package com.example.raytracer

import kotlin.math.sqrt
import kotlin.math.min
import kotlin.math.pow
import kotlin.random.Random

class Dielectric(private val indexOfRefraction: Float) : Material {
    override fun scatter(rIn: Ray, rec: HitRecord): IncidentRay? {
        val attenuation = Color3(1f, 1f, 1f)
        val refractionRatio = if (rec.frontFace) {
            1.0f / indexOfRefraction
        } else {
            indexOfRefraction
        }

        val unitDirection = unitVector(rIn.direction)
        val cosTheta: Float = min(dot(unitDirection * -1f, rec.normal), 1f)
        val sinTheta = sqrt(1f - cosTheta * cosTheta)

        val canNotRefract = refractionRatio * sinTheta > 1f

        var direction = refract(unitDirection, rec.normal, refractionRatio)
        if(canNotRefract || reflectance(cosTheta, refractionRatio) > Random.nextFloat()) {
            direction = reflect(unitDirection, rec.normal)
        }

        return IncidentRay(attenuation, Ray(rec.p, direction))
    }

    private fun reflectance(cos: Float, refractionIndex: Float): Float {
        var r0 = (1f-refractionIndex) / (1f+refractionIndex)
        r0 *= r0
        return r0 + (1f-r0)*(1-cos).pow(5)
    }
}