# Button

Buttons start an action; pick the variant by how important the action is, and the size by the density of the region.

| Variant | Use for | Carbon equivalent |
| --- | --- | --- |
| `filled` | The one main action in a view (Run, Save). At most one per view. | Primary |
| `inverse` | The partner of a filled button, e.g. "Save draft" beside "Publish". | Secondary |
| `tonal` | Important actions that should stand out less than filled. | — (M3) |
| `outlined` | Medium emphasis: actions that matter but are not the main one. | Tertiary |
| `text` | Lowest emphasis: Cancel, inline actions in tiles and tables. | Ghost |
| `elevated` | Like tonal, over images or busy backgrounds. | — (M3) |

- Provide the label as children: verb first, sentence case, one to three words.
- `size`: `sm` (32px, dense toolbars and tables), `md` (40px, default), `lg` (48px, forms and dialogs).
- `icon` adds a 16px icon. It trails the label with 32px of room (the Carbon layout) unless `iconPosition="start"`. Use IconButton for icon-only actions.
- `tone="danger"` turns filled, outlined and text buttons to the `error` role for destructive actions.
- Corners are `shape-corner-extra-small` (2px). Focus shows the 2px `focus` line inside the button with a `focus-inset` gap.
- Native button props pass through (`onClick`, `disabled`, `type`).

## Sound

Silent by default (`sound-press` is `none`): the outcome of the action makes the sound, such as a success toast, never the click itself. `sound={false}` keeps one button silent even if a product turns press sounds on.
