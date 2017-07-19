package ru.dreamkas.semver

import java.util.ArrayList

class MetaData internal constructor(
        val metaData: String? = null,
        val identifiers: List<String> = metaData?.split(".")?.toList() ?: ArrayList<String>()) {

    override fun toString(): String {
        return metaData ?: ""
    }
}