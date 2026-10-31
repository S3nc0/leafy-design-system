# CodeSnippet

Shows code, commands or machine values in IBM Plex Mono with a copy button (Carbon).

- Provide the code as a string child. `type`: `inline` (inside a sentence; the whole snippet copies on click), `single` (one line, 40px, scrolls sideways), `multi` (several lines, up to 240px tall, then scrolls).
- The copy button confirms with "Copied" for two seconds. If the clipboard is blocked, it selects the text instead.
- `layer-01` background (`layer-accent` inline). Use for values people copy; for emphasis in prose use plain `code-01`.

## Sound

A successful copy plays `copy`. If the clipboard is blocked and the text is only selected, it stays silent.
