package ru.dreamkas.semver.prerelease

import org.apache.commons.lang3.StringUtils

class PreReleaseStringId(val id: String) : PreReleaseId {
    override fun compareTo(other: PreReleaseId): Int {
        return if (other is PreReleaseNumericId) 1
        else StringUtils.compareIgnoreCase(id, (other as PreReleaseStringId).id)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as PreReleaseStringId

        if (id.toLowerCase() != other.id.toLowerCase()) return false

        return true
    }

    override fun hashCode(): Int {
        return id.toLowerCase().hashCode()
    }

}