package ru.dreamkas.semver.prerelease

class PreReleaseStringId(val id: String) : PreReleaseId {
    override fun compareTo(other: PreReleaseId): Int {
        return if (other is PreReleaseNumericId) 1
        else id.compareTo((other as PreReleaseStringId).id, ignoreCase = true)
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