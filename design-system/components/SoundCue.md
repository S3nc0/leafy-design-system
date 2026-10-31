# SoundCue

A button that plays one Zen cue and names it. Use it on sound settings pages, in onboarding ("hear what a finished run sounds like") and in documentation.

- `cue`: any uisfx cue name (`success`, `toggle-on`, `processing`…). The label and description come from the uisfx catalog; `label` and `description` override them, and `description={false}` hides the description.
- One-shots play once, and the icon turns into a level meter while they ring. Loop cues (`loading`, `processing`, `recording`, `connecting`, `scanning`, `streaming`) start on the first click and stop on the second, with `aria-pressed`.
- It follows the sound preference: while interface sounds are off it shows a muted icon and stays silent.
- The mono cue name lets engineers match what they hear to `Leafy.sound.play(name)`.
- Lay several out as a tile wall: a grid with 1px gaps over `surface`, like the condensed Carbon grid.
