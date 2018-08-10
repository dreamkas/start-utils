package ru.dreamkas.semver.prerelease;

public final class PreReleaseNumericId implements PreReleaseId {
    private final int id;

    public PreReleaseNumericId(int id) {
        this.id = id;
    }

    @Override
    public int compareTo(PreReleaseId o) {
        if (!(o instanceof PreReleaseNumericId)) {
            return -1;
        }
        return Integer.compare(id, ((PreReleaseNumericId) o).id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PreReleaseNumericId that = (PreReleaseNumericId) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
