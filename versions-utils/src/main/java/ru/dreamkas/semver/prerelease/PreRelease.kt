package ru.dreamkas.semver.prerelease

import org.apache.commons.lang3.StringUtils
import java.util.*

class PreRelease internal constructor(
        val preRelease: String?,
        val identifiers: List<PreReleaseId> = preRelease?.split(".")?.map {
            if (StringUtils.isNumeric(it)) PreReleaseNumericId(it.toInt()) else PreReleaseStringId(it)
        } ?: ArrayList<PreReleaseId>()) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as PreRelease

        if (identifiers != other.identifiers) return false

        return true
    }

    override fun hashCode(): Int {
        return identifiers.hashCode()
    }

    override fun toString(): String {
        return preRelease ?: ""
    }
}
