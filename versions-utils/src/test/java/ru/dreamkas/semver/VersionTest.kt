package ru.dreamkas.semver

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import java.util.stream.Stream

internal class VersionTest {


    @Test
    @DisplayName("Short versions")
    fun shortVersion() {
        assertEquals(Version.of("1.0.0"), Version.of("1"))
        assertEquals(Version.of("1.0.0"), Version.of("1.0"))
        assertEquals(Version.of("1.2.0"), Version.of("1.2"))
        assertEquals(Version.of("1.0.0-alpha"), Version.of("1.0-alpha"))
    }

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
    fun baseVersionTest(): Stream<DynamicTest> = Stream.of(
            DynamicTest.dynamicTest("Разные версии") {
                assertEquals(Version.of("1.0.0"), Version.of("1.0.0-beta.1").base)
            },
            DynamicTest.dynamicTest("Одинаковые версии") {
                assertEquals(Version.of("1.0.0"), Version.of("1.0.0").base)
            }
    )

    @TestFactory
    @DisplayName("Comparable version")
    fun comparableVersionTest(): Stream<DynamicTest> = Stream.of(
            DynamicTest.dynamicTest("Разные версии") {
                assertEquals(Version.of("1.0.0-beta.1"), Version.of("1.0.0-beta.1+develop").comparable)
            },
            DynamicTest.dynamicTest("Одинаковые версии") {
                assertEquals(Version.of("1.0.0-beta.1"), Version.of("1.0.0-beta.1").comparable)
            }

    )

    @Test
    @DisplayName("Full version as toString")
    fun fullVersionTest() {
        val preRelease = Version.of("1.0.0-beta.1")
        val expected = "1.0.0-beta.1"

        assertEquals(expected, preRelease.full)
        assertEquals(expected, preRelease.toString())
    }

}
