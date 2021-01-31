package com.example.raytracer

import kotlin.math.sqrt

data class Vec3(var x: Float = 0.0f, var y: Float = 0.0f, var z: Float = 0.0f) {
    inline var r: Float
        get() = x
        set(value) {
            x = value
        }

    inline var g: Float
        get() = y
        set(value) {
            y = value
        }

    inline var b: Float
        get() = z
        set(value) {
            z = value
        }


    operator fun plus(v: Float) = Vec3(x + v, x + y, x + z)
    operator fun plus(v: Vec3) = Vec3(x + v.x, y + v.y, z + v.z)
    operator fun plusAssign(v: Float) {
        this.x += v
        this.y += v
        this.z += z
    }

    operator fun plusAssign(v: Vec3) {
        this.x += v.x
        this.y += v.y
        this.z += v.z
    }

    operator fun minus(v: Float) = Vec3(x - v, x - y, x - z)
    operator fun minus(v: Vec3) = Vec3(x - v.x, y - v.y, z - v.z)
    operator fun minusAssign(v: Float) {
        this.x -= v
        this.y -= v
        this.z -= z
    }

    operator fun minusAssign(v: Vec3) {
        this.x -= v.x
        this.y -= v.y
        this.z -= v.z
    }

    operator fun times(v: Float) = Vec3(x * v, y * v, z * v)
    operator fun times(v: Vec3) = Vec3(x * v.x, y * v.y, z * v.z)

    operator fun timesAssign(v: Float) {
        this.x *= v
        this.y *= v
        this.z *= v
    }

    operator fun div(v: Float) = Vec3(x / v, y / v, z / v)
    operator fun divAssign(v: Float) {
        this.x /= v
        this.y /= v
        this.z /= v
    }

    fun length(): Float {
        return sqrt(lengthSqr())
    }

    fun lengthSqr(): Float {
        return x * x + y * y + z * z
    }
}

fun dot(u: Vec3, v: Vec3): Float {
    return u.x * v.x +
            u.y * v.y +
            u.z * v.z
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
