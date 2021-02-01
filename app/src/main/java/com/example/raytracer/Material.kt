package com.example.raytracer

class IncidentRay(attenuation: Color3, scattered: Ray)

interface Material {
    fun scatter(rIn: Ray, rec: HitRecord): IncidentRay?
}