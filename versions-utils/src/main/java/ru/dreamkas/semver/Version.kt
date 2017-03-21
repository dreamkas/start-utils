package ru.dreamkas.semver

import ru.dreamkas.semver.comparators.VersionComparator
import ru.dreamkas.semver.metadata.MetaData
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
open class Version protected constructor(
        open val full: String,
        open val base: String,
        open val comparable: String,
        open val major: Long,
        open val minor: Long,
        open val patch: Long,
        open val preRelease: PreRelease,
        open val metaData: MetaData
) : Comparable<Version> {

    fun isPreRelease() = preRelease.preRelease != null
    override fun compareTo(other: Version): Int {
        return VersionComparator.SEMVER.compare(this, other)
    }

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

}