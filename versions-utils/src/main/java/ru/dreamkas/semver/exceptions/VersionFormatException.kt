package ru.dreamkas.semver.exceptions

class VersionFormatException : IllegalArgumentException {
    internal constructor(version: String) : super("Can't parse version $version")
    internal constructor(version: String, e: Exception) : super("Can't parse version $version", e)
}
