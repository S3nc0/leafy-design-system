# SearchBar

A search field: Carbon's square field shape with M3's trailing slot for an avatar or action.

- Provide `placeholder` (also the accessible name unless `aria-label` is set) and `value` + `onValueChange` (a string) or `defaultValue`. `onChange` still receives the input event.
- A clear button appears once there is text. `leading` defaults to the search icon (`null` removes it); `trailing` takes an Avatar or IconButton.
- `size`: `lg` (48px, default, page search), `md` (40px), `sm` (32px, table toolbars).
- `field-01` fill with a `border-strong` bottom line, no corners.

## Sound

Clearing the search plays `sound-clear` (the `cancel` cue). Typing is silent (`sound-typing`).
