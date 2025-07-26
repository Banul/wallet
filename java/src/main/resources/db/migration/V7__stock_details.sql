create table if not exists stock_details (
    id UUID primary key,
    ticker varchar,
    country varchar,

    assets_creation_requests jsonb,
    created_date timestamptz
);