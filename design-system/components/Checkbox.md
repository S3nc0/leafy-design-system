# Checkbox

Lets people select one or more items, or turn one option on or off in a form.

- Provide `label` and `checked` + `onChange` or `defaultChecked`. It is a native checkbox; all native props pass through.
- `indeterminate` shows a dash for a parent whose children are partly selected (table select-all).
- `error` draws the box in `error`; say what is wrong in text nearby.
- An 18px box with a 1px `on-surface` border and 2px corners (Carbon), `primary` when checked, inside a 40px target with an M3 state layer.

## Sound

Plays `check` and `uncheck` when a person changes it. A read-only checkbox (`checked` without `onChange`) stays silent. `sound={false}` silences one instance; DataTable does this for its row checkboxes and plays its own sounds.
