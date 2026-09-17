package com.tylerdev.artshelf.presentation.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.HideImage
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.NorthEast
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.tylerdev.artshelf.presentation.components.drawHardOffsetShadow
import com.tylerdev.artshelf.presentation.ui.theme.AntonFontFamily
import com.tylerdev.artshelf.presentation.ui.theme.GalleryWhite
import com.tylerdev.artshelf.presentation.ui.theme.InkBlack
import com.tylerdev.artshelf.presentation.ui.theme.OnSurfaceVariant
import com.tylerdev.artshelf.presentation.ui.theme.Outline
import com.tylerdev.artshelf.presentation.ui.theme.PaperCream
import com.tylerdev.artshelf.presentation.ui.theme.SignalRed
import com.tylerdev.artshelf.presentation.ui.theme.SunflowerYellow
import com.tylerdev.artshelf.presentation.ui.theme.SurfaceContainerHigh
import com.tylerdev.artshelf.presentation.ui.theme.SurfaceContainerLow

private val SCREEN_MARGIN = 20.dp
private val SECTION_GAP = 24.dp
private val TOUCH_TARGET = 48.dp

data class SeedPrompt(
    val query: String,
    val label: String,
    val background: Color,
    val content: Color,
    val shadow: Color,
    val rotationDegrees: Float,
    val badge: String? = null,
)

data class RecentSignal(
    val term: String,
    val meta: String,
    val isStarred: Boolean = false,
)

private val DEFAULT_SEED_PROMPTS =
    listOf(
        SeedPrompt("neo-dadaism", "#NEO-DADAISM", InkBlack, GalleryWhite, SignalRed, -2f),
        SeedPrompt("cyber-punk origami", "CYBER-PUNK ORIGAMI", PaperCream, InkBlack, InkBlack, 2f, badge = "NEW"),
        SeedPrompt("raw expressionism", "RAW EXPRESSIONISM", SignalRed, GalleryWhite, SunflowerYellow, -1f),
        SeedPrompt("acid botanicals", "ACID BOTANICALS", InkBlack, SunflowerYellow, PaperCream, 3f),
        SeedPrompt("brutalist archive", "BRUTALIST ARCHIVE", SurfaceContainerHigh, GalleryWhite, SignalRed, -3f),
        SeedPrompt("street glitch", "STREET GLITCH", SunflowerYellow, InkBlack, InkBlack, 1f),
    )

private val DEFAULT_RECENT_SIGNALS =
    listOf(
        RecentSignal("yellow flowers", "14:02 EST • 842 ARTWORKS", isStarred = true),
        RecentSignal("brutalist concrete", "YESTERDAY • ARCHITECTURE ARCHIVE"),
        RecentSignal("tokyo neon dusk", "2 DAYS AGO • STREET PHOTOGRAPHY"),
    )

private val HeadlineXlMobile =
    TextStyle(
        fontFamily = AntonFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 40.sp,
        lineHeight = 42.sp,
        letterSpacing = (-0.02).em,
    )

@Suppress("ktlint:standard:function-naming")
@Composable
fun SearchScreenInitialState(
    modifier: Modifier = Modifier,
    seedPrompts: List<SeedPrompt> = DEFAULT_SEED_PROMPTS,
    onSeedClick: (String) -> Unit = {},
    featuredSeedQuery: String = "yellow flowers",
    onFeaturedSeedClick: (String) -> Unit = {},
    recentSignals: List<RecentSignal> = DEFAULT_RECENT_SIGNALS,
    onSignalClick: (String) -> Unit = {},
    onRemoveSignal: (String) -> Unit = {},
    onClearAllSignals: () -> Unit = {},
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = SCREEN_MARGIN),
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        HeroManifesto()
        Spacer(modifier = Modifier.height(SECTION_GAP))
        RadarSeedsSection(seedPrompts = seedPrompts, onSeedClick = onSeedClick)
        Spacer(modifier = Modifier.height(SECTION_GAP))
        FeaturedSpotlightCard(onInjectClick = { onFeaturedSeedClick(featuredSeedQuery) })
        Spacer(modifier = Modifier.height(SECTION_GAP))
        RecentSignalsLedger(
            signals = recentSignals,
            onSignalClick = onSignalClick,
            onRemoveSignal = onRemoveSignal,
            onClearAllSignals = onClearAllSignals,
        )
        Spacer(modifier = Modifier.height(SECTION_GAP))
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun HeroManifesto() {
    Column {
        DispatchStencilTag()
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "WHAT WILL YOU HUNT TODAY?",
            style = HeadlineXlMobile,
            color = GalleryWhite,
            modifier = Modifier.graphicsLayer { rotationZ = -1f },
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier =
                Modifier
                    .fillMaxWidth(fraction = 0.75f)
                    .height(8.dp)
                    .graphicsLayer { rotationZ = -2f }
                    .background(SignalRed),
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text =
                "Type a movement, subversive artist, spectrum wavelength, or raw emotion into " +
                    "the radar stub above.",
            style = MaterialTheme.typography.bodyMedium,
            color = OnSurfaceVariant,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun DispatchStencilTag() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =
            Modifier
                .graphicsLayer { rotationZ = -2f }
                .drawHardOffsetShadow(3.dp, SunflowerYellow)
                .background(InkBlack)
                .padding(horizontal = 12.dp, vertical = 6.dp),
    ) {
        Box(modifier = Modifier.size(8.dp).background(SignalRed))
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "CURATOR'S DISPATCH // ISSUE #42",
            style = MaterialTheme.typography.labelMedium,
            color = GalleryWhite,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun RadarSeedsSection(
    seedPrompts: List<SeedPrompt>,
    onSeedClick: (String) -> Unit,
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(10.dp).graphicsLayer { rotationZ = 45f }.background(SignalRed))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "RADAR SEEDS",
                    style = MaterialTheme.typography.headlineLarge,
                    color = GalleryWhite,
                )
            }
            Text(
                text = "// TAP TO INJECT",
                style = MaterialTheme.typography.labelSmall,
                color = SunflowerYellow,
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            seedPrompts.forEach { prompt ->
                SeedPill(prompt = prompt, onClick = { onSeedClick(prompt.query) })
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun SeedPill(
    prompt: SeedPrompt,
    onClick: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier =
            Modifier
                .defaultMinSize(minHeight = TOUCH_TARGET)
                .clickable(onClick = onClick)
                .semantics { contentDescription = "Search seed ${prompt.label}" },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier =
                Modifier
                    .graphicsLayer { rotationZ = prompt.rotationDegrees }
                    .drawHardOffsetShadow(3.dp, prompt.shadow)
                    .background(prompt.background)
                    .padding(horizontal = 14.dp, vertical = 8.dp),
        ) {
            if (prompt.badge != null) {
                Text(
                    text = prompt.badge,
                    style = MaterialTheme.typography.labelSmall,
                    color = prompt.background,
                    modifier = Modifier.background(prompt.content).padding(horizontal = 4.dp),
                )
                Spacer(modifier = Modifier.width(6.dp))
            }
            Text(
                text = prompt.label,
                style = MaterialTheme.typography.labelMedium,
                color = prompt.content,
            )
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun FeaturedSpotlightCard(onInjectClick: () -> Unit) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .graphicsLayer { rotationZ = 1f }
                .drawHardOffsetShadow(6.dp, SignalRed)
                .background(InkBlack)
                .padding(16.dp),
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "DAILY TRANSMISSION",
                    style = MaterialTheme.typography.labelSmall,
                    color = GalleryWhite,
                    modifier = Modifier.background(SignalRed).padding(horizontal = 6.dp, vertical = 2.dp),
                )
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = SunflowerYellow,
                    modifier = Modifier.size(20.dp),
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            FeaturedArtworkPreview()
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "TODAY'S SEED: YELLOW FLOWERS",
                style = MaterialTheme.typography.headlineLarge,
                color = GalleryWhite,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text =
                    "Explore 140+ deconstructed floral prints, tactile oil studies, and " +
                        "aggressive yellow pigment explorations.",
                style = MaterialTheme.typography.bodySmall,
                color = OnSurfaceVariant,
            )
            Spacer(modifier = Modifier.height(12.dp))
            InjectSeedButton(onClick = onInjectClick)
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun FeaturedArtworkPreview() {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 10f)
                .drawHardOffsetShadow(3.dp, InkBlack)
                .background(SurfaceContainerLow),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.Filled.HideImage,
            contentDescription = null,
            tint = SunflowerYellow.copy(alpha = 0.4f),
            modifier = Modifier.size(40.dp),
        )
        Text(
            text = "99.4% HIT",
            style = MaterialTheme.typography.labelMedium,
            color = InkBlack,
            modifier =
                Modifier
                    .align(Alignment.TopEnd)
                    .background(SunflowerYellow)
                    .padding(horizontal = 8.dp, vertical = 2.dp),
        )
        Column(
            modifier =
                Modifier
                    .align(Alignment.BottomStart)
                    .graphicsLayer { rotationZ = -2f }
                    .background(PaperCream)
                    .padding(horizontal = 10.dp, vertical = 6.dp),
        ) {
            Text(
                text = "SOLARIS ARCHIVE // 04",
                style = MaterialTheme.typography.headlineLarge,
                color = InkBlack,
            )
            Text(
                text = "Curated by Artsy Underground",
                style = MaterialTheme.typography.bodySmall,
                color = InkBlack.copy(alpha = 0.7f),
            )
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun InjectSeedButton(onClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier =
            Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = TOUCH_TARGET)
                .graphicsLayer { rotationZ = -1f }
                .drawHardOffsetShadow(4.dp, PaperCream)
                .background(SignalRed)
                .clickable(onClick = onClick)
                .semantics { contentDescription = "Inject curated seed" }
                .padding(horizontal = 16.dp, vertical = 14.dp),
    ) {
        Text(
            text = "INJECT CURATED SEED",
            style = MaterialTheme.typography.labelLarge,
            color = GalleryWhite,
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = GalleryWhite,
            modifier = Modifier.size(20.dp),
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun RecentSignalsLedger(
    signals: List<RecentSignal>,
    onSignalClick: (String) -> Unit,
    onRemoveSignal: (String) -> Unit,
    onClearAllSignals: () -> Unit,
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.History,
                    contentDescription = null,
                    tint = SignalRed,
                    modifier = Modifier.size(18.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "RECENT SIGNALS",
                    style = MaterialTheme.typography.headlineLarge,
                    color = GalleryWhite,
                )
            }
            Text(
                text = "CLEAR LOG",
                style = MaterialTheme.typography.labelSmall,
                color = Outline,
                modifier =
                    Modifier
                        .defaultMinSize(minHeight = TOUCH_TARGET)
                        .clickable(onClick = onClearAllSignals)
                        .semantics { contentDescription = "Clear all recent signals" },
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (signals.isEmpty()) {
            EmptyLedgerNotice()
        } else {
            RecentSignalsCard(signals, onSignalClick, onRemoveSignal)
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun RecentSignalsCard(
    signals: List<RecentSignal>,
    onSignalClick: (String) -> Unit,
    onRemoveSignal: (String) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .drawHardOffsetShadow(4.dp, InkBlack)
                .background(PaperCream)
                .padding(16.dp),
    ) {
        signals.forEachIndexed { index, signal ->
            RecentSignalRow(
                signal = signal,
                onClick = { onSignalClick(signal.term) },
                onRemove = { onRemoveSignal(signal.term) },
            )
            if (index != signals.lastIndex) {
                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(InkBlack.copy(alpha = 0.15f)))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "LOG-STATE: PRISTINE_IDLE",
                style = MaterialTheme.typography.bodySmall,
                color = InkBlack.copy(alpha = 0.6f),
            )
            Text(
                text = "INDEX VERIFIED",
                style = MaterialTheme.typography.labelSmall,
                color = GalleryWhite,
                modifier = Modifier.background(InkBlack).padding(horizontal = 6.dp, vertical = 2.dp),
            )
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun RecentSignalRow(
    signal: RecentSignal,
    onClick: () -> Unit,
    onRemove: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .semantics { contentDescription = "Search recent term ${signal.term}" }
                .padding(vertical = 8.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = if (signal.isStarred) Icons.Filled.Star else Icons.Filled.NorthEast,
                contentDescription = null,
                tint = if (signal.isStarred) SunflowerYellow else InkBlack.copy(alpha = 0.6f),
                modifier = Modifier.size(18.dp),
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = signal.term,
                    style = MaterialTheme.typography.titleSmall,
                    color = InkBlack,
                )
                Text(
                    text = signal.meta,
                    style = MaterialTheme.typography.bodySmall,
                    color = InkBlack.copy(alpha = 0.6f),
                )
            }
        }
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = null,
            tint = InkBlack.copy(alpha = 0.4f),
            modifier =
                Modifier
                    .size(TOUCH_TARGET / 2)
                    .clickable(onClick = onRemove)
                    .semantics { contentDescription = "Remove ${signal.term} from recent signals" },
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun EmptyLedgerNotice() {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .drawHardOffsetShadow(4.dp, InkBlack)
                .background(PaperCream)
                .padding(24.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "ALL PREVIOUS SIGNALS PURGED",
            style = MaterialTheme.typography.labelMedium,
            color = InkBlack.copy(alpha = 0.4f),
        )
    }
}
