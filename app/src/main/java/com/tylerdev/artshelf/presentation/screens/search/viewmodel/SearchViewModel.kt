package com.tylerdev.artshelf.presentation.screens.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tylerdev.artshelf.domain.model.ArtImage
import com.tylerdev.artshelf.domain.model.RecentSearch
import com.tylerdev.artshelf.domain.usecase.ClearRecentSearchesUseCase
import com.tylerdev.artshelf.domain.usecase.GetRecentSearchesUseCase
import com.tylerdev.artshelf.domain.usecase.GetSavedArtIdsUseCase
import com.tylerdev.artshelf.domain.usecase.RecordSearchUseCase
import com.tylerdev.artshelf.domain.usecase.RemoveRecentSearchUseCase
import com.tylerdev.artshelf.domain.usecase.SearchArtUseCase
import com.tylerdev.artshelf.domain.usecase.ToggleSaveArtUseCase
import com.tylerdev.artshelf.presentation.screens.search.state.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

private val SEARCH_DEBOUNCE = 300.milliseconds
private const val UI_STATE_STOP_TIMEOUT_MILLIS = 5_000L

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel
    @Inject
    constructor(
        private val searchArtUseCase: SearchArtUseCase,
        private val toggleSaveArtUseCase: ToggleSaveArtUseCase,
        private val recordSearchUseCase: RecordSearchUseCase,
        private val removeRecentSearchUseCase: RemoveRecentSearchUseCase,
        private val clearRecentSearchesUseCase: ClearRecentSearchesUseCase,
        getSavedArtIdsUseCase: GetSavedArtIdsUseCase,
        getRecentSearchesUseCase: GetRecentSearchesUseCase,
    ) : ViewModel() {
        private val _query = MutableStateFlow("")
        val query: StateFlow<String> = _query

        private val debouncedQuery: Flow<String> =
            _query.debounce(SEARCH_DEBOUNCE).distinctUntilChanged()

        val uiState: StateFlow<SearchUiState> =
            debouncedQuery
                .map { query -> if (query.isBlank()) SearchUiState.Idle else SearchUiState.Active }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(UI_STATE_STOP_TIMEOUT_MILLIS),
                    initialValue = SearchUiState.Idle,
                )

        val pagingDataFlow: Flow<PagingData<ArtImage>> =
            debouncedQuery
                .flatMapLatest { query -> if (query.isBlank()) emptyFlow() else searchArtUseCase(query) }
                .cachedIn(viewModelScope)

        val savedArtIds: StateFlow<Set<Long>> =
            getSavedArtIdsUseCase()
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(UI_STATE_STOP_TIMEOUT_MILLIS),
                    initialValue = emptySet(),
                )

        val recentSearches: StateFlow<List<RecentSearch>> =
            getRecentSearchesUseCase()
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(UI_STATE_STOP_TIMEOUT_MILLIS),
                    initialValue = emptyList(),
                )

        fun onQueryChanged(newQuery: String) {
            _query.value = newQuery
        }

        fun onQueryCommitted(term: String) {
            _query.value = term
            viewModelScope.launch { recordSearchUseCase(term) }
        }

        fun onSearchSubmitted() {
            viewModelScope.launch { recordSearchUseCase(_query.value) }
        }

        fun onSaveClick(artImage: ArtImage) {
            viewModelScope.launch { toggleSaveArtUseCase(artImage) }
        }

        fun onRemoveRecentSearch(term: String) {
            viewModelScope.launch { removeRecentSearchUseCase(term) }
        }

        fun onClearRecentSearches() {
            viewModelScope.launch { clearRecentSearchesUseCase() }
        }
    }
