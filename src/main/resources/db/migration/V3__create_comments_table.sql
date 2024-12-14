-- V3__create_comments_table.sql
CREATE TABLE IF NOT EXISTS comments (
                                        id SERIAL PRIMARY KEY,
                                        task_id INT NOT NULL,
                                        author_id INT NOT NULL,
                                        content TEXT NOT NULL,
                                        created_at TIMESTAMP NOT NULL,
                                        FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES users(id) ON DELETE CASCADE
    );
