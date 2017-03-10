package ru.dreamkas.semver

import org.apache.commons.lang3.ArrayUtils
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.opentest4j.AssertionFailedError
import ru.dreamkas.semver.exceptions.VersionFormatException
import java.util.*

@DisplayName("SemVer 2.0.0 compliance testing")
internal class SemverTest {
    @Test
    @DisplayName("Versions by specification")
    fun comparingBySpecification() {
        comparing(
                VersionBuilder.build("1.0.0-alpha"),
                VersionBuilder.build("1.0.0-alpha.1"),
                VersionBuilder.build("1.0.0-alpha.beta"),
                VersionBuilder.build("1.0.0-beta"),
                VersionBuilder.build("1.0.0-beta.2"),
                VersionBuilder.build("1.0.0-beta.11"),
                VersionBuilder.build("1.0.0-rc.1"),
                VersionBuilder.build("1.0.0"),
                VersionBuilder.build("1.0.2"),
                VersionBuilder.build("1.0.5"),
                VersionBuilder.build("1.0.10")
        )
    }

    @Test
    @DisplayName("More versions by specification")
    fun otherVersionsComparing() {
        comparing(
                VersionBuilder.build("0.0.1"),
                VersionBuilder.build("0.0.2"),
                VersionBuilder.build("1.0.0-1.2"),
                VersionBuilder.build("1.0.0-alpha"),
                VersionBuilder.build("1.0.0-alpha.1"),
                VersionBuilder.build("1.0.0-alpha.2"),
                VersionBuilder.build("1.0.0-beta.1"),
                VersionBuilder.build("1.0.0-beta.02"),
                VersionBuilder.build("1.0.0-beta.02.alpha"),
                VersionBuilder.build("1.0.0-beta.09.alpha"),
                VersionBuilder.build("1.0.0-rc.1"),
                VersionBuilder.build("1.0.0-rc.2"),
                VersionBuilder.build("1.0.0"),
                VersionBuilder.build("1.0.1"),
                VersionBuilder.build("1.0.2"),
                VersionBuilder.build("1.1.0"),
                VersionBuilder.build("1.1.1"),
                VersionBuilder.build("1.1.10"),
                VersionBuilder.build("1.10.0"),
                VersionBuilder.build("1.10.1"),
                VersionBuilder.build("2.0.0-rc.1"),
                VersionBuilder.build("2.0.0-rc.2"),
                VersionBuilder.build("2.0.0")

        )
    }

    @Test
    @DisplayName("Major versions")
    fun majoreComparing() {
        comparing(
                VersionBuilder.build("1.11.0"),
                VersionBuilder.build("2.10.0"),
                VersionBuilder.build("3.9.0")
        )
    }

    @Test
    @DisplayName("Minor versions")
    fun minorComparing() {
        comparing(
                VersionBuilder.build("1.9.0"),
                VersionBuilder.build("1.10.0"),
                VersionBuilder.build("1.11.0")
        )
    }

    @Test
    @DisplayName("Patch versions")
    fun patchComparing() {
        comparing(
                VersionBuilder.build("1.9.0"),
                VersionBuilder.build("1.9.2"),
                VersionBuilder.build("1.9.50")
        )
    }

    @Test
    @DisplayName("Prerelease versions")
    fun preReleaseComparing1() {
        comparing(
                VersionBuilder.build("1.0.0-alpha"),
                VersionBuilder.build("1.0.0-alpha.1"),
                VersionBuilder.build("1.0.0-alpha.2"),
                VersionBuilder.build("1.0.0-beta"),
                VersionBuilder.build("1.0.0-beta.1"),
                VersionBuilder.build("1.0.0-beta.10"),
                VersionBuilder.build("1.0.0-rc.1"),
                VersionBuilder.build("1.0.0-rc.2"),
                VersionBuilder.build("1.0.0")
        )
    }

    @Test
    @DisplayName("Base versions")
    fun shuffleComparing() {
        comparing(
                VersionBuilder.build("1.9.0"),
                VersionBuilder.build("2.3.10"),
                VersionBuilder.build("3.1.0")
        )
    }

    @Test
    @DisplayName("Check metadata ignoring")
    fun buildSuffixTest() {
        comparing(
                VersionBuilder.build("1.0.0-Alpha+dasd"),
                VersionBuilder.build("1.0.0-alpha.1+123123"),
                VersionBuilder.build("1.0.0-alpha.2+asss"),
                VersionBuilder.build("1.0.0-BETA+q3wx"),
                VersionBuilder.build("1.0.0-beta.1+dfhhzxdf"),
                VersionBuilder.build("1.0.0-BeTa.11+scv-dsdf"),
                VersionBuilder.build("1.0.0-beta.22+as-gt3-yuecv"),
                VersionBuilder.build("1.0.0-rc.1+ff"),
                VersionBuilder.build("1.0.0-rc.2+123"),
                VersionBuilder.build("1.0.0+sa-Ssaf")
        )
    }

    @Test
    @DisplayName("Not correct versions")
    fun incorrectVersions() {
        incorrect("incorrect",
                "1.0",
                "1.0-alpha",
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
                "1.0.0-alpha+"
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
        val v1 = VersionBuilder.build(stringV1)
        val v2 = VersionBuilder.build(stringV2)
        Assertions.assertEquals(v1, v2)
        try {
            Assertions.assertEquals(0, v1.compareTo(v2))
            Assertions.assertEquals(0, v2.compareTo(v1))
        } catch (e: AssertionFailedError) {
            throw AssertionFailedError(v1.toString() + "!=" + v2, e)
        }

    }

    private fun incorrect(vararg versions: String) {
        Arrays.stream(versions).forEach { it ->
            try {
                Assertions.assertThrows<Throwable>(VersionFormatException::class.java) { VersionBuilder.build(it) }
            } catch (e: AssertionFailedError) {
                throw AssertionFailedError(it, e)
            }
        }
    }

    private fun comparing(vararg versions: Version) {
        for (i in 0..versions.size - 2) {
            Assertions.assertTrue(versions[i + 1] > versions[i], "Expected " + versions[i + 1] + " > " + versions[i])
        }
        ArrayUtils.reverse(versions)
        for (i in 0..versions.size - 2) {
            Assertions.assertTrue(versions[i + 1] < versions[i], "Expected " + versions[i + 1] + " < " + versions[i])
        }
    }
}