create table if not exists tracked_companies(
    id uuid primary key,
    ticker varchar unique
);