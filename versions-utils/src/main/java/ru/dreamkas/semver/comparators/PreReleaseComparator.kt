package ru.dreamkas.semver.comparators

import ru.dreamkas.semver.Version
import java.io.Serializable
import java.util.*

/**
 * Comparing for [Version.preRelease]
 */
internal class PreReleaseComparator : Comparator<Version>, Serializable {
    override fun compare(o1: Version, o2: Version): Int {
        val first = o1.preRelease.identifiers
        val second = o2.preRelease.identifiers
        val firstSize = first.size
        val secondSize = second.size
        return if (firstSize == secondSize && firstSize == 0) 0
        else if (firstSize == 0) 1
        else if (secondSize == 0) -1
        else {
            val maxId = maxOf(firstSize, secondSize) - 1
            for (i in 0..maxId) {
                val result = if (firstSize <= i) -1
                else if (secondSize <= i) 1
                else first.get(i).compareTo(second.get(i))
                if (result != 0)
                    return result
            }
            return 0
        }

    }

}
