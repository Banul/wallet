create table if not exists stock_valuation_current (
    id uuid primary key,
    ticker varchar,
    price numeric,
    currency varchar,
    created_date timestamptz
);

