package com.example.raytracer


class HittableList: Hittable {

    val objects =  mutableListOf<Hittable>()

    override fun hit(r: Ray, tMin: Float, tMax: Float): IsHit? {
        var tempHitRec: IsHit? = null
        var hitAnything = false
        var closesSoFar = tMax

        for(obj in objects) {
            val isHit = obj.hit(r, tMin, tMax)
            if(isHit.hitAnything) {
                tempHitRec = isHit
            }
        }

        return tempHitRec
    }
}