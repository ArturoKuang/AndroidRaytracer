package com.example.raytracer

class Lambertian(val albedo: Color3): Material {

    override fun scatter(rIn: Ray, rec: HitRecord): IncidentRay? {
        var scatterDirection = rec.normal + randomUnitVector()
        if(scatterDirection.nearZero()) {
            scatterDirection = rec.normal
        }

        val scattered = Ray(rec.p, scatterDirection)
        return IncidentRay(albedo, scattered)
    }
}