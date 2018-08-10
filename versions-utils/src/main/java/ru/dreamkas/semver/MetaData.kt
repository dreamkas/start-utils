package ru.dreamkas.semver

import java.util.ArrayList

class MetaData internal constructor(val metaData: String? = null) {
    val identifiers: List<String> = metaData?.split(".")?.toList() ?: ArrayList()

    override fun toString(): String {
        return metaData?.let { "+$metaData" } ?: ""
    }

    companion object {
        val EMPTY = MetaData()
    }
}