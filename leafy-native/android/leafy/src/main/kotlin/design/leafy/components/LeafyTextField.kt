package design.leafy.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import design.leafy.sound.LeafySoundEvent
import design.leafy.theme.Leafy
import design.leafy.theme.LeafyOpacity
import design.leafy.theme.LeafyRadius
import design.leafy.theme.LeafySpacing

/**
 * Carbon's stacked text field in Leafy colours: label above, a `field-01` box with a bottom line,
 * and helper or error text below. Material's TextField also works inside LeafyTheme for the M3 look.
 *
 * @param errorText Puts the field in its error state and replaces the helper text. Say what to do: "Enter a value from 0 to 100".
 * @param sound Plays the `typing` event per change, which is silent by default.
 */
@Composable
fun LeafyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    helperText: String? = null,
    errorText: String? = null,
    size: LeafyControlSize = LeafyControlSize.Md,
    leadingIcon: ImageVector? = null,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    sound: Boolean = true,
) {
    val c = Leafy.colors
    val type = Leafy.typography
    val sfx = Leafy.sound
    val interaction = remember { MutableInteractionSource() }
    val focused by interaction.collectIsFocusedAsState()
    val hasError = errorText != null
    Column(
        modifier = modifier.alpha(if (enabled) 1f else LeafyOpacity.disabledContent),
        verticalArrangement = Arrangement.spacedBy(LeafySpacing.space2),
    ) {
        Text(label, style = type.bodySmall, color = c.onSurfaceVariant)
        BasicTextField(
            value = value,
            onValueChange = {
                if (sound) sfx.event(LeafySoundEvent.Typing)
                onValueChange(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .semantics {
                    contentDescription = label
                    if (errorText != null) error(errorText)
                },
            enabled = enabled,
            singleLine = singleLine,
            textStyle = type.bodyMedium.copy(color = c.onSurface),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = visualTransformation,
            interactionSource = interaction,
            cursorBrush = SolidColor(c.primary),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .height(size.height)
                        .background(c.field01, RoundedCornerShape(topStart = LeafyRadius.shapeCornerExtraSmall, topEnd = LeafyRadius.shapeCornerExtraSmall))
                        .drawBehind {
                            val line = 1.dp.toPx()
                            val y = this.size.height - line / 2
                            drawLine(c.borderStrong, Offset(0f, y), Offset(this.size.width, y), strokeWidth = line)
                        }
                        .then(
                            when {
                                hasError -> Modifier.border(2.dp, c.error)
                                focused -> Modifier.border(2.dp, c.focus)
                                else -> Modifier
                            },
                        )
                        .padding(horizontal = LeafySpacing.space4),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(LeafySpacing.space2),
                ) {
                    if (leadingIcon != null) Icon(leadingIcon, contentDescription = null, tint = c.onSurfaceVariant, modifier = Modifier.size(16.dp))
                    Box(Modifier.weight(1f)) {
                        if (value.isEmpty() && placeholder != null) Text(placeholder, style = type.bodyMedium, color = c.onSurfaceVariant)
                        innerTextField()
                    }
                    if (hasError) Icon(LeafyIcons.Error, contentDescription = null, tint = c.error, modifier = Modifier.size(16.dp))
                }
            },
        )
        val message = errorText ?: helperText
        if (message != null) Text(message, style = type.bodySmall, color = if (hasError) c.error else c.onSurfaceVariant)
    }
}
