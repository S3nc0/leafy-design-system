# Switch

Turns a single setting on or off, taking effect immediately (Carbon's toggle).

- Provide `label` and `checked` + `onChange` or `defaultChecked`. It is an `<input type="checkbox" role="switch">`.
- A 48 × 24px track: `outline` when off, `primary` when on, with an M3 check in the handle (`icons={false}` hides it). `labelFirst` puts the label before the track.
- Use when the change applies at once. When a Save step follows, use a Checkbox.

## Sound

Plays `toggle-on` and `toggle-off` (tokens `sound-toggle-on` and `sound-toggle-off`). A read-only switch stays silent. `sound={false}` silences one instance.
