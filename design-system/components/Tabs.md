# Tabs

Switches between views at the same level.

- Provide `tabs`: `[{ label, icon? }]`, and `value` + `onChange` (index) or `defaultValue`.
- `primary` (M3, default): under the app bar for main views; icons fill when selected; 3px square `primary` indicator. `secondary` (M3): sub-views inside content. `contained` (Carbon): tabs as `layer-accent` blocks, the selected one on `layer-01` with a 2px `primary` top line, for tabs that sit on top of a tile or table.
- Arrow keys move between tabs. The consumer renders the panel.

## Sound

Silent. Switching uses `sound-tab-change`, which is `none`: the new panel is the feedback.
