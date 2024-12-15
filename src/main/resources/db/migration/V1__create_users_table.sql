-- V1__create_users_table.sql
CREATE TABLE IF NOT EXISTS public.users
(
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL CHECK (role IN ('ADMIN', 'USER'))
    );

CREATE INDEX IF NOT EXISTS idx_users_email ON public.users (email);
