# Licences

## Leafy

Leafy's own work in this repository is licensed under the **Apache License 2.0**. The full text is in [LICENSE](LICENSE). That work is:
- the tokens, guidelines and component docs in `design-system/`
- the generator in `tools/`
- the CSS and JavaScript in `leafy-web/`
- the Kotlin and Swift code in `leafy-native/`

Copyright 2026 S3nku

The files listed below come from other projects and keep their own licences.

Leafy builds on Material 3 and borrows patterns from IBM's Carbon Design System. It is not affiliated with Google or IBM.

## Fonts: IBM Plex Sans and IBM Plex Mono

- **Where:**
  - `leafy-web/fonts/`
  - `leafy-native/android/leafy/src/main/res/font/`
  - `leafy-native/ios/LeafyDesign/Sources/LeafyDesign/Resources/Fonts/`
- **Licence:**
  - Copyright © 2017 IBM Corp., with Reserved Font Name "Plex".
  - Licensed under the [SIL Open Font License 1.1](https://openfontlicense.org). The full text sits next to the fonts as `IBM-Plex-OFL.txt` in each of those folders.
- **Source:** IBM's `@ibm/plex-sans` 1.1.0 and `@ibm/plex-mono` 2.5.0.
  - **Web:** uses their WOFF2 files unchanged.
  - **Native apps:** use TTFs unwrapped from the WOFF files, with the font tables unchanged.
- **What you can do:** the OFL lets you bundle the fonts in websites and apps, including paid ones. List them in your product's open-source notices, and don't sell the font files on their own.

## Sound runtime: uisfx

- **Where:** inside `design-system/components/bundle.js`, `leafy-web/js/leafy.js` and `leafy-web/js/leafy.global.js`.
- **Licence:**
  - uisfx 0.4.0 by Yuki Capital, inlined unchanged.
  - [MIT License](https://github.com/romainsimon/uisfx/blob/main/LICENSE). The full notice is kept in a comment in those files; keep it there.

## Sounds: the uisfx Zen pack

- **Where:**
  - **Web:** synthesized in the browser by the runtime above.
  - **Android:** `leafy-native/android/leafy/src/main/res/raw/zen_*.ogg`
  - **iOS:** `leafy-native/ios/LeafyDesign/Sources/LeafyDesign/Resources/Sounds/zen-*.mp3`
  - **Cue list:** `design-system/zen-cues.json`
- **Licence:** dedicated to the public domain under [CC0 1.0](https://creativecommons.org/publicdomain/zero/1.0/). No attribution is required, but it is appreciated.

## Icons

- **Web:** Material Symbols Sharp, loaded from Google Fonts by `leafy-web/css/icons.css` and not included in this repository. [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0).
- **Android:** the icon paths in `LeafyIcons.kt` come from Google's Material Design icons. [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0).
- **iOS:** SF Symbols are provided by Apple's system and used under Apple's terms for apps on its platforms.
