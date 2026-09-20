package com.tylerdev.artshelf.presentation.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.skydoves.navgraph.annotations.NavDestination
import com.github.skydoves.navgraph.annotations.NavEdge
import com.github.skydoves.navgraph.annotations.NavGraphRoot
import com.github.skydoves.navgraph.annotations.NavPreview
import com.tylerdev.artshelf.presentation.navigation.Screen
import com.tylerdev.artshelf.presentation.ui.theme.ArtShelfTheme
import com.tylerdev.artshelf.presentation.ui.theme.Graphite
import com.tylerdev.artshelf.presentation.ui.theme.InkBlack
import com.tylerdev.artshelf.presentation.ui.theme.OnSurfaceVariant
import com.tylerdev.artshelf.presentation.ui.theme.PaperCream
import com.tylerdev.artshelf.presentation.ui.theme.SignalRed
import com.tylerdev.artshelf.presentation.ui.theme.SunflowerYellow
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

private const val SPLASH_DISPLAY_DURATION_MILLIS = 2200L
private const val BANNER_ROTATION_DEGREES = -4f
private const val ICON_ROTATION_DEGREES = -6f
private const val STRIKE_ROTATION_DEGREES = -4f

@Suppress("ktlint:standard:function-naming")
@NavGraphRoot
@NavDestination(route = Screen.Splash::class)
@NavEdge(to = Screen.Search::class, label = "Finished")
@Composable
fun SplashScreen(onFinished: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(SPLASH_DISPLAY_DURATION_MILLIS.milliseconds)
        onFinished()
    }

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(InkBlack),
    ) {
        DiagonalBanner(modifier = Modifier.align(Alignment.TopCenter))

        TopCornerLabels(modifier = Modifier.align(Alignment.TopCenter))

        Column(
            modifier =
                Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            RadarTag()
            Spacer(modifier = Modifier.height(20.dp))
            ArtsyIconBadge()
            Spacer(modifier = Modifier.height(16.dp))
            ArtsyWordmark()
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "REBELLIOUS GALLERY & DISCOVERY ZINE",
                style = MaterialTheme.typography.labelMedium,
                color = SunflowerYellow,
                letterSpacing = 0.1.sp,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text =
                    "Unearth subversive movements, private exhibition " +
                        "vaults, and radical contemporary canvas.",
                style = MaterialTheme.typography.bodyMedium,
                color = OnSurfaceVariant,
                textAlign = TextAlign.Center,
            )
        }

        TicketStubCard(
            modifier =
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 24.dp, vertical = 40.dp),
        )
    }
}

@NavPreview(route = Screen.Splash::class, primary = true)
@Preview
@Suppress("ktlint:standard:function-naming")
@Composable
private fun SplashScreenPreview() {
    ArtShelfTheme {
        SplashScreen(onFinished = {})
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun DiagonalBanner(modifier: Modifier = Modifier) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(top = 60.dp)
                .background(SignalRed.copy(alpha = 0.12f))
                .rotate(BANNER_ROTATION_DEGREES),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "ARCHIVE DISRUPT ARCHIVE DISRUPT",
            style = MaterialTheme.typography.headlineLarge,
            color = SignalRed.copy(alpha = 0.35f),
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun TopCornerLabels(modifier: Modifier = Modifier) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = "SYS.BOOT // V2.0",
            style = MaterialTheme.typography.labelSmall,
            color = OnSurfaceVariant,
        )
        Box(
            modifier =
                Modifier
                    .background(SunflowerYellow, RoundedCornerShape(2.dp))
                    .padding(horizontal = 8.dp, vertical = 2.dp),
        ) {
            Text(
                text = "ISSUE #42",
                style = MaterialTheme.typography.labelSmall,
                color = InkBlack,
            )
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun RadarTag() {
    Box(
        modifier =
            Modifier
                .background(PaperCream, RoundedCornerShape(2.dp))
                .padding(horizontal = 10.dp, vertical = 4.dp),
    ) {
        Text(
            text = "RADAR ACTIVE // CLASSIFIED",
            style = MaterialTheme.typography.labelSmall,
            color = InkBlack,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun ArtsyIconBadge() {
    Box(contentAlignment = Alignment.TopEnd) {
        Box(
            modifier =
                Modifier
                    .size(96.dp)
                    .rotate(ICON_ROTATION_DEGREES)
                    .background(SignalRed, RoundedCornerShape(20.dp))
                    .border(3.dp, InkBlack, RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "A",
                style = MaterialTheme.typography.displayMedium,
                color = InkBlack,
            )
        }
        Text(
            text = "★",
            style = MaterialTheme.typography.titleLarge,
            color = SunflowerYellow,
            modifier =
                Modifier
                    .padding(4.dp)
                    .rotate(12f),
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun ArtsyWordmark() {
    Box(contentAlignment = Alignment.Center) {
        Text(
            text = "ARTSY",
            style = MaterialTheme.typography.displayLarge,
            color = PaperCream,
        )
        Box(
            modifier =
                Modifier
                    .width(140.dp)
                    .height(4.dp)
                    .rotate(STRIKE_ROTATION_DEGREES)
                    .background(SignalRed),
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun TicketStubCard(modifier: Modifier = Modifier) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .background(PaperCream, RoundedCornerShape(4.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "● STRONG ARCHIVE",
                style = MaterialTheme.typography.labelMedium,
                color = SignalRed,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "EST. REPO // 00002-XZ",
                style = MaterialTheme.typography.labelMedium,
                color = InkBlack,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .background(InkBlack),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "ISSUING 1 // 20260",
                style = MaterialTheme.typography.labelSmall,
                color = Graphite,
            )
            Text(
                text = "NON-CONFORMIST CATALOGUE",
                style = MaterialTheme.typography.labelSmall,
                color = Graphite,
            )
        }
    }
}
