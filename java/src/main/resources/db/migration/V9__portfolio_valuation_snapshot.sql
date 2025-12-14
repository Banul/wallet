create table if not exists portfolio_valuation_snapshot (
        id UUID primary key,
        portfolio_id varchar,
        valuation numeric,
        currency varchar,
        created_date timestamptz
);