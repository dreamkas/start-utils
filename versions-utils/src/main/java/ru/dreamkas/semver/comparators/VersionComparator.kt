package ru.dreamkas.semver.comparators

import ru.dreamkas.semver.Version

class VersionComparator private constructor(
        private val delegate: Comparator<Version> = MajorComparator()
                .thenComparing(MinorComparator())
                .thenComparing(PatchComparator())
                .thenComparing(PreReleaseComparator())
) : Comparator<Version> by delegate {

    companion object {
        @JvmField
        val SEMVER: Comparator<Version> = VersionComparator()
    }

}
