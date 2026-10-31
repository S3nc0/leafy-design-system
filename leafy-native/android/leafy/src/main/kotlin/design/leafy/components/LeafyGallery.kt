package design.leafy.components

import androidx.compose.foundation.ExperimentalLayoutApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import design.leafy.sound.LeafyCue
import design.leafy.theme.Leafy
import design.leafy.theme.LeafySpacing
import design.leafy.theme.LeafyTheme

/**
 * Every Leafy component with greenhouse sample content. Show it in a debug screen, or open the
 * previews below in Android Studio, to check the theme on a device.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LeafyGallery(modifier: Modifier = Modifier) {
    val c = Leafy.colors
    var frostAlerts by remember { mutableStateOf(true) }
    var zoneB4 by remember { mutableStateOf(true) }
    var dripLine by remember { mutableStateOf(true) }
    var zoneName by remember { mutableStateOf("") }
    var moisture by remember { mutableStateOf("120") }
    Column(
        modifier = modifier.background(c.surface).verticalScroll(rememberScrollState()).padding(LeafySpacing.space4),
        verticalArrangement = Arrangement.spacedBy(LeafySpacing.space5),
    ) {
        Section("Buttons")
        FlowRow(horizontalArrangement = Arrangement.spacedBy(LeafySpacing.space2), verticalArrangement = Arrangement.spacedBy(LeafySpacing.space2)) {
            LeafyButton("Run irrigation", onClick = {}, icon = LeafyIcons.ArrowForward)
            LeafyButton("Save draft", onClick = {}, variant = LeafyButtonVariant.Inverse)
            LeafyButton("Calibrate", onClick = {}, variant = LeafyButtonVariant.Tonal)
            LeafyButton("Export CSV", onClick = {}, variant = LeafyButtonVariant.Outlined)
            LeafyButton("Cancel", onClick = {}, variant = LeafyButtonVariant.Text)
            LeafyButton("Delete zone", onClick = {}, danger = true)
        }

        Section("Tags")
        FlowRow(horizontalArrangement = Arrangement.spacedBy(LeafySpacing.space2), verticalArrangement = Arrangement.spacedBy(LeafySpacing.space2)) {
            LeafyTag("Healthy", type = LeafyTagType.Success, icon = LeafyIcons.CheckCircle)
            LeafyTag("Dry soil", type = LeafyTagType.Warning, icon = LeafyIcons.Warning)
            LeafyTag("Sensor offline", type = LeafyTagType.Error)
            LeafyTag("Zone B4", small = true, onRemove = {})
        }

        Section("Notifications")
        LeafyNotification(LeafyNotificationKind.Success, "Run complete", message = "Zone B4 watered for 12 minutes.", onClose = {})
        LeafyNotification(LeafyNotificationKind.Warning, "Soil is drying", message = "MS-105 reads 21%.", actionLabel = "Run now", onAction = {}, onClose = {})
        LeafyNotification(
            LeafyNotificationKind.Info, "Firmware 2.4 available", message = "Install it during the next quiet hour.",
            variant = LeafyNotificationVariant.Toast, caption = "Today, 08:40", onClose = {}, sound = false,
        )

        Section("Tiles")
        Row(horizontalArrangement = Arrangement.spacedBy(1.dp)) {
            LeafyTile(Modifier.weight(1f)) {
                Text("Soil moisture", style = Leafy.typography.titleSmall)
                Text("32%", style = Leafy.typography.displaySmall)
            }
            LeafyTile(selected = dripLine, onSelectedChange = { dripLine = it }, modifier = Modifier.weight(1f)) {
                Text("Drip line A", style = Leafy.typography.titleSmall)
            }
        }

        Section("Selection")
        LeafySwitch(frostAlerts, { frostAlerts = it }, "Frost alerts")
        LeafyCheckbox(zoneB4, { zoneB4 = it }, "Zone B4")

        Section("Text fields")
        LeafyTextField(zoneName, { zoneName = it }, "Zone name", placeholder = "e.g. B4 Tomatoes", helperText = "Shown on the floor plan")
        LeafyTextField(moisture, { moisture = it }, "Target moisture (%)", errorText = "Enter a value from 0 to 100")

        Section("Sound")
        LeafySoundSettings(sampleCue = LeafyCue.Complete)
    }
}

@Composable
private fun Section(title: String) {
    Text(title, style = Leafy.typography.titleMedium, color = Leafy.colors.onSurface, modifier = Modifier.padding(top = LeafySpacing.space2))
}

@Preview(name = "Light", widthDp = 400, heightDp = 1400)
@Composable
private fun LeafyGalleryLightPreview() {
    LeafyTheme(darkTheme = false, highContrast = false) { LeafyGallery() }
}

@Preview(name = "Dark", widthDp = 400, heightDp = 1400)
@Composable
private fun LeafyGalleryDarkPreview() {
    LeafyTheme(darkTheme = true, highContrast = false) { LeafyGallery() }
}

@Preview(name = "Dark high contrast", widthDp = 400, heightDp = 1400)
@Composable
private fun LeafyGalleryDarkHighContrastPreview() {
    LeafyTheme(darkTheme = true, highContrast = true) { LeafyGallery() }
}
