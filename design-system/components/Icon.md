# Icon

A Material Symbols Sharp glyph at weight 300, named by its ligature: square corners and fine strokes, close to Carbon's icon style.

- Provide `name` (any Material Symbols name: `eco`, `water_drop`, `sensors`). `filled` switches to the filled style; use it for selected states and status icons, never for emphasis alone.
- Default size is 20px (`size` overrides). Components size their own: 16px in buttons, tags, chips and menus; 20px in fields, tables and navigation; 24px in the navigation bar and FAB.
- Icons inherit `currentColor`; colour them through the role around them (`on-surface-variant` at rest, `primary` or `on-secondary-container` when selected, a status colour in notifications).
- Decorative by default (`aria-hidden`). Give `label` when the icon alone carries meaning.
- The icon font loads from Google Fonts through `bundle.css`.
