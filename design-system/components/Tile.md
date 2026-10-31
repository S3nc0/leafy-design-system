# Tile

A flat, square block on `layer-01` for grouping content (Carbon). The building block of dashboards and data walls.

- `kind`: `base` (static, default), `clickable` (the whole tile is a link or button, with an arrow at the bottom right; pass `href` or `onClick`), `selectable` (toggles, with a checkbox icon; `selected` + `onSelectedChange` or `defaultSelected`).
- 16px padding, no corners, no shadow. A selected tile gets a 1px `on-surface` outline.
- Lay tiles out on the grid with `grid-gutter-condensed` (1px) for a data wall, or `grid-gutter-narrow` (16px) for separate blocks.
- A tile inside a tile uses `layer-02`.

## Sound

A selectable tile plays `select` and `deselect`. Base and clickable tiles are silent.
