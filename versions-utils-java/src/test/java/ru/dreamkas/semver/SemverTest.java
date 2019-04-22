package ru.dreamkas.semver;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.dreamkas.semver.VersionBuilder.build;

@DisplayName("SemVer 2.0.0 compliance testing")
public class SemverTest {
    @Test
    @DisplayName("Versions by specification")
    public void comparingBySpecification() {
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
        );
    }

    @Test
    @DisplayName("More versions by specification")
    public void otherVersionsComparing() {
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
        );
    }

    @Test
    @DisplayName("Builders testing")
    public void buildersTesting() {
        assertEquals(build("1.0.0"), build(1), "Check equality of versions built in different ways");
        assertEquals(build("1.0.0-beta.22+as-gt3-yuecv"), build(1, 0, 0, "beta.22", "as-gt3-yuecv"), "Check equality of versions built in different ways");
        assertEquals(build("1.0.0-beta.22+as-gt3-yuecv"), build("1.0.0-beta.22", "as-gt3-yuecv"), "Check equality of versions built in different ways");
        assertEquals(build(1, 0, 0, "beta.22", "as-gt3-yuecv"), build("1.0.0-beta.22", "as-gt3-yuecv"), "Check equality of versions built in different ways");
    }

    @Test
    @DisplayName("Major versions")
    public void majorComparing() {
        comparing(
            build("1.11.0"),
            build("2.10.0"),
            build("3.9.0")
        );
    }

    @Test
    @DisplayName("Minor versions")
    public void minorComparing() {
        comparing(
            build("1.9.0"),
            build("1.10.0"),
            build("1.11.0")
        );
    }

    @Test
    @DisplayName("Patch versions")
    public void patchComparing() {
        comparing(
            build("1.9.0"),
            build("1.9.2"),
            build("1.9.50")
        );
    }

    @Test
    @DisplayName("Prerelease versions")
    public void preReleaseComparing1() {
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
        );
    }

    @Test
    @DisplayName("Base versions")
    public void shuffleComparing() {
        comparing(
            build("1.9.0"),
            build("2.3.10"),
            build("3.1.0")
        );
    }

    @Test
    @DisplayName("Check metadata ignoring")
    public void buildSuffixTest() {
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
        );
    }

    @Test
    @DisplayName("Not correct versions")
    public void incorrectVersions() {
        incorrect("incorrect",
            "10.3.8.55",
            "1.0.99999999999999999999999999999999999",
            "01.0.0",
            //"1.0.00",
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
        );
        ;
    }

    @Test
    @DisplayName("Version equals checking")
    public void equalsTest() {
        eq("1.0.0-alpha+dasd", "1.0.0-alpha+123123");
        eq("1.0.0-alpha.2+asss", "1.0.0-alpha.2+q3wx");
        eq("1.0.0-beta.1+dfhhzxdf", "1.0.0-beta.1+asgt3yuecv");
        eq("1.0.0-beta.11+scv-dsdf", "1.0.0-BETA.11+ffF");
        eq("1.0.0-r-c.2+123", "1.0.0-r-c.2+sassaf");
        eq("1.0.0+123", "1.0.0+000");
        eq("1.0.0-alpha.01.beta.1+asss", "1.0.0-alpha.1.beta.001+q3wx");
    }

    public void eq(String stringV1, String stringV2) {
        Version v1 = build(stringV1);
        Version v2 = build(stringV2);
        assertEquals(v1, v2);
        try {
            assertEquals(0, v1.compareTo(v2));
            assertEquals(0, v2.compareTo(v1));
        } catch (AssertionFailedError e) {
            throw new AssertionFailedError(v1.toString() + "!=" + v2, e);
        }
    }

    public void incorrect(String... versions) {
        Arrays.stream(versions).forEach(v -> assertThrows(VersionFormatException.class, () -> build(v)));
    }

    public void comparing(Version... versions) {
        for (int i = 0; i <= versions.length - 2; i++) {
            assertTrue(versions[i + 1].gt(versions[i]), "Expected " + versions[i + 1] + " > " + versions[i]);
        }
        List<Version> versionsList = Arrays.asList(versions);
        Collections.reverse(versionsList);
        for (int i = 0; i <= versionsList.size() - 2; i++) {
            assertTrue(versionsList.get(i + 1).lt(versionsList.get(i)), "Expected " + versionsList.get(i + 1) + " < " + versionsList.get(i));
        }
    }
}
