# Progress

Shows how far a process has got, or that it is running.

- `type`: `linear` (default) or `circular` (48px). `value` (0 to 1) for determinate progress; leave it out for indeterminate.
- Linear takes Carbon's `label` (shown above with the percentage) and `helperText` (below), and `size`: `small` (4px, default) or `big` (8px). Square ends.
- Without `label`, give `aria-label`. `primary` on a `layer-accent` track.
- For content that is loading in place, prefer Skeleton.

## Sound

Silent unless you pass `sound`. With it, reaching 100% plays `complete`. It is off by default so it does not double up with the notification that usually reports the result.
