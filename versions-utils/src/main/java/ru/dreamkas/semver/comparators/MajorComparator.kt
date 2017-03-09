package ru.dreamkas.semver.comparators

import ru.dreamkas.semver.Version
import java.io.Serializable
import java.util.*

/**
 * Comparing for [Version.major]
 */
internal class MajorComparator internal constructor() : Comparator<Version>, Serializable {
    override fun compare(o1: Version, o2: Version): Int {
        return o1.major.compareTo(o2.major)
    }
}
