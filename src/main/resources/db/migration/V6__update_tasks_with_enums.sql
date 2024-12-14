-- V6__update_tasks_with_enums.sql
ALTER TABLE tasks
ALTER COLUMN status TYPE task_status USING status::task_status,
    ALTER COLUMN priority TYPE task_priority USING priority::task_priority;
