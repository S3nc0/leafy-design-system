package design.leafy.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import design.leafy.sound.LeafySoundEvent
import design.leafy.theme.Leafy
import design.leafy.theme.LeafyOpacity
import design.leafy.theme.LeafySpacing

// Material 3 selection controls with a label as one touch target and Leafy's sounds.
// Inside LeafyTheme the controls already take Leafy's colours and shapes.

/** A switch with its label; plays `toggle-on` and `toggle-off`. Use for settings that apply at once. */
@Composable
fun LeafySwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    sound: Boolean = true,
) {
    val sfx = Leafy.sound
    SelectionRow(
        modifier.toggleable(value = checked, enabled = enabled, role = Role.Switch) {
            if (sound) sfx.event(if (it) LeafySoundEvent.ToggleOn else LeafySoundEvent.ToggleOff)
            onCheckedChange(it)
        },
        label,
        enabled,
    ) {
        Switch(
            checked = checked,
            onCheckedChange = null,
            enabled = enabled,
            thumbContent = if (checked) {
                { Icon(LeafyIcons.Check, contentDescription = null, modifier = Modifier.size(SwitchDefaults.IconSize)) }
            } else {
                null
            },
        )
    }
}

/** A checkbox with its label; plays `check` and `uncheck`. Use for independent options in a list or form. */
@Composable
fun LeafyCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    sound: Boolean = true,
) {
    val sfx = Leafy.sound
    SelectionRow(
        modifier.toggleable(value = checked, enabled = enabled, role = Role.Checkbox) {
            if (sound) sfx.event(if (it) LeafySoundEvent.Check else LeafySoundEvent.Uncheck)
            onCheckedChange(it)
        },
        label,
        enabled,
    ) {
        Checkbox(checked = checked, onCheckedChange = null, enabled = enabled)
    }
}

/** A radio button with its label; choosing it plays `select`. Group several under a `selectableGroup()`. */
@Composable
fun LeafyRadio(
    selected: Boolean,
    onClick: () -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    sound: Boolean = true,
) {
    val sfx = Leafy.sound
    SelectionRow(
        modifier.selectable(selected = selected, enabled = enabled, role = Role.RadioButton) {
            if (sound && !selected) sfx.event(LeafySoundEvent.Select)
            onClick()
        },
        label,
        enabled,
    ) {
        RadioButton(selected = selected, onClick = null, enabled = enabled)
    }
}

@Composable
private fun SelectionRow(modifier: Modifier, label: String, enabled: Boolean, control: @Composable () -> Unit) {
    val c = Leafy.colors
    Row(
        modifier = modifier.padding(vertical = LeafySpacing.space1),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(LeafySpacing.space3),
    ) {
        control()
        Text(
            label,
            style = Leafy.typography.bodyMedium,
            color = if (enabled) c.onSurface else c.onSurface.copy(alpha = LeafyOpacity.disabledContent),
        )
    }
}
