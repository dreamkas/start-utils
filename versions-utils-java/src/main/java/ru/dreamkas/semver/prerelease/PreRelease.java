package ru.dreamkas.semver.prerelease;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public final class PreRelease {
    public static final PreRelease EMPTY = new PreRelease();
    private String preRelease;
    private List<PreReleaseId> identifiers = Collections.emptyList();

    private PreRelease() {}

    public PreRelease(String preRelease) {
        this.preRelease = preRelease;
        if (preRelease != null) {
            List<PreReleaseId> result = new ArrayList<>();
            for (String s : preRelease.split("\\.")) {
                if (StringUtils.isNumeric(s)) {
                    result.add(new PreReleaseNumericId(Integer.parseInt(s)));
                } else {
                    result.add(new PreReleaseStringId(s));
                }
            }
            identifiers = result;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PreRelease that = (PreRelease) o;
        return identifiers.equals(that.identifiers);
    }

    @Override
    public int hashCode() {
        return preRelease.hashCode();
    }

    @Override
    public String toString() {
        return preRelease == null ? "" : ("-" + preRelease);
    }

    public List<PreReleaseId> getIdentifiers() {
        return identifiers;
    }
}
