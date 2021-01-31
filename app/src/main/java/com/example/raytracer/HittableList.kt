package com.example.raytracer


class HittableList: Hittable {

    val objects =  mutableListOf<Hittable>()

    override fun hit(r: Ray, tMin: Float, tMax: Float): HitRecord? {
        var tempHitRec: HitRecord? = null
        var closesSoFar = tMax

        for(obj in objects) {
            val isHit = obj.hit(r, tMin, closesSoFar)
            if(isHit != null) {
                tempHitRec = isHit
                closesSoFar = tempHitRec.t
            }
        }

        return tempHitRec
    }
}