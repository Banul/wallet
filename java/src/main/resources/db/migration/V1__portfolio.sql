create table if not exists portfolio (
    id UUID primary key,
    name varchar,
    created_date timestamptz,
    positions jsonb
);