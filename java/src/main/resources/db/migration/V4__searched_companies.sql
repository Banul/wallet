create table if not exists searched_companies (
    id uuid primary key,
    ticker varchar unique,
    does_exist boolean
);