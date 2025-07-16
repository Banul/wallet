create table if not exists stock_valuation (
    id uuid primary key,
    ticker varchar,
    price numeric,
    currency varchar,
    created_date timestamptz
);