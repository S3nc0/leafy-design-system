package design.leafy.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import design.leafy.sound.LeafySoundEvent
import design.leafy.theme.Leafy
import design.leafy.theme.LeafySize
import design.leafy.theme.LeafySpacing

enum class LeafyTagType { Neutral, Primary, Secondary, Tertiary, Success, Warning, Error, Info }

/**
 * A short label for status, category or a filter in use. Pair status colours with an icon or a word.
 *
 * @param onRemove Makes the tag dismissible; removing plays the `remove` event.
 */
@Composable
fun LeafyTag(
    text: String,
    modifier: Modifier = Modifier,
    type: LeafyTagType = LeafyTagType.Neutral,
    small: Boolean = false,
    icon: ImageVector? = null,
    sound: Boolean = true,
    onRemove: (() -> Unit)? = null,
) {
    val c = Leafy.colors
    val sfx = Leafy.sound
    val (background, foreground) = when (type) {
        LeafyTagType.Neutral -> c.surfaceContainerHighest to c.onSurface
        LeafyTagType.Primary -> c.primaryContainer to c.onPrimaryContainer
        LeafyTagType.Secondary -> c.secondaryContainer to c.onSecondaryContainer
        LeafyTagType.Tertiary -> c.tertiaryContainer to c.onTertiaryContainer
        LeafyTagType.Success -> c.successContainer to c.onSuccessContainer
        LeafyTagType.Warning -> c.warningContainer to c.onWarningContainer
        LeafyTagType.Error -> c.errorContainer to c.onErrorContainer
        LeafyTagType.Info -> c.infoContainer to c.onInfoContainer
    }
    Row(
        modifier = modifier
            .height(if (small) 18.dp else LeafySize.sizeXs)
            .widthIn(max = 208.dp)
            .background(background, CircleShape)
            .padding(start = LeafySpacing.space2, end = if (onRemove == null) LeafySpacing.space2 else LeafySpacing.spaceHalf),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(LeafySpacing.space1),
    ) {
        if (icon != null) Icon(icon, contentDescription = null, tint = foreground, modifier = Modifier.size(16.dp))
        Text(
            text,
            style = Leafy.typography.labelMedium,
            color = foreground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f, fill = false),
        )
        if (onRemove != null) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .clickable(onClickLabel = "Remove $text", role = Role.Button) {
                        if (sound) sfx.event(LeafySoundEvent.Remove)
                        onRemove()
                    },
                contentAlignment = Alignment.Center,
            ) {
                Icon(LeafyIcons.Close, contentDescription = "Remove $text", tint = foreground, modifier = Modifier.size(16.dp))
            }
        }
    }
}
