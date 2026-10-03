CREATE TABLE task.app_user
(
    id         UUID PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL
        CONSTRAINT uq_app_user_email UNIQUE,
    active     BOOLEAN      NOT NULL DEFAULT TRUE
);
