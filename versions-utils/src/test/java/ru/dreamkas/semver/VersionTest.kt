package ru.dreamkas.semver

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class VersionTest {

    @Test
    @DisplayName("Prerelease version")
    fun equalsTest() {
        assertFalse(VersionBuilder.build("1.0.1").isPreRelease())
        assertFalse(VersionBuilder.build("1.0.1+develop6f3d8ae2").isPreRelease())
        assertTrue(VersionBuilder.build("1.0.1-rc.1").isPreRelease())
    }
}
