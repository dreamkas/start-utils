package ru.dreamkas.semver

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.opentest4j.AssertionFailedError
import ru.dreamkas.semver.VersionBuilder.build
import ru.dreamkas.semver.exceptions.VersionFormatException
import java.util.Arrays

@DisplayName("SemVer 2.0.0 compliance testing")
internal class SemverTest {
    @Test
    @DisplayName("Versions by specification")
    fun comparingBySpecification() {
        comparing(
                build("1.0.0-alpha"),
                build("1.0.0-alpha.1"),
                build("1.0.0-alpha.beta"),
                build("1.0.0-beta"),
                build("1.0.0-beta.2"),
                build("1.0.0-beta.11"),
                build("1.0.0-rc.1"),
                build("1.0.0"),
                build("1.0.2"),
                build("1.0.5"),
                build("1.0.10")
        )
    }

    @Test
    @DisplayName("More versions by specification")
    fun otherVersionsComparing() {
        comparing(
                build("0.0.1"),
                build("0.0.2"),
                build("1.0.0-1.2"),
                build("1.0.0-alpha"),
                build("1.0.0-alpha.1"),
                build("1.0.0-alpha.2"),
                build("1.0.0-beta.1"),
                build("1.0.0-beta.02"),
                build("1.0.0-beta.02.alpha"),
                build("1.0.0-beta.09.alpha"),
                build("1.0.0-rc.1"),
                build("1.0.0-rc.2"),
                build("1.0.0"),
                build("1.0.1"),
                build("1.0.2"),
                build("1.1.0"),
                build("1.1.1"),
                build("1.1.10"),
                build("1.10.0"),
                build("1.10.1"),
                build("2.0.0-rc.1"),
                build("2.0.0-rc.2"),
                build("2.0.0")

        )
    }

    @Test
    @DisplayName("Builders testing")
    fun buildersTesting() {
        assertEquals(build("1.0.0"), build(major = 1), "Check equality of versions built in different ways")
        assertEquals(build("1.0.0-beta.22+as-gt3-yuecv"), build(1, 0, 0, "beta.22", "as-gt3-yuecv"), "Check equality of versions built in different ways")
        assertEquals(build("1.0.0-beta.22+as-gt3-yuecv"), build("1.0.0-beta.22", "as-gt3-yuecv"), "Check equality of versions built in different ways")
        assertEquals(build(1, 0, 0, "beta.22", "as-gt3-yuecv"), build("1.0.0-beta.22", "as-gt3-yuecv"), "Check equality of versions built in different ways")
    }

    @Test
    @DisplayName("Major versions")
    fun majorComparing() {
        comparing(
                build("1.11.0"),
                build("2.10.0"),
                build("3.9.0")
        )
    }

    @Test
    @DisplayName("Minor versions")
    fun minorComparing() {
        comparing(
                build("1.9.0"),
                build("1.10.0"),
                build("1.11.0")
        )
    }

    @Test
    @DisplayName("Patch versions")
    fun patchComparing() {
        comparing(
                build("1.9.0"),
                build("1.9.2"),
                build("1.9.50")
        )
    }

    @Test
    @DisplayName("Prerelease versions")
    fun preReleaseComparing1() {
        comparing(
                build("1.0.0-alpha"),
                build("1.0.0-alpha.1"),
                build("1.0.0-alpha.2"),
                build("1.0.0-beta"),
                build("1.0.0-beta.1"),
                build("1.0.0-beta.10"),
                build("1.0.0-rc.1"),
                build("1.0.0-rc.2"),
                build("1.0.0")
        )
    }

    @Test
    @DisplayName("Base versions")
    fun shuffleComparing() {
        comparing(
                build("1.9.0"),
                build("2.3.10"),
                build("3.1.0")
        )
    }

    @Test
    @DisplayName("Check metadata ignoring")
    fun buildSuffixTest() {
        comparing(
                build("1.0.0-Alpha+dasd"),
                build("1.0.0-alpha.1+123123"),
                build("1.0.0-alpha.2+asss"),
                build("1.0.0-BETA+q3wx"),
                build("1.0.0-beta.1+dfhhzxdf"),
                build("1.0.0-BeTa.11+scv-dsdf"),
                build("1.0.0-beta.22+as-gt3-yuecv"),
                build("1.0.0-rc.1+ff"),
                build("1.0.0-rc.2+123"),
                build("1.0.0+sa-Ssaf")
        )
    }

    @Test
    @DisplayName("Not correct versions")
    fun incorrectVersions() {
        incorrect("incorrect",
                "10.3.8.55",
                "1.0.99999999999999999999999999999999999",
                "01.0.0",
                "1.0.00",
                "1.0.0-АЛЬФА.1",
                "1.0.0-alpha.1+Test\ntest",
                "1.0.0-alpha.1+Test+test",
                "1.0.0-alpha.1+Test_test",
                "1.0.0-alpha.1+Test test",
                "1.0.0-",
                "1.0.0+",
                "1.0.0-alpha+",
                "1.0.0-pre_release_id_with_underscores.1",
                "1.0.0+meta_with_underscores"
        )
    }

    @Test
    @DisplayName("Version equals checking")
    fun equalsTest() {
        eq("1.0.0-alpha+dasd", "1.0.0-alpha+123123")
        eq("1.0.0-alpha.2+asss", "1.0.0-alpha.2+q3wx")
        eq("1.0.0-beta.1+dfhhzxdf", "1.0.0-beta.1+asgt3yuecv")
        eq("1.0.0-beta.11+scv-dsdf", "1.0.0-BETA.11+ffF")
        eq("1.0.0-r-c.2+123", "1.0.0-r-c.2+sassaf")
        eq("1.0.0+123", "1.0.0+000")
        eq("1.0.0-alpha.01.beta.1+asss", "1.0.0-alpha.1.beta.001+q3wx")
    }

    private fun eq(stringV1: String, stringV2: String) {
        val v1 = build(stringV1)
        val v2 = build(stringV2)
        assertEquals(v1, v2)
        try {
            assertEquals(0, v1.compareTo(v2))
            assertEquals(0, v2.compareTo(v1))
        } catch (e: AssertionFailedError) {
            throw AssertionFailedError(v1.toString() + "!=" + v2, e)
        }

    }

    private fun incorrect(vararg versions: String) {
        Arrays.stream(versions).forEach { it ->
            try {
                assertThrows<VersionFormatException>(VersionFormatException::class.java) { build(it) }
            } catch (e: AssertionFailedError) {
                throw AssertionFailedError(it, e)
            }
        }
    }

    private fun comparing(vararg versions: Version) {
        for (i in 0..versions.size - 2) {
            assertTrue(versions[i + 1] > versions[i], "Expected " + versions[i + 1] + " > " + versions[i])
        }
        versions.reverse()
        for (i in 0..versions.size - 2) {
            assertTrue(versions[i + 1] < versions[i], "Expected " + versions[i + 1] + " < " + versions[i])
        }
    }
}