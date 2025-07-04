create table if not exists asset_details(
    id uuid primary key,
    ticker varchar,
    amount numeric,
    currency varchar,
    created_date timestamptz
);