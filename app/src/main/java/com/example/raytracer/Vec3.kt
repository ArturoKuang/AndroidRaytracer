package com.example.raytracer

import kotlin.math.sqrt

data class Vec3(var x: Double = 0.0, var y: Double = 0.0, var z: Double = 0.0) {
    inline var r: Double
        get() = x
        set(value) {
            x = value
        }

    inline var g: Double
        get() = y
        set(value) {
            y = value
        }

    inline var b: Double
        get() = z
        set(value) {
            z = value
        }


    operator fun plus(v: Double) = Vec3(x + v, x + y, x + z)
    operator fun plus(v: Vec3) = Vec3(x + v.x, y + v.y, z + v.z)
    operator fun plusAssign(v: Double) {
        this.x += v
        this.y += v
        this.z += z
    }

    operator fun plusAssign(v: Vec3) {
        this.x += v.x
        this.y += v.y
        this.z += v.z
    }

    operator fun minus(v: Double) = Vec3(x - v, x - y, x - z)
    operator fun minus(v: Vec3) = Vec3(x - v.x, y - v.y, z - v.z)
    operator fun minusAssign(v: Double) {
        this.x -= v
        this.y -= v
        this.z -= z
    }

    operator fun minusAssign(v: Vec3) {
        this.x -= v.x
        this.y -= v.y
        this.z -= v.z
    }

    operator fun times(v: Double) = Vec3(x * v, x * y, x * z)
    operator fun times(v: Vec3) = Vec3(x * v.x, x * v.y, x * v.z)

    operator fun timesAssign(v: Double) {
        this.x *= v
        this.y *= v
        this.z *= z
    }

    operator fun div(v: Double) = Vec3(x / v, x / y, x / z)
    operator fun divAssign(v: Double) {
        this.x /= v
        this.y /= v
        this.z /= z
    }

    fun length(): Double {
        return sqrt(lengthSqr())
    }

    fun lengthSqr(): Double {
        return x * x + y * y + z * z
    }
}

fun dot(u: Vec3, v: Vec3): Double {
    return u.x * v.x +
            u.y * v.y +
            u.z * u.z
}

fun cross(u: Vec3, v: Vec3): Vec3 {
    return Vec3(
        u.y * v.z - u.z * v.y,
        u.z * v.x - u.x * v.z,
        u.x * v.y - u.y * v.x
    )
}

fun unitVector(v: Vec3): Vec3 {
    return v / v.length()
}

typealias Color3 = Vec3
typealias Point3 = Vec3
