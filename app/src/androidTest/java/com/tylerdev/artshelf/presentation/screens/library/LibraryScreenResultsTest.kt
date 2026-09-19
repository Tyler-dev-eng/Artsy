package com.tylerdev.artshelf.presentation.screens.library

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.hasScrollToIndexAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.tylerdev.artshelf.domain.model.ArtImage
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private const val HAS_NOTES_DESCRIPTION = "Has notes"
private const val REMOVE_DESCRIPTION = "Remove from library"

@RunWith(AndroidJUnit4::class)
class LibraryScreenResultsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun setLibraryContent(
        artItems: List<ArtImage>,
        onRemoveClick: (ArtImage) -> Unit = {},
        onArtClick: (ArtImage) -> Unit = {},
    ) {
        composeTestRule.setContent {
            val pagingItems = flowOf(PagingData.from(artItems)).collectAsLazyPagingItems()
            LibraryScreenResults(
                artItems = pagingItems,
                onRemoveClick = onRemoveClick,
                onArtClick = onArtClick,
            )
        }
        composeTestRule.waitForIdle()
    }

    @Test
    fun showsNoteIndicator_whenNotesArePresent() {
        setLibraryContent(artItems = listOf(buildArtImage(id = 1, notes = "remember the frame color")))

        composeTestRule.onNodeWithContentDescription(HAS_NOTES_DESCRIPTION).assertExists()
    }

    @Test
    fun hidesNoteIndicator_whenNotesAreNull() {
        setLibraryContent(artItems = listOf(buildArtImage(id = 1, notes = null)))

        composeTestRule.onAllNodesWithContentDescription(HAS_NOTES_DESCRIPTION).assertCountEquals(0)
    }

    @Test
    fun hidesNoteIndicator_whenNotesAreBlank() {
        setLibraryContent(artItems = listOf(buildArtImage(id = 1, notes = "   ")))

        composeTestRule.onAllNodesWithContentDescription(HAS_NOTES_DESCRIPTION).assertCountEquals(0)
    }

    @Test
    fun noteIndicator_exposesContentDescription_forAccessibility() {
        setLibraryContent(artItems = listOf(buildArtImage(id = 1, notes = "framed in oak")))

        composeTestRule
            .onNodeWithContentDescription(HAS_NOTES_DESCRIPTION, useUnmergedTree = true)
            .assertExists()
    }

    @Test
    fun unsaveButton_stillClickable_whenNoteIndicatorIsShown() {
        var removedArt: ArtImage? = null
        val art = buildArtImage(id = 1, notes = "framed in oak")
        setLibraryContent(
            artItems = listOf(art),
            onRemoveClick = { removedArt = it },
        )

        composeTestRule.onNodeWithContentDescription(REMOVE_DESCRIPTION).performClick()

        assertThat(removedArt).isEqualTo(art)
    }

    @Test
    fun cardClick_andNoteIndicator_bothWork_inUnmergedTree() {
        var clickedArt: ArtImage? = null
        val art = buildArtImage(id = 1, notes = "framed in oak", title = "Sunset Study")
        setLibraryContent(
            artItems = listOf(art),
            onArtClick = { clickedArt = it },
        )

        composeTestRule
            .onNodeWithContentDescription(HAS_NOTES_DESCRIPTION, useUnmergedTree = true)
            .assertExists()
        composeTestRule.onNodeWithText("Sunset Study").performClick()

        assertThat(clickedArt).isEqualTo(art)
    }

    @Test
    fun mixedList_showsNoteIndicator_onlyOnItemsWithNotes() {
        setLibraryContent(
            artItems = listOf(
                buildArtImage(id = 1, notes = "has a note"),
                buildArtImage(id = 2, notes = null),
                buildArtImage(id = 3, notes = ""),
                buildArtImage(id = 4, notes = "also has a note"),
            ),
        )
        val listNode = composeTestRule.onNode(hasScrollToIndexAction())

        // Item indices are offset by 1 in the LazyColumn because of the header item.
        // useUnmergedTree = true because the clickable card merges descendant semantics.
        listNode.performScrollToIndex(1)
        composeTestRule.onNodeWithTag(noteIndicatorTestTag(1), useUnmergedTree = true).assertExists()

        listNode.performScrollToIndex(2)
        composeTestRule.onNodeWithTag(noteIndicatorTestTag(2), useUnmergedTree = true).assertDoesNotExist()

        listNode.performScrollToIndex(3)
        composeTestRule.onNodeWithTag(noteIndicatorTestTag(3), useUnmergedTree = true).assertDoesNotExist()

        listNode.performScrollToIndex(4)
        composeTestRule.onNodeWithTag(noteIndicatorTestTag(4), useUnmergedTree = true).assertExists()
    }

    private fun buildArtImage(
        id: Long,
        notes: String? = null,
        title: String? = null,
    ) = ArtImage(
        id = id,
        pageUrl = "https://example.com/page/$id",
        previewUrl = "https://example.com/preview/$id",
        webformatUrl = "https://example.com/webformat/$id",
        largeImageUrl = "https://example.com/large/$id",
        tags = listOf("art", "sample"),
        userName = "artist$id",
        likes = 10,
        downloads = 5,
        notes = notes,
        title = title,
    )
}
