package design.leafy.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import design.leafy.sound.LeafySoundEvent
import design.leafy.theme.Leafy
import design.leafy.theme.LeafyElevation
import design.leafy.theme.LeafyRadius
import design.leafy.theme.LeafySize
import design.leafy.theme.LeafySpacing

enum class LeafyNotificationKind(internal val icon: ImageVector, internal val label: String, internal val event: LeafySoundEvent) {
    Info(LeafyIcons.Info, "Information", LeafySoundEvent.NotifyInfo),
    Success(LeafyIcons.CheckCircle, "Success", LeafySoundEvent.NotifySuccess),
    Warning(LeafyIcons.Warning, "Warning", LeafySoundEvent.NotifyWarning),
    Error(LeafyIcons.Error, "Error", LeafySoundEvent.NotifyError),
}

enum class LeafyNotificationVariant {
    /** A banner in the page, beside what it is about. */
    Inline,

    /** A floating message that arrives from elsewhere (360dp wide, elevated). */
    Toast,
}

/**
 * Status messages about the system or a task (Carbon's notification, in Leafy's status roles).
 * Warnings and errors announce assertively to TalkBack; info and success politely.
 *
 * @param sound Toasts play their kind as they appear; inline notifications only when this is true,
 *   because they often load with the screen.
 */
@Composable
fun LeafyNotification(
    kind: LeafyNotificationKind,
    title: String,
    modifier: Modifier = Modifier,
    message: String? = null,
    variant: LeafyNotificationVariant = LeafyNotificationVariant.Inline,
    highContrast: Boolean = false,
    caption: String? = null,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
    onClose: (() -> Unit)? = null,
    sound: Boolean = variant == LeafyNotificationVariant.Toast,
) {
    val c = Leafy.colors
    val type = Leafy.typography
    val sfx = Leafy.sound
    val roles = when (kind) {
        LeafyNotificationKind.Info -> Roles(c.info, c.infoContainer, c.onInfoContainer, c.inverseInfo)
        LeafyNotificationKind.Success -> Roles(c.success, c.successContainer, c.onSuccessContainer, c.inverseSuccess)
        LeafyNotificationKind.Warning -> Roles(c.warning, c.warningContainer, c.onWarningContainer, c.inverseWarning)
        LeafyNotificationKind.Error -> Roles(c.error, c.errorContainer, c.onErrorContainer, c.inverseError)
    }
    val toast = variant == LeafyNotificationVariant.Toast
    val urgent = kind == LeafyNotificationKind.Warning || kind == LeafyNotificationKind.Error
    LaunchedEffect(Unit) {
        if (sound) sfx.event(kind.event)
    }
    Surface(
        modifier = modifier
            .then(if (toast) Modifier.widthIn(max = 360.dp) else Modifier.fillMaxWidth())
            .semantics { liveRegion = if (urgent) LiveRegionMode.Assertive else LiveRegionMode.Polite },
        shape = RectangleShape,
        color = if (highContrast) c.inverseSurface else roles.container,
        contentColor = if (highContrast) c.inverseOnSurface else roles.onContainer,
        border = if (highContrast) null else BorderStroke(1.dp, roles.accent.copy(alpha = 0.5f)),
        shadowElevation = if (toast) LeafyElevation.elevation3 else 0.dp,
    ) {
        Row(
            modifier = Modifier.heightIn(min = LeafySize.sizeLg).padding(start = LeafySpacing.space4),
            horizontalArrangement = Arrangement.spacedBy(LeafySpacing.space4),
        ) {
            Icon(
                kind.icon,
                contentDescription = kind.label,
                tint = if (highContrast) roles.inverse else roles.onContainer,
                modifier = Modifier.padding(top = 14.dp).size(20.dp),
            )
            Column(Modifier.weight(1f).padding(top = 15.dp, bottom = if (toast) LeafySpacing.space5 else 15.dp)) {
                if (toast) {
                    Text(title, style = type.bodyMedium, fontWeight = FontWeight.SemiBold)
                    if (message != null) Text(message, style = type.bodyMedium, modifier = Modifier.padding(top = LeafySpacing.spaceHalf))
                    if (caption != null) Text(caption, style = type.bodySmall, modifier = Modifier.padding(top = LeafySpacing.space4).alpha(0.86f))
                } else {
                    Text(
                        buildAnnotatedString {
                            withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) { append(title) }
                            if (message != null) append(" $message")
                        },
                        style = type.bodyMedium,
                    )
                }
            }
            if (actionLabel != null && onAction != null) {
                val shape = RoundedCornerShape(LeafyRadius.shapeCornerExtraSmall)
                Box(
                    modifier = Modifier
                        .padding(vertical = LeafySpacing.space2)
                        .height(LeafySize.sizeSm)
                        .clip(shape)
                        .border(1.dp, LocalContentColor.current, shape)
                        .clickable(role = Role.Button, onClick = onAction)
                        .padding(horizontal = LeafySpacing.space4),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(actionLabel, style = type.labelLarge, maxLines = 1)
                }
            }
            if (onClose != null) {
                IconButton(onClick = onClose, modifier = Modifier.size(LeafySize.sizeLg)) {
                    Icon(LeafyIcons.Close, contentDescription = "Close notification", modifier = Modifier.size(20.dp))
                }
            } else {
                Spacer(Modifier.width(LeafySpacing.space4))
            }
        }
    }
}

private class Roles(val accent: Color, val container: Color, val onContainer: Color, val inverse: Color)
