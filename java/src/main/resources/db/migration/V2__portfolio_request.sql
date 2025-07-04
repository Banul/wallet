create table if not exists portfolio_request (
    id UUID primary key,
    status varchar,
    assetsCreationRequests jsonb,
    created_date timestamptz
);