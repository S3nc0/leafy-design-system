package design.leafy.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import design.leafy.sound.LeafySoundEvent
import design.leafy.theme.Leafy
import design.leafy.theme.LeafyElevation
import design.leafy.theme.LeafyOpacity
import design.leafy.theme.LeafyRadius
import design.leafy.theme.LeafySize
import design.leafy.theme.LeafySpacing

/** Button variants by emphasis. Use one [Filled] button per view. */
enum class LeafyButtonVariant {
    /** The one main action (Carbon primary). */
    Filled,

    /** The partner of a filled button, such as "Save draft" beside "Publish" (Carbon secondary). */
    Inverse,

    /** Important but quieter than filled. */
    Tonal,

    /** Medium emphasis (Carbon tertiary). */
    Outlined,

    /** Lowest emphasis: Cancel, inline actions (Carbon ghost). */
    Text,

    /** Like tonal, over images or busy backgrounds. */
    Elevated,
}

/** Carbon control heights: small 32, medium 40 (default), large 48. */
enum class LeafyControlSize(val height: Dp, internal val horizontalPadding: Dp) {
    Sm(LeafySize.sizeSm, LeafySpacing.space3),
    Md(LeafySize.sizeMd, LeafySpacing.space4),
    Lg(LeafySize.sizeLg, LeafySpacing.space4),
}

/** Where a button's icon sits. Leafy follows Carbon: the icon trails the label. */
enum class LeafyIconPosition { Start, End }

/**
 * A Leafy button: 2dp corners, Carbon sizes, M3 colour roles.
 *
 * @param text Verb first, sentence case, one to three words.
 * @param danger Turns filled, outlined and text buttons to the error role for destructive actions.
 * @param sound Plays the `press` event, which is silent by default; false keeps it silent even if a product turns press sounds on.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeafyButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: LeafyButtonVariant = LeafyButtonVariant.Filled,
    size: LeafyControlSize = LeafyControlSize.Md,
    danger: Boolean = false,
    icon: ImageVector? = null,
    iconPosition: LeafyIconPosition = LeafyIconPosition.End,
    enabled: Boolean = true,
    sound: Boolean = true,
) {
    val c = Leafy.colors
    val sfx = Leafy.sound
    val (container, content) = when (variant) {
        LeafyButtonVariant.Filled -> if (danger) c.error to c.onError else c.primary to c.onPrimary
        LeafyButtonVariant.Inverse -> c.inverseSurface to c.inverseOnSurface
        LeafyButtonVariant.Tonal -> c.secondaryContainer to c.onSecondaryContainer
        LeafyButtonVariant.Elevated -> c.surfaceContainerLow to c.primary
        LeafyButtonVariant.Outlined, LeafyButtonVariant.Text -> Color.Transparent to if (danger) c.error else c.primary
    }
    val filledKind = variant != LeafyButtonVariant.Outlined && variant != LeafyButtonVariant.Text
    val contentColor = if (enabled) content else c.onSurface.copy(alpha = LeafyOpacity.disabledContent)
    val containerColor = when {
        enabled -> container
        filledKind -> c.onSurface.copy(alpha = LeafyOpacity.disabledContainer)
        else -> Color.Transparent
    }
    val interaction = remember { MutableInteractionSource() }
    val focused by interaction.collectIsFocusedAsState()
    val border = when {
        // Carbon's 2dp focus line for keyboard and D-pad users.
        focused -> BorderStroke(2.dp, c.focus)
        variant == LeafyButtonVariant.Outlined ->
            BorderStroke(1.dp, if (enabled) contentColor else c.onSurface.copy(alpha = LeafyOpacity.disabledContainer))
        else -> null
    }
    val leading = icon != null && iconPosition == LeafyIconPosition.Start
    val trailing = icon != null && iconPosition == LeafyIconPosition.End
    Surface(
        onClick = {
            if (sound) sfx.event(LeafySoundEvent.Press)
            onClick()
        },
        modifier = modifier.height(size.height).defaultMinSize(minWidth = LeafySize.sizeLg),
        enabled = enabled,
        shape = RoundedCornerShape(LeafyRadius.shapeCornerExtraSmall),
        color = containerColor,
        contentColor = contentColor,
        shadowElevation = if (variant == LeafyButtonVariant.Elevated && enabled) LeafyElevation.elevation1 else 0.dp,
        border = border,
        interactionSource = interaction,
    ) {
        Row(
            modifier = Modifier.padding(
                start = if (leading) LeafySpacing.space3 else size.horizontalPadding,
                end = if (trailing) LeafySpacing.space3 else size.horizontalPadding,
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                if (trailing) LeafySpacing.space8 else LeafySpacing.space2,
                Alignment.CenterHorizontally,
            ),
        ) {
            if (leading) Icon(icon!!, contentDescription = null, modifier = Modifier.size(16.dp))
            Text(text, style = Leafy.typography.labelLarge, maxLines = 1)
            if (trailing) Icon(icon!!, contentDescription = null, modifier = Modifier.size(16.dp))
        }
    }
}
