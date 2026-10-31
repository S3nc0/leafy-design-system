# Notification

Status messages about the system or a task: inline banners and toasts (Carbon), coloured with Verdant's status roles.

- `kind`: `info`, `success`, `warning`, `error`, each with its filled icon. Provide `title` (short, bold) and details as children.
- `variant`: `inline` (default; in the page flow, near what it is about) or `toast` (floating, top end of the window, 360px, with an optional `caption` timestamp; auto-hide info and success after 5–10 seconds, never errors).
- `contrast`: `low` (default; `*-container` fill with a 1px status border) or `high` (`inverse-surface` with a bright status icon, for alerts that must stand out).
- `onClose` adds a close button; `action` (`{ label, onClick }`) adds one short action. Errors and warnings announce with `role="alert"`, others with `role="status"`.
- Never colour alone: the icon and the title name the status.

## Sound

A toast plays its kind as it appears: `success`, `error`, `warning` or `info` (tokens `sound-notify-*`). Inline notifications are silent unless you pass `sound`, because they often load with the page. Never let sound be the only sign of an error: the icon, colour and text are always there.
