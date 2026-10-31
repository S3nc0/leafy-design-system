# Select

A native dropdown for choosing one option from a list, styled as a stacked field (Carbon).

- Provide `label`, `options` (strings, or `{ value, label, disabled? }`), and `value` + `onChange` or `defaultValue`. It wraps a real `<select>`, so keyboard and screen-reader behaviour are native.
- `size`: `sm` / `md` / `lg` like TextField. `inline` drops the fill for use inside toolbars and sentences.
- `supportingText` and `error` work as on TextField.
- Use for five or more options; for fewer, show Radio buttons.
