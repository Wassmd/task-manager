CREATE TABLE task.task_watcher
(
    id        UUID PRIMARY KEY,
    email     VARCHAR(255) NOT NULL,
    task_id   UUID NOT NULL
        CONSTRAINT fk_watcher_task REFERENCES task.task (id) ON DELETE CASCADE
);

CREATE INDEX idx_task_watcher_task_id on task.task_watcher (task_id);
