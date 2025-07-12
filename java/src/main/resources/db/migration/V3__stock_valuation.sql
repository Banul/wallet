create table if not exists stock_valuation (
    id uuid primary key,
    ticker varchar,
    amount numeric,
    currency varchar,
    created_date timestamptz
);