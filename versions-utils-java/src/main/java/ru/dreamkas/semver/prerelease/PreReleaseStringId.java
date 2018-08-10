package ru.dreamkas.semver.prerelease;

import org.jetbrains.annotations.NotNull;

public final class PreReleaseStringId implements PreReleaseId {
    private final String id;

    public PreReleaseStringId(@NotNull String id) {
        this.id = id;
    }

    @Override
    public int compareTo(@NotNull PreReleaseId o) {
        if (!(o instanceof PreReleaseStringId)) {
            return 1;
        }
        return id.compareToIgnoreCase(((PreReleaseStringId) o).id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PreReleaseStringId)) {
            return false;
        }
        PreReleaseStringId that = (PreReleaseStringId) o;
        return id.equalsIgnoreCase(that.id);
    }

    @Override
    public int hashCode() {
        return id.toLowerCase().hashCode();
    }
}
