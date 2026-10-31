# Skeleton

Placeholder shapes shown while content loads, so the layout does not jump (Carbon).

- `type`: `text` (14px line; `lines` for a paragraph whose last line is shorter), `heading` (24px, half width), `block` (a chart or image area), `circle` (an avatar).
- `width` and `height` override the defaults; match the real content's size.
- `skeleton-background` with a `skeleton-element` shimmer; the shimmer stops under reduced motion. Hidden from screen readers: announce loading elsewhere (e.g. `aria-busy` on the region).
