package ru.dreamkas.semver

import ru.dreamkas.semver.exceptions.VersionFormatException
import ru.dreamkas.semver.prerelease.PreRelease
import java.util.regex.Matcher
import java.util.regex.Pattern

object VersionBuilder {
    private val full = "full"
    private val base = "base"
    private val comparable = "comparable"
    private val major = "major"
    private val minor = "minor"
    private val patch = "patch"
    private val preRelease = "preRelease"
    private val metaData = "metaData"

    private val PATTERN = Pattern.compile(
    """
        (?<$full>
            (?<$comparable>
                (?<$base>
                    (?<$major>0|[1-9]+0*)\.
                    (?<$minor>0|[1-9]+0*)\.
                    (?<$patch>0|[1-9]+0*)
                )
                (?:-(?<$preRelease>[\da-z\-]+(?:\.[\da-z\-]+)*))?
            )
            (?:\+(?<$metaData>[\da-z\-\.]+))?
        )

    """.trimIndent(), Pattern.CASE_INSENSITIVE or Pattern.COMMENTS)


    /**
     * # SemVer template:
     * ## @param [version]: `<major>.<minor>.<patch>[-preRelease][+metaData]`
     * * **_major_ ::** `0|[1-9]+0*`
     * * **_minor_ ::** `0|[1-9]+0*`
     * * **_patch_ ::** `0|[1-9]+0*`
     * * **_preRelease_ ::** `[id](\.[id])*`
     * * **_id_ ::** `[0-9a-zA-Z\-]+`
     * * **_metaData_ ::** `[0-9a-zA-Z\-]+`
     * # [SemVer Details](http://semver.org/spec/v2.0.0.html)
     * @exception VersionFormatException when version isn't correct
     */
    @JvmStatic
    fun build(version: String): Version {
        val matches = PATTERN.matcher(version)
        if (matches.matches()) {
            try {
                return InternalVersion(matches)
            } catch (e: NumberFormatException) {
                throw VersionFormatException(version, e)
            }
        } else {
            throw VersionFormatException(version)
        }
    }

    private class InternalVersion(matches: Matcher) : Version(
            matches.group(full),
            matches.group(base),
            matches.group(comparable),
            matches.group(major).toLong(), matches.group(minor).toLong(),
            matches.group(patch).toLong(),
            PreRelease(matches.group(preRelease)),
            matches.group(metaData)?.split(".") ?: ArrayList<String>()
    )
}

