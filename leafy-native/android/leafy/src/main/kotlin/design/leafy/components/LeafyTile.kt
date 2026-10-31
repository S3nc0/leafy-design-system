package design.leafy.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import design.leafy.sound.LeafySoundEvent
import design.leafy.theme.Leafy
import design.leafy.theme.LeafySize
import design.leafy.theme.LeafySpacing

/** A base tile: a square-cornered block of content on `layer-01` (Carbon's tile). */
@Composable
fun LeafyTile(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    TileFrame(modifier, endPadding = LeafySpacing.space4, bottomPadding = LeafySpacing.space4, content = content)
}

/** A clickable tile with a trailing arrow, for navigation. */
@Composable
fun LeafyTile(onClick: () -> Unit, modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Box(modifier.clickable(role = Role.Button, onClick = onClick)) {
        TileFrame(Modifier, endPadding = LeafySpacing.space4, bottomPadding = LeafySpacing.space12, content = content)
        Icon(
            LeafyIcons.ArrowForward,
            contentDescription = null,
            tint = Leafy.colors.primary,
            modifier = Modifier.align(Alignment.BottomEnd).padding(LeafySpacing.space4).size(20.dp),
        )
    }
}

/** A selectable tile; selecting plays `select` and clearing plays `deselect`. */
@Composable
fun LeafyTile(
    selected: Boolean,
    onSelectedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    sound: Boolean = true,
    content: @Composable ColumnScope.() -> Unit,
) {
    val c = Leafy.colors
    val sfx = Leafy.sound
    Box(
        modifier
            .toggleable(value = selected, role = Role.Checkbox) {
                if (sound) sfx.event(if (it) LeafySoundEvent.Select else LeafySoundEvent.Deselect)
                onSelectedChange(it)
            }
            .then(if (selected) Modifier.border(1.dp, c.onSurface) else Modifier),
    ) {
        TileFrame(Modifier, endPadding = LeafySpacing.space12, bottomPadding = LeafySpacing.space4, content = content)
        Icon(
            if (selected) LeafyIcons.CheckBox else LeafyIcons.CheckBoxOutlineBlank,
            contentDescription = null,
            tint = if (selected) c.primary else c.onSurfaceVariant,
            modifier = Modifier.align(Alignment.TopEnd).padding(LeafySpacing.space4).size(20.dp),
        )
    }
}

@Composable
private fun TileFrame(modifier: Modifier, endPadding: Dp, bottomPadding: Dp, content: @Composable ColumnScope.() -> Unit) {
    val c = Leafy.colors
    CompositionLocalProvider(LocalContentColor provides c.onSurface) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .heightIn(min = LeafySize.sizeXl)
                .background(c.layer01)
                .padding(start = LeafySpacing.space4, top = LeafySpacing.space4, end = endPadding, bottom = bottomPadding),
            content = content,
        )
    }
}
