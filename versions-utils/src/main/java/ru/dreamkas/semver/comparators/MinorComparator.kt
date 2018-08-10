package ru.dreamkas.semver.comparators

import ru.dreamkas.semver.Version
import java.io.Serializable
import java.util.Comparator

/**
 * Comparing for [Version.minor]
 */
internal class MinorComparator : Comparator<Version>, Serializable {
    override fun compare(o1: Version, o2: Version): Int {
        return o1.minor.compareTo(o2.minor)
    }
}
