package com.tylerdev.artshelf.domain.model

data class RecentSearch(
    val term: String,
    val searchedAtEpochMillis: Long,
)
