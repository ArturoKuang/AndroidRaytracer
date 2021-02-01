package com.example.raytracer

class IncidentRay(val attenuation: Color3, val scattered: Ray)

interface Material {
    fun scatter(rIn: Ray, rec: HitRecord): IncidentRay?
}