create table if not exists portfolio_request (
    id UUID primary key,
    status varchar,
    assets jsonb,
    created_date timestamptz
);