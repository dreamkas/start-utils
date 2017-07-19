package ru.dreamkas.semver

import ru.dreamkas.semver.comparators.VersionComparator
import ru.dreamkas.semver.prerelease.PreRelease

/**
 * Version object corresponding to [Semantic Versioning 2.0.0 Specification](http://semver.org/spec/v2.0.0.html)
 *
 * Field examples for 1.5.8-beta.22+revision.2ed49def
 * Use [VersionBuilder.build] for instance
 * * [full] 1.5.8-beta.22+revision.2ed49def
 * * [base] 1.5.8
 * * [comparable] 1.5.8-beta.22
 * * [major] 1
 * * [minor] 5
 * * [patch] 8
 * * [preRelease] beta.22
 * * [metaData] revision.2ed49def
 */
class Version internal constructor(
        val major: Int,
        val minor: Int = 0,
        val patch: Int = 0,
        val full: String = "$major.$minor.$patch",
        base: String = full,
        comparable: String = base,
        val preRelease: PreRelease = PreRelease(),
        val metaData: MetaData = MetaData()
) : Comparable<Version> {

    val base = if (base == full) this else this.truncateToPatch()

    val comparable = if (comparable == full) {
        this
    } else {
        Version(major, minor, patch, comparable, this.base.toString(), comparable, preRelease)
    }

    fun isPreRelease() = preRelease.preRelease != null
    fun isRelease() = !isPreRelease()
    override fun compareTo(other: Version): Int {
        return VersionComparator.SEMVER.compare(this, other)
    }

    fun truncateToMajor(): Version = Version(major)
    fun truncateToMinor(): Version = Version(major, minor)
    fun truncateToPatch(): Version = Version(major, minor, patch)
    fun truncateToPreRelease(): Version = Version(major, minor, patch, comparable.toString(), comparable.base.toString(), comparable.toString(),
            preRelease)
    fun gt(other: Version): Boolean = this > other
    fun lt(other: Version): Boolean = this < other
    fun le(other: Version): Boolean = this <= other
    fun ge(other: Version): Boolean = this >= other
    override fun toString(): String {
        return full
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Version

        if (major != other.major) return false
        if (minor != other.minor) return false
        if (patch != other.patch) return false
        if (preRelease != other.preRelease) return false

        return true
    }

    override fun hashCode(): Int {
        var result = major.hashCode()
        result = 31 * result + minor.hashCode()
        result = 31 * result + patch.hashCode()
        result = 31 * result + preRelease.hashCode()
        return result
    }

    companion object {
        @JvmStatic
        fun of(version: String): Version {
            return VersionBuilder.build(version)
        }

        @JvmStatic
        fun matches(version: String): Boolean {
            return VersionBuilder.matches(version)
        }

        @JvmStatic
        @JvmOverloads
        fun of(major: Int, minor: Int = 0, patch: Int = 0): Version {
            return Version(major, minor, patch);
        }
    }
}

