package com.example.raytracer

class Metal(val albedo: Color3): Material {
    override fun scatter(rIn: Ray, rec: HitRecord): IncidentRay? {
        val reflected = reflect(unitVector(rIn.direction), rec.normal)
        val scattered = Ray(rec.p, reflected)

        if(dot(scattered.direction, rec.normal) > 0) {
            return IncidentRay(albedo, scattered)
        }

        return null
    }
}