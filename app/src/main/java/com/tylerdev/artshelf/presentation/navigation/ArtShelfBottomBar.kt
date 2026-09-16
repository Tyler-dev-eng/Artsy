package com.tylerdev.artshelf.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.tylerdev.artshelf.presentation.ui.theme.Background
import com.tylerdev.artshelf.presentation.ui.theme.GalleryWhite
import com.tylerdev.artshelf.presentation.ui.theme.OnSurfaceVariant
import com.tylerdev.artshelf.presentation.ui.theme.SignalRed
import com.tylerdev.artshelf.presentation.ui.theme.SunflowerYellow

private data class BottomNavItem(
    val screen: Screen,
    val label: String,
    val icon: ImageVector,
)

private val BOTTOM_NAV_ITEMS =
    listOf(
        BottomNavItem(Screen.Search, "SEARCH", Icons.Filled.Search),
        BottomNavItem(Screen.Library, "LIBRARY", Icons.Filled.PhotoLibrary),
    )

private const val UNDERLINE_ROTATION_DEGREES = -6f
private val NAV_ICON_SIZE = 28.dp
private val NAV_LABEL_SPACING = 2.dp
private val NAV_UNDERLINE_SPACING = 2.dp
private val NAV_UNDERLINE_HEIGHT = 3.dp
private val NAV_UNDERLINE_WIDTH_SELECTED = 32.dp
private val NAV_UNDERLINE_WIDTH_UNSELECTED = 0.dp

@Suppress("ktlint:standard:function-naming")
@Composable
fun ArtShelfBottomBar(navController: NavHostController) {
    val currentRoute =
        navController
            .currentBackStackEntryAsState()
            .value
            ?.destination
            ?.route

    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .background(Background)
                .navigationBarsPadding(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        BOTTOM_NAV_ITEMS.forEach { item ->
            val isSelected = currentRoute == item.screen.route
            BottomNavBarItem(
                item = item,
                isSelected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                },
            )
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun BottomNavBarItem(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val tint = if (isSelected) SunflowerYellow else OnSurfaceVariant
    val labelColor = if (isSelected) GalleryWhite else OnSurfaceVariant
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier =
            Modifier.clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.label,
            tint = tint,
            modifier = Modifier.size(NAV_ICON_SIZE),
        )
        Text(
            text = item.label,
            style = MaterialTheme.typography.labelLarge,
            color = labelColor,
            modifier = Modifier.padding(top = NAV_LABEL_SPACING),
        )
        Box(
            modifier =
                Modifier
                    .padding(top = NAV_UNDERLINE_SPACING)
                    .width(if (isSelected) NAV_UNDERLINE_WIDTH_SELECTED else NAV_UNDERLINE_WIDTH_UNSELECTED)
                    .height(NAV_UNDERLINE_HEIGHT)
                    .rotate(UNDERLINE_ROTATION_DEGREES)
                    .background(if (isSelected) SignalRed else Color.Transparent),
        )
    }
}
