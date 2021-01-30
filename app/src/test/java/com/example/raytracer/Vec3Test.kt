package com.example.raytracer

import org.junit.Test

import org.junit.Assert.*

private const val DELTA: Double = 1e-15

class Vec3Test {

    @Test
    fun add() {
        val a = Vec3(1.0, 1.0, 1.0)
        val b = Vec3(2.0, 2.0, 2.0)
        val expected = Vec3(3.0, 3.0, 3.0)

        assertEquals(expected, a + b)
        a += b
        assertEquals(expected, a)
    }

    @Test
    fun subtract() {
        val a = Vec3(1.0, 1.0, 1.0)
        val b = Vec3(2.0, 2.0, 2.0)
        val expected = Vec3(-1.0, -1.0, -1.0)

        assertEquals(expected, a - b)
        a -= b
        assertEquals(expected, a)
    }

    @Test
    fun multiply() {
        val a = Vec3(3.0, 5.0, 6.0)
        val b = Vec3(2.0, 2.0, 2.0)
        val expected = Vec3(6.0, 10.0, 12.0)

        assertEquals(expected, a * b, )
        assertEquals(expected, a * 2.0, )
    }

    @Test
    fun divide() {
        val a = Vec3(10.0, 6.0, 2.0)
        val expected = Vec3(5.0, 3.0, 1.0)

        assertEquals(expected, a / 2.0, )
    }

    @Test
    fun length() {
        val a = Vec3(2.0, 4.0, 4.0)
        val expected = 6.0

        assertEquals(expected, a.length(), DELTA)
    }

    @Test
    fun dot() {
        val a = Vec3(2.0, 4.0, 4.0)
        val b = Vec3(3.0, 4.0, 5.0)
        val expected = 42.0

        assertEquals(expected, dot(a, b), DELTA)
    }

    @Test
    fun cross() {
        val a = Vec3(2.0, 4.0, 4.0)
        val b = Vec3(3.0, 4.0, 5.0)
        val expected = Vec3(4.0, 2.0, -4.0)

        assertEquals(expected, cross(a, b))
    }
}