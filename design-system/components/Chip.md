# Chip

Interactive compact elements for filtering, entering information or triggering actions (M3 behaviour, Carbon pill shape).

| Variant | Use for |
| --- | --- |
| `assist` | A smart action related to the content ("Set alert"). |
| `filter` | Toggling a filter; shows a check when selected. |
| `input` | A piece of entered information (a zone, a person); removable with `onRemove`. |
| `suggestion` | Generated suggestions. |

- Provide `label`, optionally `icon`. Filter chips take `selected` + `onSelectedChange` or `defaultSelected`.
- 32px tall, `shape-corner-full`, `outline-variant` border. For a read-only status label, use Tag instead.
- Lay chips out in a wrapping row with `space-2` gaps.

## Sound

Filter chips play `select` and `deselect`. Removing an input chip plays `sound-remove` (the `deselect` cue: removing a filter loses nothing). Assist and suggestion chips are silent.
