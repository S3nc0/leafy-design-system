# IconButton

A square, icon-only button for toolbars, table rows, app bars and tiles.

- Always provide `aria-label` naming the action ("Run irrigation"); there is no visible text.
- Variants by emphasis: `standard` (default), `outlined`, `tonal`, `filled`.
- `size`: `sm` (32px), `md` (40px, default), `lg` (48px). Match the height of the buttons beside it.
- Pass `selected` (true or false) to make it a toggle: it gets `aria-pressed` and the icon fills when selected.
- 2px corners. Pair with a Tooltip so mouse users can read the label.

## Sound

A toggle IconButton with `onClick` plays `toggle-on` or `toggle-off`. Other icon buttons use `sound-press`, which is silent by default. A toggle without `onClick` cannot change, so it stays silent.
