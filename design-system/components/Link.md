# Link

Text that navigates to another page or resource (Carbon).

- Provide `href` and the text as children. Name the destination, not "click here".
- Standalone links have no underline until hover; `inline` links inside sentences are always underlined so they are not told apart by colour alone.
- `icon` adds a trailing 16px icon: `arrow_forward` for a next step, `open_in_new` for another site.
- `size`: `sm` (12px), `md` (14px), `lg` (16px); without it the link inherits the surrounding text size.
- `link-primary` at rest, `link-primary-hover` on hover. Use a Button, not a Link, for actions that change data.
