package ru.dreamkas.semver;

public final class VersionFormatException extends IllegalArgumentException {
    public VersionFormatException(String version) {
        super("Can't parse version " + version);
    }

    public VersionFormatException(String version, Throwable cause) {
        super("Can't parse version " + version, cause);
    }
}
