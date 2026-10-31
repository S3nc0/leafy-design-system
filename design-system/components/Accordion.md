# Accordion

A vertical stack of headings that each show or hide a panel of content (Carbon).

- Provide `items`: `[{ title, content, disabled? }]`, and `open` + `onOpenChange` (array of indices) or `defaultOpen`.
- The chevron sits at the end (`align="start"` moves it first). `size`: `sm` 32px, `md` 40px (default), `lg` 48px.
- Rows are separated by `border-subtle` lines; panels keep a 25% right margin so text lines stay readable.
- Use for secondary or reference content. Do not hide information people need to finish a task.

## Sound

Opening an item plays `expand`; closing it plays `collapse`.
