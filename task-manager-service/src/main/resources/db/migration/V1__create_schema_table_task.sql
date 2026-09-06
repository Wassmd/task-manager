CREATE SCHEMA IF NOT EXISTS TASK;

CREATE TABLE task.task
(
    id          UUID PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    description TEXT,
    status      VARCHAR(50)  NOT NULL,
    due_date     DATE
)