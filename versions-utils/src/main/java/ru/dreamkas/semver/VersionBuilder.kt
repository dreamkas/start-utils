package ru.dreamkas.semver

import ru.dreamkas.semver.exceptions.VersionFormatException
import ru.dreamkas.semver.prerelease.PreRelease
import java.util.regex.Matcher
import java.util.regex.Pattern

object VersionBuilder {
    private const val full = "full"
    private const val base = "base"
    private const val comparable = "comparable"
    private const val major = "major"
    private const val minor = "minor"
    private const val patch = "patch"
    private const val preRelease = "preRelease"
    private const val metaData = "metaData"

    private val PATTERN = Pattern.compile(
            """
        (?<$full>
            (?<$comparable>
                (?<$base>
                    (?<$major>0|[1-9]+0*)
                    (?:
                        \.(?<$minor>0|[1-9]+0*)
                        (?:
                            \.(?<$patch>0|[1-9]+0*)
                        )?
                    )?
                )
                (?:-(?<$preRelease>[\da-z\-]+(?:\.[\da-z\-]+)*))?
            )
            (?:\+(?<$metaData>[\da-z\-\.]+))?
        )

    """.trimIndent(), Pattern.CASE_INSENSITIVE or Pattern.COMMENTS)

    /**
     * Build [Version][ru.dreamkas.semver.Version] from String template
     * ## @param [fullVersion]: `<major>.<minor>.<patch>[-preRelease][+metaData]`
     * * **_major_ ::** `0|[1-9]+0*`
     * * **_minor_ ::** `0|[1-9]+0*`
     * * **_patch_ ::** `0|[1-9]+0*`
     * * **_preRelease_ ::** `[id](\.[id])*`
     * * **_id_ ::** `[0-9a-zA-Z\-]+`
     * * **_metaData_ ::** `[0-9a-zA-Z\-]+`
     * @see <a href="http://semver.org/spec/v2.0.0.html">Semantic versioning details</a>
     * @exception VersionFormatException when version isn't correct
     */
    @JvmStatic
    fun build(fullVersion: String): Version {
        val matches = PATTERN.matcher(fullVersion)
        if (matches.matches()) {
            try {
                return makeVersion(matches)
            } catch (e: NumberFormatException) {
                throw VersionFormatException(fullVersion, e)
            }
        }
        throw VersionFormatException(fullVersion)
    }

    /**
     * Build [Version][ru.dreamkas.semver.Version] from all elements
     * @param [major]
     *        major version :: `0|[1-9]+0*`
     * @param [minor]
     *        minor version :: `0|[1-9]+0*`
     * @param [patch]
     *        patch version :: `0|[1-9]+0*`
     * @param [preRelease]
     *        pre-release :: `[[0-9a-zA-Z\-]+](\.[[0-9a-zA-Z\-]+])*`
     * @param [metaData]
     *        metadata :: `[0-9a-zA-Z\-]+`
     * @see Version
     * @see <a href="http://semver.org/spec/v2.0.0.html">Semantic versioning details</a>
     * @exception VersionFormatException when version isn't correct
     */
    @JvmStatic
    @JvmOverloads
    fun build(major: Int = 0, minor: Int = 0, patch: Int = 0, preRelease: String = "", metaData: String = ""): Version {
        var version = "$major.$minor.$patch"
        if (preRelease.isNotEmpty()) version += "-$preRelease"
        if (metaData.isNotEmpty()) version += "+$metaData"
        return build(version)
    }

    /**
     * Build [Version][ru.dreamkas.semver.Version] from comparable and metadata elements
     * @param [comparable]
     *        comparable part of version that consists of major, minor, patch and pre-release elements. For example: 1.5.8-beta.22
     * @param [metaData]
     *        metadata part:: `[0-9a-zA-Z\-]+`
     * @see Version
     * @see <a href="http://semver.org/spec/v2.0.0.html">Semantic versioning details</a>
     * @exception VersionFormatException when version isn't correct
     */
    @JvmStatic
    fun build(comparable: String, metaData: String = ""): Version {
        var version = comparable
        if (metaData.isNotEmpty()) version += "+$metaData"
        return build(version)
    }

    @JvmStatic
    fun matches(fullVersion: String) = PATTERN.matcher(fullVersion).matches()

    private fun makeVersion(matches: Matcher): Version {
        return Version(
                matches.group(major).toInt(),
                matches.group(minor)?.toInt() ?: 0,
                matches.group(patch)?.toInt() ?: 0,
                PreRelease(matches.group(preRelease)),
                MetaData(matches.group(metaData))
        )
    }

}

