# ProgressIndicator

Shows the steps of a multi-step task and where the person is in it (Carbon).

- Provide `steps`: `[{ label, secondary?, state? }]` and `current` (index). States derive from `current` (complete, current, incomplete) unless a step sets `state`: `complete`, `current`, `incomplete`, `invalid`, `disabled`.
- `onChange(index)` makes completed and incomplete steps clickable to go back or skip ahead.
- Each step has a 2px line on top (`primary` up to the current step, `outline-variant` after) and a status icon; invalid steps use `error` and say why in `secondary`.
- `spaceEqually` stretches steps across the width; `vertical` stacks them for side panels.

## Sound

When `current` moves forward it plays `progress-step`, and moving past the last step plays `complete`. Going back is silent. Nothing plays on first render.
