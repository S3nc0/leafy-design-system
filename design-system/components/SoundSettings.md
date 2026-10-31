# SoundSettings

The sound preference panel: a switch for interface sounds, a volume slider and a sample button. Every product that uses sound puts this in its settings, because sound is only acceptable when people can turn it off.

- It reads and writes the shared `Leafy.sound` preference (saved in localStorage under `leafy:sound`), so every SoundSettings on the page and every component stay in step.
- `title` (default "Interface sounds"), `description` (`false` hides it), `showVolume` (default true), `sampleCue` (default `success`), and `onChange({ enabled, volume })` to save the choice to the person's account.
- Turning sound on plays `toggle-on` as confirmation. Releasing the volume slider, or moving it with the arrow keys, plays `volume-change` at the new level. This is the only slider in the system that makes a sound.
- Sound starts on, at the `sound-volume` token (0.6). On shared screens, such as a greenhouse floor display, call `Leafy.sound.setEnabled(false)` once at start-up; people can still turn it on here.
- The switch and slider are the system's own, so focus, sizes and disabled states match the rest of the form.
