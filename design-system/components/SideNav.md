# SideNav

The 256px side navigation of the desktop shell (Carbon UI shell), with M3 selection colour.

- Provide `items`: `[{ key?, icon, label, href?, onClick?, badge?, children? }]`. Items with `children` become expandable groups (`expanded` + `onExpandedChange` or `defaultExpanded`, by key).
- `value` + `onChange` or `defaultValue` names the current item by key (a child's default key is `<parent>.<index>`).
- The current item is `secondary-container` with a 3px `primary` bar at the start; a collapsed group shows it when a child is current.
- Use from `window-expanded` up, under a Header. On smaller windows, open it from the Header menu button or use NavigationRail or NavigationBar.

## Sound

Opening and closing a group plays `expand` and `collapse`. Links use `sound-navigate`, which is silent.
