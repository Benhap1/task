-- V2__create_tasks_table.sql

CREATE TABLE IF NOT EXISTS public.tasks
(
    id
    SERIAL
    PRIMARY
    KEY,
    title
    VARCHAR
(
    255
) NOT NULL,
    description TEXT NOT NULL,
    status VARCHAR
(
    255
) NOT NULL,
    priority VARCHAR
(
    255
) NOT NULL,
    author_id INT NOT NULL,
    assignee_id INT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY
(
    author_id
) REFERENCES public.users
(
    id
) ON DELETE CASCADE,
    FOREIGN KEY
(
    assignee_id
) REFERENCES public.users
(
    id
)
  ON DELETE SET NULL
    );
