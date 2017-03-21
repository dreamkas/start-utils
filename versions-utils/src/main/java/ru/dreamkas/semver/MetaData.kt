package ru.dreamkas.semver.metadata

import java.util.*

class MetaData internal constructor(
        val metaData: String?,
        val identifiers: List<String> = metaData?.split(".")?.toList() ?: ArrayList<String>()) {

    override fun toString(): String {
        return metaData ?: ""
    }
}