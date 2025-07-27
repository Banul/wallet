create table if not exists company_details (
    id UUID primary key,
    ticker varchar,
    country varchar,
    industry varchar,
    sector varchar,
    created_date timestamptz
);