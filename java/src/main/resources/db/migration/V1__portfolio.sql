create table if not exists portfolio (
    id UUID primary key,
    created_date timestamptz,
    positions jsonb
);