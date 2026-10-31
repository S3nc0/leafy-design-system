# FAB

The floating action button holds the single most important constructive action on a touch screen (M3).

- One FAB per screen, for create, add or compose. Never for destructive or minor actions. On desktop layouts with a shell header, prefer a filled Button in the page header.
- Sizes: `small` (40px, 4px corners), `regular` (56px, 8px), `large` (96px, 12px).
- `label` makes it extended (icon plus text). `color`: `primary` (default, `primary-container`), `secondary`, `tertiary`, `surface`.
- Provide `aria-label` on icon-only FABs. It is one of the few elements that casts a shadow (`elevation-3`).

## Sound

Silent by default, like Button (`sound-press`).
