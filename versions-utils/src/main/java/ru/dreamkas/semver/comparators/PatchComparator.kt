package ru.dreamkas.semver.comparators

import ru.dreamkas.semver.Version
import java.io.Serializable
import java.util.Comparator

/**
 * Comparing for [Version.patch]
 */
internal class PatchComparator : Comparator<Version>, Serializable {
    override fun compare(o1: Version, o2: Version): Int {
        return o1.patch.compareTo(o2.patch)
    }

}
