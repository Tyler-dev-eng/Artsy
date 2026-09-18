package com.tylerdev.artshelf.data.local.entity

import com.tylerdev.artshelf.domain.model.RecentSearch

fun RecentSearchEntity.toRecentSearch(): RecentSearch = RecentSearch(
    term = term,
    searchedAtEpochMillis = searchedAtEpochMillis,
)
