package com.example.raytracer

import kotlin.math.max
import kotlin.math.min

const val infinity = Float.POSITIVE_INFINITY
const val pi = 3.1415926535897932385f

fun degreesToRadians(degrees: Float): Float {
    return degrees * pi / 180.0f
}

fun clamp(x: Float, min: Float, max: Float): Float {
    return min(max(x, min), max)
}