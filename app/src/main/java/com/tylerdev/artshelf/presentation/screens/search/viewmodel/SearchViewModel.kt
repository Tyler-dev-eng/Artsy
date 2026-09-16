package com.tylerdev.artshelf.presentation.screens.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tylerdev.artshelf.domain.usecase.SearchArtUseCase
import com.tylerdev.artshelf.domain.util.Resource
import com.tylerdev.artshelf.presentation.screens.search.state.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

private val SEARCH_DEBOUNCE = 300.milliseconds

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel
    @Inject
    constructor(
        private val searchArtUseCase: SearchArtUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
        val uiState: StateFlow<SearchUiState> = _uiState

        private val _query = MutableStateFlow("")
        val query: StateFlow<String> = _query

        init {
            viewModelScope.launch {
                _query
                    .debounce(SEARCH_DEBOUNCE)
                    .distinctUntilChanged()
                    .collectLatest { query -> searchArt(query) }
            }
        }

        fun onQueryChanged(newQuery: String) {
            _query.value = newQuery
        }

        private suspend fun searchArt(query: String) {
            if (query.isBlank()) {
                _uiState.value = SearchUiState.Idle
                return
            }

            searchArtUseCase(query).collectLatest { resource ->
                _uiState.value =
                    when (resource) {
                        is Resource.Loading -> SearchUiState.Loading
                        is Resource.Success -> SearchUiState.Success(resource.data.orEmpty())
                        is Resource.Error -> SearchUiState.Error(resource.message.orEmpty())
                    }
            }
        }
    }
