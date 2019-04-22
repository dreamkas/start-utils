package ru.dreamkas.semver;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VersionTest {

    @Test
    @DisplayName("Short versions")
    public void shortVersion() {
        assertEquals(Version.of("1.0.0"), Version.of("1"));
        assertEquals(Version.of("1.0.0"), Version.of("1.0"));
        assertEquals(Version.of("1.2.0"), Version.of("1.2"));
        assertEquals(Version.of("1.0.0-alpha"), Version.of("1.0-alpha"));
        assertEquals(Version.of("1.1.0-alpha"), Version.of("1.1-ALPhA"));

        assertEquals(Version.of("9.1.2"), Version.of(9, 1, 2));
        assertEquals(Version.of("9.1"), Version.of(9, 1));
        assertEquals(Version.of("9"), Version.of(9));

        assertEquals(Version.of("3.0.2070"), Version.of(3,0,2070));
    }

    @Test
    @DisplayName("Short int versions")
    public void shortIntVersion() {
        assertEquals(Version.of("9.1.2"), Version.of(9, 1, 2));
        assertEquals(Version.of("19.1.0"), Version.of(19, 1));
        assertEquals(Version.of("9.0.0"), Version.of(9));
    }

    @Test
    @DisplayName("PreRelease version")
    public void preReleaseTest() {
        assertFalse(Version.of("1.0.1").isPreRelease());
        assertFalse(Version.of("1.0.1+develop6f3d8ae2").isPreRelease());
        assertTrue(Version.of("1.0.1-rc.1").isPreRelease());
    }

    @Test
    @DisplayName("Release version")
    public void releaseTest() {
        assertTrue(Version.of("1.0.1").isRelease());
        assertTrue(Version.of("1.0.1+develop6f3d8ae2").isRelease());
        assertFalse(Version.of("1.0.1-rc.1").isRelease());
    }

    @Test
    @DisplayName("Base version")
    public Stream<DynamicTest> baseVersionTest() {
        return Stream.of(
            DynamicTest.dynamicTest("Разные версии", () -> assertEquals(Version.of("1.0.0"), Version.of("1.0.0-beta.1").getBase())),
            DynamicTest.dynamicTest("Одинаковые версии", () -> assertEquals(Version.of("1.0.0"), Version.of("1.0.0").getBase()))
        );
    }

    @TestFactory
    @DisplayName("Comparable version")
    public Stream<DynamicTest> comparableVersionTest() {
        return Stream.of(
            DynamicTest.dynamicTest("Разные версии", () -> assertEquals(Version.of("1.0.0-beta.1"), Version.of("1.0.0-beta.1+develop").getComparable())),
            DynamicTest.dynamicTest("Одинаковые версии", () -> assertEquals(Version.of("1.0.0-beta.1"), Version.of("1.0.0-BETA.1").getComparable()))
        );
    }

    @Test
    @DisplayName("toString() returns full version")
    public void toStringTest() {
        assertEquals("1.0.0", Version.of(1).toString());
        assertEquals("1.2.0", Version.of("1.2").toString());
        assertEquals("1.0.0-beta.2", Version.of("1.0.0-beta.2").toString());
        assertEquals("1.0.0+meta.5", Version.of("1.0.0+meta.5").toString());
        assertEquals("1.0.0-beta.1+meta.5", Version.of("1.0.0-beta.1+meta.5").toString());
    }

    @Test
    @DisplayName("toComparableString returns comparable version")
    public void toComparableStringTest() {
        assertEquals("1.0.0", Version.of(1).toComparableString());
        assertEquals("1.2.0", Version.of("1.2").toComparableString());
        assertEquals("1.0.0-beta.2", Version.of("1.0.0-beta.2").toComparableString());
        assertEquals("1.0.0", Version.of("1.0.0+meta.5").toComparableString());
        assertEquals("1.0.0-beta.1", Version.of("1.0.0-beta.1+meta.5").toComparableString());
    }

    @Test
    public void truncateTest() {
        Version version = Version.of("9.5.4-beta.1+meta");
        assertEquals(Version.of("9.0.0"), version.truncateToMajor());
        assertEquals(Version.of("9.5.0"), version.truncateToMinor());
        assertEquals(Version.of("9.5.4"), version.truncateToPatch());
        assertEquals(Version.of("9.5.4-beta.1"), version.truncateToPreRelease());
    }

    @Test
    public void shortNamesCompareTest() {
        assertTrue(Version.of("9.0.0").gt(Version.of("8.0.0")));

        assertTrue(Version.of("9.0.0").ge(Version.of("8.0.0")));
        assertTrue(Version.of("9.0.0").ge(Version.of("9.0.0")));

        assertTrue(Version.of("9.0.0").lt(Version.of("10.0.0")));

        assertTrue(Version.of("9.0.0").le(Version.of("10.0.0")));
        assertTrue(Version.of("9.0.0").le(Version.of("9.0.0")));
    }

    @Test
    public void versionMatchesTest() {
        assertTrue(Version.matches("9.0.0"));
        assertTrue(Version.matches("9.5.4-beta.1"));
        assertTrue(Version.matches("9.5.4-beta.1+meta"));
        assertFalse(Version.matches("9.5.4.2"));
    }

    @Test
    public void hashCodeTest() {
        assertNotEquals(Version.of(9).hashCode(), Version.of("9.0.0-beta.1").hashCode(), "Hashcode for different prerelease");
        assertEquals(Version.of(9).hashCode(), Version.of("9.0.0").hashCode(), "Hashcode for same prerelease");
    }
}
