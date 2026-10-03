CREATE SCHEMA IF NOT EXISTS TASK;

CREATE TABLE task.task
(
    id          UUID PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    description TEXT,
    status      VARCHAR(50)  NOT NULL,
    due_date     DATE,
    user_id     UUID
);

CREATE INDEX idx_task_user_id ON task.task (user_id);
