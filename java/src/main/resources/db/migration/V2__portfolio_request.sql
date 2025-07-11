create table if not exists portfolio_request (
    id UUID primary key,
    status varchar,
    assets_creation_requests jsonb,
    created_date timestamptz
);