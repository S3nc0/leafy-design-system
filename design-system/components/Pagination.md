# Pagination

The bar under a data table for moving between pages and choosing how many rows to show (Carbon).

- Provide `totalItems`, `pageSizes` (default 10–50), and `page` + `pageSize` with `onChange({ page, pageSize })`, or `defaultPage` / `defaultPageSize`.
- Changing the page size returns to page 1. Previous and next disable at the ends.
- 48px tall on `layer-01`, sections split by `border-subtle`. Native selects keep it keyboard and screen-reader friendly.

## Sound

Silent. Page changes use `sound-navigate`, which is `none`: moving around is not an outcome.
