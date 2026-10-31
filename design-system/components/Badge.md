# Badge

A small marker on an icon showing a count, or that something is new (M3).

- Wrap the anchor (usually an Icon) as children; without children it renders inline.
- No `count`: a 6px dot. With `count`: a 16px pill; values over `max` (default 999) show as "999+".
- Always `error` on `on-error`. Give `aria-label` with the meaning ("3 new alerts").
- Use on navigation destinations and header actions.
