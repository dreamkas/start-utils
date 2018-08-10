package ru.dreamkas.semver;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class MetaData {
    static final MetaData EMPTY = new MetaData();
    private String metaData;
    private List<String> identifiers = Collections.emptyList();

    private MetaData() {}

    public MetaData(String metaData) {
        this.metaData = metaData;
        if (metaData != null) {
            identifiers = Arrays.asList(metaData.split("\\."));
        }
    }

    public String getMetaData() {
        return metaData;
    }

    public List<String> getIdentifiers() {
        return identifiers;
    }

    @Override
    public String toString() {
        return metaData == null ? "" : ("+" + metaData);
    }
}
