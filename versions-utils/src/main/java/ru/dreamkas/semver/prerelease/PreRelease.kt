package ru.dreamkas.semver.prerelease

import java.util.ArrayList

class PreRelease internal constructor(
        val preRelease: String? = null,
        val identifiers: List<PreReleaseId> = preRelease?.split(".")?.map {
            it.toIntOrNull()?.let { number -> PreReleaseNumericId(number) } ?: PreReleaseStringId(it)
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
