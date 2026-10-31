# Tag

A small read-only label for status, category or metadata (Carbon), coloured by role container.

- Provide the text as children (one or two words). `type`: `neutral` (default), `primary`, `secondary`, `tertiary`, `success`, `warning`, `error`, `info`.
- Status types must still say the status in words ("Dry soil"), and may add an `icon`; never rely on the colour.
- `size`: `md` (24px, default) or `sm` (18px, inside dense table rows). `onRemove` makes it dismissible (filter summaries).
- Tags do not take clicks; for a selectable or clickable label, use Chip.

## Sound

Removing a dismissible tag plays `sound-remove` (the `deselect` cue).
