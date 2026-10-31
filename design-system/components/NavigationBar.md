# NavigationBar

Bottom navigation between three to five destinations on compact windows (M3).

- Provide `items`: `[{ icon, label, badge? }]` and `value` + `onChange` or `defaultValue`.
- The active destination gets a squared `secondary-container` indicator (4px corners) and a filled icon. Always show labels.
- Below 600px only. Switch to NavigationRail from `window-medium`, and to Header with SideNav from `window-large`.

## Sound

Silent (`sound-navigate`).
