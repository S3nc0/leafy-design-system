# Snackbar

A brief, low-priority message about something the person just did (M3).

- Provide `message` (one line), optional `action` + `onAction` ("Undo"), and `onClose`.
- `inverse-surface` with an `inverse-primary` action, 2px corners.
- Bottom of the screen, one at a time, 4–10 seconds. For system status (errors, warnings), use a Notification toast instead.

## Sound

Plays `sound-snackbar` (the `notification` cue) as it appears.
