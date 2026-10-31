Leafy is Verdant M3 with Carbon's structure. It keeps Verdant M3's whole colour system (the green `#4CAF50` Tonal Spot scheme, every M3 role, four themes) and its component model (M3 variants, state layers, the 25 M3 components). On top of that it takes what IBM's Carbon Design System does best for dense, data-heavy products: IBM Plex type, crisp corners, fixed component sizes, the 2x grid, layers, the inset focus line, and Carbon's working components (data table, notifications, tiles, shell header and side nav, and more).

The rule of the blend: **Verdant M3 decides what a thing is and what colour it is; Carbon decides how it is set, sized and spaced.** When the two disagree, colour and behaviour follow M3, geometry and density follow Carbon.

| Area | From Verdant M3 | From Carbon |
| --- | --- | --- |
| Colour | All roles, palettes, four themes, `on-` pairs | Status colours (success, warning, info), layer and field names, `focus` / `focus-inset` |
| Type | The M3 scale and role names | IBM Plex Sans and Mono, Light display, SemiBold small titles, Carbon tracking |
| Shape | The M3 token names | Values squared to 0–4px on content and inputs |
| Space and size | 4px steps | 2px step, large layout steps, `size-xs` … `size-2xl`, the 2x grid |
| Interaction | State layers, disabled opacities | 2px inset focus line, productive motion |
| Components | 25 M3 components | 14 Carbon components, Carbon sizes and variants on the M3 ones |

Sound comes from a third source: the Zen pack of uisfx, wired into the components (see Sound).

## Content fundamentals

- Sentence case everywhere: titles, buttons, tabs, table headers ("Run irrigation", not "Run Irrigation").
- Productive voice: precise, short, factual. Lead with the object and the number: "Zone B4 moisture 21%", not "It looks like your soil might be getting dry".
- Address the person as "you"; the product does not say "I" or "we".
- Buttons start with a verb and name the result: "Delete zone", "Export CSV". Never "OK" or "Yes".
- Always give units and use the real ones: %, °C, minutes, kg. Put the unit in the column header or label ("Moisture (%)"), not in every cell.
- Errors say what happened and how to fix it: "Enter a value from 0 to 100", not "Invalid input".
- Numbers in tables and tiles use tabular figures and align to the end.
- No emoji in UI text. Use Material Symbols.
- Examples in this system come from a greenhouse operations console (zones, sensors, irrigation runs). Replace them with your own content.

## Colour

Colour is assigned by **role**, exactly as in Verdant M3. Each role is a pair: a fill and the `on-` colour for text and icons on it. Use the pair together.

- `primary` / `on-primary`: the main action (filled button), checked controls, the active step and tab line, current-item bars. `primary` is also the link colour (`link-primary`).
- `primary-container` / `on-primary-container`: soft brand fills (FAB, primary tags).
- `secondary-container` / `on-secondary-container`: selection. Selected rows, the current side-nav item, selected chips and segments, navigation indicators, tonal buttons.
- `tertiary` / `tertiary-container`: the cool blue-teal accent for information and data highlights. Tertiary is also the **focus** colour, so focus never looks like selection.
- `inverse-surface` / `inverse-on-surface`: the `inverse` button (Carbon's secondary), the inverse shell header, high-contrast notifications, snackbars and tooltips.
- `error` / `error-container`: errors and destructive actions only.
- Lines: `outline` (= `border-strong`) for borders that carry meaning (field lines, checkbox rings, the switch track). `outline-variant` (= `border-subtle`) for dividers and row lines.

**Status colours** come from Carbon's support set, toned exactly like the primary family in every theme and contrast level, so they meet the same contrast as M3 roles:

- `success`, `warning`, `info`, each with `on-`, `-container` and `on-…-container`. Use them in Notification, Tag and status icons only. `error` stays the M3 role. `inverse-success`, `inverse-warning`, `inverse-info` and `inverse-error` colour status icons on `inverse-surface` (high-contrast notifications).
- `success` sits close to the brand green. Never use it without the check-circle icon and a word ("Healthy", "Run complete").

**Layers.** Carbon builds depth by stacking flat layers instead of shadows. Here each Carbon layer name is an alias of an M3 surface container, with the same value, so you can think in either vocabulary:

| Carbon name | M3 role | Holds |
| --- | --- | --- |
| `surface` (background) | `surface` | The page |
| `layer-01` | `surface-container-low` | Tiles, data tables, side nav, code snippets |
| `layer-02` | `surface-container` | A tile in a tile, zebra rows, the table toolbar |
| `layer-03` | `surface-container-high` | Menus, the innermost layer |
| `layer-accent` | `surface-container-highest` | Table header row, contained tabs, inline code |
| `field-01` | `surface-container-highest` | Fields on `surface` and `layer-01` |
| `field-02` | `surface-container-lowest` | Fields on `layer-02` and `layer-03` |

Go up one layer for each level of nesting. Text uses `on-surface` and `on-surface-variant` on every layer; both pass 4.5:1 on all of them.

**Themes.** Light, Dark, Light high contrast and Dark high contrast, from Verdant M3. In the high-contrast themes every text pair reaches 7:1. Components read roles only, so they change theme with no extra work.

## Typography

IBM Plex Sans for everything (`--font-brand` and `--font-plain`), IBM Plex Mono for code, IDs and machine values (`--font-mono`). Both load from Google Fonts through `components/bundle.css`.

The scale keeps M3's five roles in three sizes, set to Carbon's metrics and weights:

- `display-*` (57 / 45 / 36px) in **Plex Light 300**: hero numbers and dashboard totals.
- `headline-*` (32 / 28 / 24px), Regular: page and dialog headlines.
- `title-large` (20px, Regular): shell and app bar titles, table titles. `title-medium` (16px) and `title-small` (14px) in **SemiBold 600**: tile and card titles, table column headers, notification titles, accordion and side-nav labels.
- `body-large` (16 / 24), `body-medium` (14 / 20, the default), `body-small` (12 / 16): running text, table cells, helper text.
- `label-large` (14 / 18, Medium): buttons, tabs, chips. `label-medium` (12 / 16, Regular): field labels above inputs, tags, navigation labels. `label-small` (11px SemiBold): badges.
- `code-01` (12px) and `code-02` (14px) in Plex Mono.

Tracking follows Carbon: 0.16px at 14px, 0.32px at 12px, none at 16px and up. Keep body lines near 60–75 characters.

## Shape

The M3 shape tokens keep their names, with values squared toward Carbon:

- `shape-corner-none` (0): tiles, tables, fields (filled and stacked), search, the shell, tabs, notifications.
- `shape-corner-extra-small` (2px): buttons, icon buttons, segmented buttons, outlined fields, checkboxes, tooltips, snackbars.
- `shape-corner-small` (4px): cards, menus, the small FAB, navigation indicators.
- `shape-corner-medium` (8px) and `-large` (12px): dialogs and FABs, the floating surfaces.
- `shape-corner-full`: only status and selection pills (chips, tags, badges, switches, radio buttons, slider handles).

Content and inputs are crisp. Something round is either a status, a selection, or a person.

## Elevation

Flat first. Use layers for depth, and cast a shadow only on things that float above the page: menus (`elevation-2`), dialogs, snackbars, toasts and FABs (`elevation-3`), FAB hover (`elevation-4`). Tiles, tables, cards and buttons sit at `elevation-0`. The `elevated` card and button variants exist for photos and busy backgrounds only.

## Spacing, sizes and grid

- **Spacing** steps by 4px (`space-1` … `space-12`), with Carbon's 2px step (`space-half`) and large layout steps (`space-16`, `-20`, `-24`, `-40`). Each usage note gives the Carbon equivalent.
- **Sizes**: every control height comes from `size-xs` (24) · `size-sm` (32) · `size-md` (40, default) · `size-lg` (48) · `size-xl` (64) · `size-2xl` (80). Pick one size per region and keep it: a table toolbar's search, buttons and rows should share a height.
- **Grid**: Carbon's 2x grid inside M3's window classes. 4 columns below 600px, 8 columns from 600px, 16 from 1200px (`grid-columns-*`). Gutters: `grid-gutter` (32px) for text-led pages, `grid-gutter-narrow` (16px) for dense dashboards, `grid-gutter-condensed` (1px) for tile walls. Cap content at `grid-max-width` (1584px).
- **Navigation by window class**: compact uses NavigationBar and TopAppBar; medium and expanded use NavigationRail; large and up use Header with SideNav.
- Touch targets stay at least 48 × 48px on touch layouts; on desktop, 32px controls are fine in dense regions.

## States and focus

- **State layers** (M3): hover, focus and press tint any element with its own content colour at `state-hover` (8%), `state-focus` (10%), `state-pressed` (12%). The `lf-state` class does this.
- **Focus** (Carbon): a 2px `focus` line drawn inside the component, with a 1px `focus-inset` gap so it shows on any fill (`lf-focus`). Round controls (checkbox, radio, switch, chips) draw it 1px outside. `focus` reaches 3:1 against every surface and layer in every theme.
- **Selected**: `secondary-container`, plus a 3px `primary` bar where Carbon uses one (side nav, contained tabs, step lines).
- **Disabled**: `on-surface` at `disabled-container` (12%) for fills and `disabled-content` (38%) for content, with a not-allowed cursor. No state layer.
- **Invalid**: a 2px `error` line around the field, a filled error icon, and the message below in `error`.

## Motion

Motion is not stored as tokens; use Carbon's curves and durations.

- **Productive** `cubic-bezier(0.2, 0, 0.38, 0.9)` for everyday UI: state layers, toggles, chevrons, menus. **Expressive** `cubic-bezier(0.4, 0.14, 0.3, 1)` for moments that deserve attention: a dialog entering, a toast arriving, a page transition.
- Durations: fast-01 70ms (micro), fast-02 110ms (state changes, toggles), moderate-01 150ms (menus, tooltips), moderate-02 240ms (progress, expanding panels), slow-01 400ms (dialogs, side nav), slow-02 700ms (large transitions).
- Honour `prefers-reduced-motion`: drop movement and shimmer, keep fades short.

## Sound

Leafy speaks with the **Zen pack of [uisfx](https://uisfx.com)**: pure sine tones with a touch of dry wood and washi paper, short and dry. It suits a calm operations tool, where you hear that something changed without anything competing for attention. Nothing is downloaded: the runtime in `bundle.js` synthesizes each sound in the browser the first time it plays and caches it.

**Principles**

1. **Sound confirms a change.** Something the person did (toggled, selected, opened, copied) or must notice (a run finished, a sensor failed). It never decorates.
2. **Movement and input are silent.** Hover, focus, typing, tabs, navigation, pagination, scrolling and sliders make no sound. A button press makes no sound either; its outcome does.
3. **One sound per action.** Composite components silence their parts (a DataTable row's checkbox), so one click is heard once. The player plays at most four sounds at a time (`sound-max-voices`) and restarts a repeated cue instead of stacking it.
4. **Never the only signal.** Every sound repeats something visible: a state, an icon, a word. Errors keep their icon, colour, text and `role="alert"`.
5. **People are in charge.** Nothing plays before their first tap or key press. The device volume applies, and SoundSettings gives a lasting on/off switch and a volume control.

**Component sounds.** Each event is a token in the `sound` family, read at play time from `tokens.css`:

| Token | Zen cue | Played by |
| --- | --- | --- |
| `sound-toggle-on`, `sound-toggle-off` | `toggle-on`, `toggle-off` | Switch, toggle IconButton |
| `sound-check`, `sound-uncheck` | `check`, `uncheck` | Checkbox |
| `sound-select`, `sound-deselect` | `select`, `deselect` | Radio, filter Chip, SegmentedButton, selectable Tile, DataTable rows |
| `sound-sort` | `reorder` | DataTable sort |
| `sound-open`, `sound-close` | `open`, `close` | Dialog, Menu |
| `sound-expand`, `sound-collapse` | `expand`, `collapse` | Accordion, SideNav groups |
| `sound-notify-success` … `-info` | `success`, `error`, `warning`, `info` | Notification toasts (inline ones with `sound`) |
| `sound-snackbar` | `notification` | Snackbar |
| `sound-remove` | `deselect` | Tag and input Chip removal |
| `sound-copy` | `copy` | CodeSnippet |
| `sound-clear` | `cancel` | SearchBar clear |
| `sound-step`, `sound-complete` | `progress-step`, `complete` | ProgressIndicator; Progress with `sound` |
| `sound-press`, `sound-typing`, `sound-tab-change`, `sound-navigate` | `none` | Buttons, fields, Tabs, navigation |

Override a token to change a sound: `:root { --sound-snackbar: none; }` silences snackbars, and `--sound-press: press` would turn press sounds on. `sound={false}` silences a single component.

**Outcomes your product plays.** Components cannot know what an action meant, so product code plays the result:

- A save or run succeeded: show a toast Notification, which plays `success`. If the confirmation is inline, call `Leafy.sound.play('success')`.
- A destructive action confirmed: `delete`. An undo: `undo`.
- Work the person is waiting on and can see: start `const job = Leafy.sound.play('processing')`, call `job?.stop()` the moment the spinner disappears, then play the outcome. Never loop for background sync.
- Messages and alerts that arrive while someone works: `receive`, `notification`, `mention`. Debounce bursts so ten sensor alerts make one sound.
- Leave the reward and commerce cues alone unless the product really has those moments.

```js
const { sound } = window.Leafy;
sound.play('success');                // any of the 78 uisfx cues
sound.event('notify-error');          // the cue a component event maps to
const job = sound.play('processing'); // loops return a handle
job?.stop();
sound.setEnabled(false);              // remembered in localStorage ('leafy:sound')
```

The volume starts at `sound-volume` (0.6) and each cue keeps uisfx's own relative level; do not boost single cues. The same Zen sounds ship as MP3 and Ogg files in the uisfx npm package (`sounds/zen/<cue>.mp3`) for native apps, product videos and places without Web Audio. The inlined runtime carries all twelve uisfx packs, because it is the library unchanged, but Leafy uses Zen only.

## Iconography

- Material Symbols **Sharp**, weight 300, grade 0, optical size 20, from Google Fonts through `bundle.css`. The sharp corners and fine strokes sit close to Carbon's icon style. Use `Leafy.Icon` with the symbol name (`sensors`, `water_drop`, `thermostat`).
- 16px in buttons, tags, chips, menus and links; 20px in fields, tables and navigation; 24px in the navigation bar and FAB.
- Outlined at rest, filled when selected and in status icons (notifications, step states).
- Icons take the colour of the role around them. No multicolour icons, no emoji.
- No logo ships with this system. Set the product name in the Header (`prefix` in SemiBold, `name` in Regular) until a mark is supplied.

## Accessibility

- Every role pair meets WCAG 2 AA (4.5:1 text, 3:1 large text, borders and focus) in Light and Dark, and AAA (7:1) in the high-contrast themes. That includes the status colours on every surface and layer, and `focus` against every fill it sits beside.
- Never use colour alone: status carries an icon and a word, inline links are underlined, selection shows a check, a filled icon or a bar.
- Icon-only controls (IconButton, header actions, the copy button) always have an accessible name.
- Use the real controls: Checkbox, Radio, Switch, Slider and Select wrap native inputs; Tabs move with arrow keys; Accordion and SideNav groups use `aria-expanded`; DataTable sets `aria-sort` and `aria-selected`.
- Errors and warnings announce with `role="alert"`; other notifications with `role="status"`.
- Sound only repeats what is on screen, never starts before the first interaction, and can always be turned off in SoundSettings.

## Components

Forty-one components. From M3 (restyled with Carbon type, shape and sizes): Button, IconButton, FAB, SegmentedButton, Chip, Badge, Checkbox, Radio, Switch, Slider, TextField, SearchBar, Card, Divider, List, Menu, Tabs, TopAppBar, NavigationBar, NavigationRail, Dialog, Snackbar, Tooltip, Progress and Icon. From Carbon: Link, Tag, Select, Tile, Accordion, DataTable, Pagination, CodeSnippet, Header, SideNav, Breadcrumb, ProgressIndicator, Notification and Skeleton. For sound: SoundSettings and SoundCue, plus the shared `Leafy.sound` player. Several M3 components also gained Carbon options: Button `inverse` and sizes, TextField `stacked`, Tabs `contained`, Dialog `eyebrow`, `closeButton` and flush actions, Progress labels. `List` and `Menu` also export `ListItem`, `Avatar` and `MenuItem`. Each component has a README with when to use it and what to provide.

Components are React 18 function components on `window.Leafy`, styled by `components/bundle.css` (class prefix `lf-`) through the tokens' CSS variables. Load `tokens.css`, then `bundle.css`, then React 18, then `bundle.js`.

Carbon Design System is IBM's open-source design system; Leafy borrows its principles and patterns but is not affiliated with IBM. IBM Plex is open source under the SIL Open Font License. uisfx is by Yuki Capital: its code is MIT licensed (the licence is kept in `bundle.js`) and its sounds are dedicated to the public domain under CC0 1.0.
