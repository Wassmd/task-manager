CREATE TABLE task.app_user
(
    id         UUID PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL
        CONSTRAINT uq_app_user_email UNIQUE,
    active     BOOLEAN      NOT NULL DEFAULT TRUE
);

-- 1. Spalte zunächst nullable hinzufügen
ALTER TABLE task.task
    ADD COLUMN user_id UUID
        CONSTRAINT fk_task_user REFERENCES task.app_user (id);

-- 2. Default-User für bestehende Tasks anlegen
INSERT INTO task.app_user (id, first_name, last_name, email, active)
VALUES ('00000000-0000-0000-0000-000000000001', 'System', 'User', 'system@task-manager.local', TRUE);

-- 3. Bestehende Tasks backfillen
UPDATE task.task
SET user_id = '00000000-0000-0000-0000-000000000001'
WHERE user_id IS NULL;

-- 4. Jetzt NOT NULL erzwingen
ALTER TABLE task.task
    ALTER COLUMN user_id SET NOT NULL;

CREATE INDEX idx_task_user_id ON task.task (user_id);
