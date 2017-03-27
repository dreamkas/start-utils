package ru.dreamkas.semver

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class VersionTest {

    @Test
    @DisplayName("PreRelease version")
    fun preReleaseTest() {
        assertFalse(Version.of("1.0.1").isPreRelease())
        assertFalse(Version.of("1.0.1+develop6f3d8ae2").isPreRelease())
        assertTrue(Version.of("1.0.1-rc.1").isPreRelease())
    }

    @Test
    @DisplayName("Release version")
    fun releaseTest() {
        assertTrue(Version.of("1.0.1").isRelease())
        assertTrue(Version.of("1.0.1+develop6f3d8ae2").isRelease())
        assertFalse(Version.of("1.0.1-rc.1").isRelease())
    }

    @Test
    @DisplayName("Base version")
    fun baseVersionTest() {
        val preRelease = Version.of("1.0.0-beta.1")
        val expected = Version.of("1.0.0")

        assertEquals(expected, preRelease.base)
    }

    @Test
    @DisplayName("Comparable version")
    fun comparableVersionTest() {
        val preRelease = Version.of("1.0.0-beta.1+develop")
        val expected = Version.of("1.0.0-beta.1")

        assertEquals(expected, preRelease.comparable)
    }

    @Test
    @DisplayName("Full version as toString")
    fun fullVersionTest() {
        val preRelease = Version.of("1.0.0-beta.1")
        val expected = "1.0.0-beta.1"

        assertEquals(expected, preRelease.full)
        assertEquals(expected, preRelease.toString())
    }

}
