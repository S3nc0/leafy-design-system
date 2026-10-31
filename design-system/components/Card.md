# Card

A container for content and actions about one subject (M3), with 4px corners.

- Variants: `outlined` (default; `surface` with an `outline-variant` border), `filled` (`surface-container-highest`), `elevated` (`surface-container-low` + `elevation-1`). One variant per collection.
- Provide children. `.lf-card__body` gives 16px padding and an 8px gap; `.lf-card__media` a 16:9 image slot.
- `onClick` makes the whole card one button; then put no other buttons inside.
- In dense desktop layouts and dashboards, prefer Tile: flat, square and on the Carbon layer model.
