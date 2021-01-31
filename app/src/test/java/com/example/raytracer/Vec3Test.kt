package com.example.raytracer

import org.junit.Test

import org.junit.Assert.*
import kotlin.math.sqrt

private const val DELTA: Float = 1e-15f

class Vec3Test {

    @Test
    fun add() {
        val a = Vec3(1.0f, 1.0f, 1.0f)
        val b = Vec3(2.0f, 2.0f, 2.0f)
        val expected = Vec3(3.0f, 3.0f, 3.0f)

        assertEquals(expected, a + b)
        a += b
        assertEquals(expected, a)
    }

    @Test
    fun subtract() {
        val a = Vec3(1.0f, 1.0f, 1.0f)
        val b = Vec3(2.0f, 2.0f, 2.0f)
        val expected = Vec3(-1.0f, -1.0f, -1.0f)

        assertEquals(expected, a - b)
        a -= b
        assertEquals(expected, a)
    }

    @Test
    fun multiply() {
        val a = Vec3(3.0f, 5.0f, 6.0f)
        val b = Vec3(2.0f, 2.0f, 2.0f)
        val expected = Vec3(6.0f, 10.0f, 12.0f)

        assertEquals(expected, a * b, )
        assertEquals(expected, a * 2.0f, )
    }

    @Test
    fun divide() {
        val a = Vec3(10.0f, 6.0f, 2.0f)
        val expected = Vec3(5.0f, 3.0f, 1.0f)

        assertEquals(expected, a / 2.0f, )
    }

    @Test
    fun length() {
        val a = Vec3(2.0f, 4.0f, 4.0f)
        val expected = 6.0f

        assertEquals(expected, a.length(), DELTA)
    }

    @Test
    fun dot() {
        val a = Vec3(2.0f, 4.0f, 4.0f)
        val b = Vec3(3.0f, 4.0f, 5.0f)
        val expected = 42.0f

        assertEquals(expected, dot(a, b), DELTA)
    }

    @Test
    fun cross() {
        val a = Vec3(2.0f, 4.0f, 4.0f)
        val b = Vec3(3.0f, 4.0f, 5.0f)
        val expected = Vec3(4.0f, 2.0f, -4.0f)

        assertEquals(expected, cross(a, b))
    }

    @Test
    fun unit() {
        val a = Vec3(1.0f, 1.0f, 1.0f)
        val x: Float = 1.0f / sqrt(3.0f)
        val expected = Vec3(x, x, x)

        assertEquals(unitVector(a), expected)
    }
}