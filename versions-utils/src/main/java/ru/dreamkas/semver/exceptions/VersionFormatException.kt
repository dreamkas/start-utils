package ru.dreamkas.semver.exceptions

class VersionFormatException : IllegalArgumentException {
    internal constructor(version: String) : super(String.format("Can't parse version %s", version)) {}
    internal constructor(version: String, e: Exception) : super(String.format("%nCan't parse version %s.%n%s: %s", version, e.javaClass.name, e.message))
}
