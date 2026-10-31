# TextField

Lets people enter and edit text.

| Variant | Use for |
| --- | --- |
| `stacked` | Carbon's field, label above, `field-01` fill with a `border-strong` bottom line. The default for forms, tables, toolbars and dense desktop UIs. |
| `filled` | M3's floating-label field, squared. Touch-first or sparse forms. |
| `outlined` | M3's outlined field with 2px corners. Sparse forms on busy surfaces. |

- Use one variant per form. Provide `label` and `value` + `onChange` or `defaultValue`; native input props pass through.
- `stacked` takes `size` (`sm` 32px, `md` 40px default, `lg` 48px) and a real `placeholder` for an example value; never put the label in the placeholder.
- `supportingText` sits below; with `error` it becomes the message (say how to fix it), the box gets a 2px `error` line and stacked fields show a filled error icon.
- `leadingIcon` / `trailingIcon` take an icon name or node. On a layer, set `background` for the outlined notch, or use `field-02` behind stacked fields on `layer-02`.
- Focus draws the 2px `focus` line around the box.

## Sound

Silent. Keystrokes map to `sound-typing`, which is `none`: key clicks tire people within minutes.
