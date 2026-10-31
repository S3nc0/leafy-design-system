# Breadcrumb

Shows where the current page sits in the hierarchy and links back up it (Carbon).

- Provide `items`: `[{ label, href?, onClick? }]` from the top level down. The last item is the current page (`aria-current`) and is not a link.
- Separated by "/" in `on-surface-variant`. Put it above the page title, never inside the app bar.
- Use for hierarchies three or more levels deep.
