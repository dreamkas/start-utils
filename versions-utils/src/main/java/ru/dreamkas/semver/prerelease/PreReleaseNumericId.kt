package ru.dreamkas.semver.prerelease

data class PreReleaseNumericId(val id: Int) : PreReleaseId {
    override fun compareTo(other: PreReleaseId): Int {
        return if (other is PreReleaseStringId) -1
        else id.compareTo((other as PreReleaseNumericId).id)
    }
}