# Leafy for native apps

Leafy's design system for Android (Jetpack Compose, Kotlin) and iOS (SwiftUI). Both packages are generated from the same `tokens/tokens.json` as the web system, so colour roles, type, spacing, sizes, corners and the Zen sound map stay identical across platforms.

![Leafy components in SwiftUI, light theme](docs/swiftui-light.png)

## What's here

```
leafy-native/
├── tokens/
│   ├── tokens.json            the Leafy tokens, exported from the design system
│   └── zen-cues.json          the 78 uisfx Zen cues (names, categories, loops)
├── tools/
│   ├── generate.mjs           regenerates the Kotlin and Swift token files
│   └── woff-to-sfnt.cjs       turns IBM's WOFF fonts into the TTFs both apps bundle
├── android/leafy/             Android library module (Jetpack Compose + Material 3)
├── ios/LeafyDesign/           Swift package (SwiftUI, iOS 16+, macOS 13+)
└── docs/                      reference renders of the SwiftUI components
```

Each platform gets:

- **Theme:** all four themes (Light, Dark, Light high contrast, Dark high contrast), every M3 colour role plus Leafy's status, layer, field, border, focus and link roles.
- **Type:** the 15 Material 3 styles in IBM Plex, plus the two code styles. Plex Sans and Mono are bundled.
- **Tokens:** spacing, control sizes, corners, opacities, elevation, grid and breakpoints, and Carbon's motion curves.
- **Sound:** a player for the Zen pack's 78 sounds, with the same component-event map as the web, a saved on/off and volume preference, and a voice limit of 4.
- **Components:** the pieces Material and SwiftUI don't already provide, listed below.

## Android: Jetpack Compose

**1. Add the module.** Copy `android/leafy` into your project, then:

```kotlin
// settings.gradle.kts
include(":leafy")

// app/build.gradle.kts
dependencies { implementation(project(":leafy")) }
```

It needs Kotlin 2.0+, the Compose compiler plugin and AGP 8. It targets the Compose BOM `2024.12.01` (Material 3 1.3); change it in `android/leafy/build.gradle.kts` to match your app's BOM.

**2. Wrap your UI in `LeafyTheme`.**

```kotlin
setContent {
    LeafyTheme {
        GreenhouseApp()
    }
}
```

It follows dark mode, and on Android 14+ it also follows the system contrast setting (medium or high picks the high-contrast themes). You can force either with `darkTheme` and `highContrast`.

**3. Use it.**
- **Material 3 components** inside `LeafyTheme` already use Leafy's colours, type and corners.
- **Leafy components** cover the rest:
  ```kotlin
  LeafyButton("Run irrigation", onClick = ::run, icon = LeafyIcons.ArrowForward)
  LeafyNotification(LeafyNotificationKind.Warning, "Soil is drying", message = "MS-105 reads 21%.")
  Text("Zone B4", style = Leafy.typography.titleSmall, color = Leafy.colors.onSurfaceVariant)
  Spacer(Modifier.height(LeafySpacing.space4))
  ```

**4. Fonts.** IBM Plex is bundled in the module's `res/font` and is the default, so there's nothing to set up:
- Sans in Light, Regular, Medium, SemiBold and Bold.
- Mono in Regular and SemiBold.

You can use the families directly as `LeafyPlex.Sans` and `LeafyPlex.Mono`. If an app must use the device fonts instead, pass `LeafyTheme(fonts = LeafyFonts.System)`. Either way, list IBM Plex (SIL Open Font License, text in `android/leafy/IBM-Plex-OFL.txt`) in your app's open-source notices.

**Status:** written for Compose and Material 3 1.3, but **not compiled yet**. This Mac has no Android SDK or Kotlin compiler. Build it once in Android Studio and open `LeafyGallery.kt`'s previews to check every component in three themes.

## iOS: SwiftUI

**1. Add the package.** In Xcode, choose File › Add Package Dependencies › Add Local…, then pick `ios/LeafyDesign`. You can also push that folder to its own Git repository and add it by URL.

**2. Apply `.leafyTheme()` once at the root.**

```swift
import LeafyDesign

@main
struct GreenhouseApp: App {
    var body: some Scene {
        WindowGroup {
            ContentView().leafyTheme()
        }
    }
}
```

It follows Dark Mode and Increase Contrast (Increase Contrast picks the high-contrast themes). To force a theme, pass one, for example `.leafyTheme(.dark)`.

**3. Use it.**

```swift
@Environment(\.leafyColors) private var colors

LeafyButton("Run irrigation", systemImage: "arrow.right") { run() }
Button("Export CSV") { export() }.buttonStyle(.leafy(.outlined, size: .sm))
Toggle("Frost alerts", isOn: $frostAlerts).toggleStyle(.leafySwitch)
Text("Zone B4").leafyFont(.titleSmall).foregroundStyle(colors.onSurfaceVariant)
```

- **Type:** every style scales with Dynamic Type.
- **UIKit:** `UIColor.leafy(\.primary)` gives a dynamic colour for appearance APIs.
- **Icons:** SF Symbols, the platform's own set (Android uses Material Sharp shapes).

**4. Fonts.** IBM Plex is bundled in the package (the same seven faces as Android) and registers itself the first time a Leafy style is used. No Info.plist entry is needed.
- **UIKit:** if UIKit code asks for Plex with `UIFont(name:size:)` before any Leafy view appears, call `LeafyFonts.registerBundledFonts()` at launch. IBM's files use short PostScript names: `IBMPlexSans-Light`, `IBMPlexSans`, `IBMPlexSans-Medm`, `IBMPlexSans-SmBld`, `IBMPlexSans-Bold`, `IBMPlexMono` and `IBMPlexMono-SmBld`.
- **Other weights:** a weight Leafy doesn't ship (Thin, for example) falls back to SF Pro.
- **Licence:** list IBM Plex in your app's acknowledgements (SIL Open Font License, text in `Resources/Fonts/IBM-Plex-OFL.txt`).

**Status:** builds for iOS 16+ and macOS 13+ with Xcode 27.1, with no warnings. The components were rendered and checked in Light, Dark and Dark high contrast (see `docs/`). Open `LeafyGalleryView` in Xcode's preview canvas to see them yourself.

## Components

| Leafy | Compose | SwiftUI | Sound it plays |
| --- | --- | --- | --- |
| Button (6 variants, 3 sizes, danger) | `LeafyButton` | `LeafyButton`, `.buttonStyle(.leafy(...))` | `press`, silent by default |
| Tag (8 types, dismissible) | `LeafyTag` | `LeafyTag` | `remove` |
| Notification (inline, toast, high contrast) | `LeafyNotification` | `LeafyNotification` | Its kind, for toasts; inline only with `sound` |
| Tile (base, clickable, selectable) | `LeafyTile` | `LeafyTile` | `select` and `deselect` |
| Text field (Carbon stacked) | `LeafyTextField` | `LeafyTextField` | `typing`, silent by default |
| Switch | `LeafySwitch` | `.toggleStyle(.leafySwitch)` | `toggle-on` and `toggle-off` |
| Checkbox | `LeafyCheckbox` | `.toggleStyle(.leafyCheckbox)` | `check` and `uncheck` |
| Radio | `LeafyRadio` | Use `Picker` (iOS has no radio button) | `select` |
| Sound settings | `LeafySoundSettings` | `LeafySoundSettings` | Sample and volume tick |
| All of the above | `LeafyGallery` | `LeafyGalleryView` | |

For everything else, use the platform's own components inside the theme:
- **Android:** Material 3's dialogs, menus, navigation bar and rail, tabs, lists, progress and snackbar take Leafy's roles and corners automatically.
- **iOS:** use `List`, `NavigationSplitView`, `TabView`, `.sheet` and `ProgressView`, with `.leafyTheme()`'s tint.

The web system's data table, side nav and header are web-shaped patterns, so they weren't ported.

## Sound

Both platforms play the uisfx **Zen** sounds (CC0) from files: `.ogg` in Android's `res/raw`, `.mp3` in the Swift package. The sound rules are the same as on the web:

1. **Sound confirms a change**, something the person did or must notice. It never decorates.
2. **Movement and input are silent:** typing, navigation, tabs, scrolling, sliders and button presses. The outcome of an action makes the sound, not the press.
3. **One sound per action.** At most 4 play at once, and a repeated cue restarts instead of stacking.
4. **Sound is never the only signal.** Every sound repeats something visible.
5. **People are in charge.** Put `LeafySoundSettings` in your settings screen.
   - On iOS, sounds respect the silent switch and mix with the person's music: Leafy uses the ambient audio session unless your app has set its own.
   - On Android they play as sonification audio.

Product code plays outcomes:

```kotlin
Leafy.sound.play(LeafyCue.Success)                 // a save worked
val job = Leafy.sound.play(LeafyCue.Processing)    // work the person is waiting on
job?.stop()                                        // stop it when the spinner goes away
```

```swift
LeafySound.shared.play(.success)
let job = LeafySound.shared.play(.processing)
job?.stop()
```

## Updating from the design system

The token files are generated, so don't edit them by hand. These are:
- `LeafyColors`, `LeafyTypography`, `LeafyTokens` and `LeafySoundCues` (Kotlin)
- `Generated/*.swift`

When Leafy's tokens change, export `tokens.json` again into `tokens/` and run:

```bash
node tools/generate.mjs
```

To refresh the sound files from a newer uisfx release:

```bash
node tools/generate.mjs --sounds node_modules/uisfx/sounds/zen
```

The generator needs only Node 18+.

The bundled fonts come from IBM's npm packages `@ibm/plex-sans@1.1.0` and `@ibm/plex-mono@2.5.0`, which ship only web formats. `tools/woff-to-sfnt.cjs` unwraps their WOFF files into TTFs. The table data stays byte-identical and only the whole-file checksum is recomputed. To update the fonts:

```bash
node tools/woff-to-sfnt.cjs <out dir> node_modules/@ibm/plex-sans/fonts/complete/woff/IBMPlexSans-*.woff
```

See [LICENSES.md](LICENSES.md) for fonts, sounds and icons.
