# Menu

A temporary list of choices that opens from a button or icon button (M3 behaviour, Carbon overflow-menu density).

- `Menu` renders the surface (`layer-03`, `elevation-2`, 4px corners); fill it with `Leafy.MenuItem` and `Leafy.Divider`.
- `MenuItem`: `label`, `icon`, `trailing` (a shortcut), `onClick`, `disabled`. Rows are 40px.
- The consumer positions the menu, controls `open`, and closes it on selection, Escape and outside clicks.
- Put destructive items last, after a divider.

## Sound

Plays `open` when it appears and `close` when `open` turns false. Keep it mounted and drive `open`, or the close sound has nothing to play it.
