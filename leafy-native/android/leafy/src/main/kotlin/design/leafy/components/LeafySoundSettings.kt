package design.leafy.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import design.leafy.sound.LeafyCue
import design.leafy.theme.Leafy
import design.leafy.theme.LeafySpacing
import kotlin.math.roundToInt

/**
 * The sound preference panel: a switch for interface sounds, a volume slider and a sample button.
 * Every app that uses Leafy's sounds puts this in its settings.
 */
@Composable
fun LeafySoundSettings(
    modifier: Modifier = Modifier,
    title: String = "Interface sounds",
    description: String? = "Short Zen tones confirm the changes you make. Everything you hear is also shown on screen.",
    showVolume: Boolean = true,
    sampleCue: LeafyCue = LeafyCue.Success,
) {
    val c = Leafy.colors
    val type = Leafy.typography
    val sound = Leafy.sound
    Column(
        modifier = modifier.fillMaxWidth().background(c.layer01).padding(LeafySpacing.space5),
        verticalArrangement = Arrangement.spacedBy(LeafySpacing.space5),
    ) {
        Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(LeafySpacing.space3)) {
            Icon(
                if (sound.isEnabled) LeafyIcons.VolumeUp else LeafyIcons.VolumeOff,
                contentDescription = null,
                tint = c.primary,
                modifier = Modifier.size(20.dp),
            )
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(LeafySpacing.space1)) {
                Text(title, style = type.titleSmall, color = c.onSurface)
                if (description != null) Text(description, style = type.bodySmall, color = c.onSurfaceVariant)
            }
            Switch(
                checked = sound.isEnabled,
                onCheckedChange = { on ->
                    sound.setEnabled(on)
                    if (on) sound.play(LeafyCue.ToggleOn)
                },
                modifier = Modifier.semantics { contentDescription = title },
            )
        }
        if (showVolume) {
            Row(
                modifier = Modifier.padding(start = 20.dp + LeafySpacing.space3),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(LeafySpacing.space4),
            ) {
                Column(Modifier.weight(1f)) {
                    Text("Volume", style = type.bodySmall, color = c.onSurfaceVariant)
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(LeafySpacing.space3)) {
                        // Releasing the slider plays a tick at the new level.
                        Slider(
                            value = sound.volume,
                            onValueChange = sound::setVolume,
                            onValueChangeFinished = { sound.play(LeafyCue.VolumeChange) },
                            steps = 19,
                            enabled = sound.isEnabled,
                            modifier = Modifier.weight(1f).semantics { contentDescription = "Volume" },
                        )
                        Text(
                            "${(sound.volume * 100).roundToInt()}",
                            style = type.bodyMedium,
                            color = c.onSurface,
                            textAlign = TextAlign.End,
                            modifier = Modifier.width(32.dp),
                        )
                    }
                }
                LeafyButton(
                    "Play sample",
                    onClick = { sound.play(sampleCue) },
                    variant = LeafyButtonVariant.Text,
                    size = LeafyControlSize.Sm,
                    icon = LeafyIcons.PlayArrow,
                    iconPosition = LeafyIconPosition.Start,
                    enabled = sound.isEnabled,
                )
            }
        }
    }
}
