package com.tylerdev.artshelf.presentation.screens.artworkdetail.viewmodel

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.tylerdev.artshelf.domain.model.ArtImage
import com.tylerdev.artshelf.domain.usecase.GetSavedArtByIdUseCase
import com.tylerdev.artshelf.domain.usecase.IsArtSavedUseCase
import com.tylerdev.artshelf.domain.usecase.ToggleSaveArtUseCase
import com.tylerdev.artshelf.domain.usecase.UpdateArtNotesUseCase
import com.tylerdev.artshelf.presentation.screens.artworkdetail.state.ArtworkDetailUiState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class ArtworkDetailViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private val mockToggleSaveArtUseCase = mockk<ToggleSaveArtUseCase>()
    private val mockUpdateArtNotesUseCase = mockk<UpdateArtNotesUseCase>()
    private val mockIsArtSavedUseCase = mockk<IsArtSavedUseCase>()
    private val mockGetSavedArtByIdUseCase = mockk<GetSavedArtByIdUseCase>()

    private val isSavedFlow = MutableStateFlow(true)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun buildViewModel(savedArt: ArtImage?): ArtworkDetailViewModel {
        every { mockIsArtSavedUseCase(NAV_ART.id) } returns isSavedFlow
        every { mockGetSavedArtByIdUseCase(NAV_ART.id) } returns flowOf(savedArt)
        return ArtworkDetailViewModel(
            savedStateHandle = buildSavedStateHandle(),
            toggleSaveArtUseCase = mockToggleSaveArtUseCase,
            updateArtNotesUseCase = mockUpdateArtNotesUseCase,
            isArtSavedUseCase = mockIsArtSavedUseCase,
            getSavedArtByIdUseCase = mockGetSavedArtByIdUseCase,
        )
    }

    @Test
    fun `uiState reflects notes persisted in the library when art is saved`() =
        runTest {
            val savedArt = NAV_ART.copy(notes = "gallery notes")
            val viewModel = buildViewModel(savedArt = savedArt)

            viewModel.uiState.test {
                skipItems(1)
                val state = awaitItem() as ArtworkDetailUiState.Content
                assertThat(state.isSaved).isTrue()
                assertThat(state.art.notes).isEqualTo("gallery notes")
            }
        }

    @Test
    fun `onEditClick shows dialog and onDismissEditDialog hides it`() =
        runTest {
            val viewModel = buildViewModel(savedArt = NAV_ART)

            viewModel.isEditDialogVisible.test {
                assertThat(awaitItem()).isFalse()

                viewModel.onEditClick()
                assertThat(awaitItem()).isTrue()

                viewModel.onDismissEditDialog()
                assertThat(awaitItem()).isFalse()
            }
        }

    @Test
    fun `onSaveNotes persists notes and hides dialog`() =
        runTest {
            coEvery { mockUpdateArtNotesUseCase(any(), any()) } returns Unit
            val viewModel = buildViewModel(savedArt = NAV_ART)
            viewModel.onEditClick()

            viewModel.onSaveNotes("updated notes")
            testDispatcher.scheduler.advanceUntilIdle()

            coVerify { mockUpdateArtNotesUseCase(NAV_ART.id, "updated notes") }
            assertThat(viewModel.isEditDialogVisible.value).isFalse()
        }

    private companion object {
        val NAV_ART =
            ArtImage(
                id = 1L,
                pageUrl = "https://example.com/page",
                previewUrl = "https://example.com/preview",
                webformatUrl = "https://example.com/webformat",
                largeImageUrl = "https://example.com/large",
                tags = listOf("art"),
                userName = "artist",
                likes = 10,
                downloads = 5,
            )

        fun buildSavedStateHandle(): SavedStateHandle =
            SavedStateHandle(
                mapOf(
                    "id" to NAV_ART.id.toString(),
                    "pageUrl" to NAV_ART.pageUrl,
                    "previewUrl" to NAV_ART.previewUrl,
                    "webformatUrl" to NAV_ART.webformatUrl,
                    "largeImageUrl" to NAV_ART.largeImageUrl,
                    "tags" to NAV_ART.tags.joinToString(","),
                    "userName" to NAV_ART.userName,
                    "likes" to NAV_ART.likes.toString(),
                    "downloads" to NAV_ART.downloads.toString(),
                ),
            )
    }
}
