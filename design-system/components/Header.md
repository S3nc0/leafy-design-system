# Header

The 48px shell header for desktop apps (Carbon UI shell): product name, top-level links and global actions.

- Provide `name` (the product) and an optional `prefix` (the family name, set in SemiBold). `href` links the name home.
- `navItems`: `[{ label, href?, onClick?, current? }]`; the current link gets a 3px `primary` underline.
- `actions`: `[{ icon, label, onClick?, badge?, active? }]` as 48px icon buttons at the end (search, alerts, account, app switcher).
- `onMenu` adds a menu button at the start to open SideNav on smaller windows (`menuOpen` swaps it to a close icon).
- `inverse` uses `inverse-surface`: the dark Carbon header. Use it on dense, data-heavy products; the default light header suits content pages.
