create table if not exists asset_details(
    id uuid primary key,
    portfolio_id uuid not null references portfolio(id) on delete cascade,
    ticker varchar,
    amount numeric,
    currency varchar,
    created_date timestamptz
);