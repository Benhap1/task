-- V2__create_tasks_table.sql
CREATE TABLE IF NOT EXISTS public.tasks
(
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    status VARCHAR(255) NOT NULL,
    priority VARCHAR(255) NOT NULL,
    author_id INT NOT NULL,
    assignee_id INT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (author_id) REFERENCES public.users(id) ON DELETE CASCADE,
    FOREIGN KEY (assignee_id) REFERENCES public.users(id) ON DELETE SET NULL
    );

DO
$$
BEGIN
CREATE TYPE public.task_status AS ENUM ('PENDING', 'IN_PROGRESS', 'COMPLETED');
CREATE TYPE public.task_priority AS ENUM ('HIGH', 'MEDIUM', 'LOW');
EXCEPTION
    WHEN duplicate_object THEN null;
END $$;

ALTER TABLE public.tasks
ALTER COLUMN status TYPE public.task_status USING status::public.task_status,
    ALTER COLUMN priority TYPE public.task_priority USING priority::public.task_priority;
