package ru.dreamkas.semver;

import java.util.Comparator;
import java.util.List;

import ru.dreamkas.semver.prerelease.PreReleaseId;

public class VersionComparator {
    private static final Comparator<Version> PRE_RELEASE_COMPARATOR = (v1, v2) -> {
        List<PreReleaseId> first = v1.getPreRelease().getIdentifiers();
        List<PreReleaseId> second = v2.getPreRelease().getIdentifiers();
        int firstSize = first.size();
        int secondSize = second.size();
        if (firstSize == secondSize && firstSize == 0) {
            return 0;
        } else if (firstSize == 0) {
            return 1;
        } else if (secondSize == 0) {
            return -1;
        }
        int maxId = Math.max(firstSize, secondSize) - 1;
        for (int i = 0; i <= maxId; i++) {
            int result = firstSize <= i ? -1 : (secondSize <= i ? 1 : first.get(i).compareTo(second.get(i)));
            if (result != 0) {
                return result;
            }
        }
        return 0;
    };

    public static final Comparator<Version> SEMVER = Comparator.comparing(Version::getMajor)
        .thenComparing(Version::getMinor)
        .thenComparing(Version::getPatch)
        .thenComparing(PRE_RELEASE_COMPARATOR);
}
