package ru.dreamkas.semver.prerelease

import org.apache.commons.lang3.StringUtils

class PreRelease internal constructor(
        val preRelease: String?,
        val identificators: List<PreReleaseId> = preRelease?.split(".")?.map {
            if (StringUtils.isNumeric(it)) PreReleaseNumericId(it.toLong()) else PreReleaseStringId(it)
        } ?: ArrayList<PreReleaseId>()) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as PreRelease

        if (identificators != other.identificators) return false

        return true
    }

    override fun hashCode(): Int {
        return identificators.hashCode()
    }
}
