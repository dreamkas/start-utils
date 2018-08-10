package ru.dreamkas.semver;

import org.jetbrains.annotations.NotNull;

import ru.dreamkas.semver.prerelease.PreRelease;

public final class Version implements Comparable<Version> {
    private int major;
    private int minor;
    private int patch;
    private PreRelease preRelease = PreRelease.EMPTY;
    private MetaData metaData = MetaData.EMPTY;

    public final Version getBase() {
        return truncateToPatch();
    }

    public final Version getComparable() {
        return truncateToPreRelease();
    }

    public final boolean isPreRelease() {
        return !isRelease();
    }

    public final boolean isRelease() {
        return preRelease.getIdentifiers().isEmpty();
    }

    public int compareTo(@NotNull Version other) {
        return VersionComparator.SEMVER.compare(this, other);
    }

    public final Version truncateToMajor() {
        return new Version(major, 0, 0);
    }

    public final Version truncateToMinor() {
        return new Version(major, minor, 0);
    }

    public final Version truncateToPatch() {
        return new Version(major, minor, patch);
    }

    public final Version truncateToPreRelease() {
        return new Version(major, minor, patch, preRelease, MetaData.EMPTY);
    }

    public final boolean gt(@NotNull Version other) {
        return compareTo(other) > 0;
    }

    public final boolean lt(@NotNull Version other) {
        return compareTo(other) < 0;
    }

    public final boolean le(@NotNull Version other) {
        return compareTo(other) <= 0;
    }

    public final boolean ge(@NotNull Version other) {
        return compareTo(other) >= 0;
    }

    public final String toComparableString() {
        return major + "." + minor + "." + patch + preRelease.toString();
    }

    public String toString() {
        return toComparableString() + metaData.toString();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof Version)) {
            return false;
        }
        Version otherVersion = (Version) other;
        if (major != otherVersion.major) {
            return false;
        } else if (minor != otherVersion.minor) {
            return false;
        } else if (patch != otherVersion.patch) {
            return false;
        }
        return preRelease.equals(otherVersion.preRelease);
    }

    public int hashCode() {
        int result = major;
        result = 31 * result + minor;
        result = 31 * result + patch;
        result = 31 * result + preRelease.hashCode();
        return result;
    }

    public final int getMajor() {
        return major;
    }

    public final int getMinor() {
        return minor;
    }

    public final int getPatch() {
        return patch;
    }

    public final PreRelease getPreRelease() {
        return preRelease;
    }

    public final MetaData getMetaData() {
        return metaData;
    }

    public Version(int major, int minor, int patch) {
        this(major, minor, patch, PreRelease.EMPTY, MetaData.EMPTY);
    }

    public Version(int major, int minor, int patch, PreRelease preRelease, MetaData metaData) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.preRelease = preRelease;
        this.metaData = metaData;
    }

    public static Version of(@NotNull String version) {
        return VersionBuilder.build(version);
    }

    public static boolean matches(@NotNull String version) {
        return VersionBuilder.matches(version);
    }

    public static Version of(int major, int minor, int patch) {
        return new Version(major, minor, patch);
    }

    public static Version of(int major, int minor) {
        return new Version(major, minor, 0);
    }

    public static Version of(int major) {
        return new Version(major, 0, 0);
    }
}

