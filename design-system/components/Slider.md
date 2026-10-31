# Slider

Selects a value from a range by dragging a handle.

- Provide `min`, `max`, `step`, and `value` + `onChange` (a number) or `defaultValue`.
- Give `label` with an `id`, or `aria-label`. `showRange` prints min and max at the ends; `showValue` shows the value in a field-styled box (Carbon).
- A 2px track (`primary` filled, `outline` rest) with a 16px handle.
- For exact numbers, use a TextField.

## Sound

Silent. Continuous controls never make sound, because they would chatter through every step. The volume slider in SoundSettings is the one exception, and it plays only when released.
