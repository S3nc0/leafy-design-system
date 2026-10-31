# SegmentedButton

Two to five related options in one control, for switching views or filtering. It covers Carbon's content switcher.

- Provide `options`: `[{ value, label, icon?, disabled? }]`, and `value` + `onChange` (controlled) or `defaultValue`.
- Single-select by default; `multiple` allows several (value is an array).
- A selected segment shows a check and the `secondary-container` fill. 40px tall, 2px corners, `outline` border.
- Give the group an `aria-label`.

## Sound

Choosing an option plays `select`. With `multiple`, clearing one plays `deselect`. Clicking the option that is already chosen is silent.
