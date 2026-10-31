# DataTable

A sortable, selectable table for comparing rows of data (Carbon), on the M3 roles.

- Provide `columns`: `[{ key, header, align?: 'end', sortable?, width?, render?(row), sortValue?(row) }]` and `rows` (each with a unique `id`). `render` lets a cell hold a Tag, Link or IconButton.
- `sortable` makes every column sortable (a column's own `sortable` wins). Sorting cycles ascending, descending, none; `sort` + `onSortChange` or `defaultSort`.
- `selectable` adds a checkbox column; `selectedIds` + `onSelectionChange` or `defaultSelectedIds`. While rows are selected, `batchActions` (Buttons) replace the toolbar on a `primary` bar.
- `title`, `description` and `toolbar` (a small SearchBar and Buttons) sit above. `size` sets row height: `xs` 24, `sm` 32, `md` 40, `lg` 48 (default), `xl` 64. `zebra` stripes rows with `layer-02`.
- Header row on `layer-accent`, body on `layer-01`, rows split by `border-subtle`, selected rows `secondary-container`. Numbers align to the end with tabular figures.
- Put Pagination directly under long tables.

## Sound

Selecting rows (one at a time or all at once) plays `select`, clearing them plays `deselect`, and changing the sort plays `sound-sort` (the `reorder` cue). The batch bar's Cancel plays `deselect`. The row checkboxes are silenced so each change makes one sound.
