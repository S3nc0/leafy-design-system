# Dialog

A modal window for decisions that must be made before continuing: the M3 dialog, with Carbon's modal header and footer as options.

- Provide `open`, `headline`, body as children, and `actions` (Buttons, dismissive first). `icon` centres an M3 layout above the headline.
- Carbon options: `eyebrow` (a small label above the headline, e.g. the object's name), `closeButton` (an X at the top end; needs `onClose`), `actionsLayout="flush"` (full-width 64px buttons along the bottom edge; pair `inverse` with `filled` or `tone="danger"`).
- `onClose` fires on a scrim click or Escape. Move focus in on open and back to the trigger on close. `alert` for interruptions, `inline` to render without the scrim.
- `surface-container-high`, 8px corners, `elevation-3`, scrim at `scrim-opacity` (50%). Name the confirming action by its result ("Delete", not "OK").

## Sound

Plays `open` when `open` turns true and `close` when it turns false. Keep the dialog mounted and drive `open`, so the close sound can play. Inline dialogs (documentation) are silent.
