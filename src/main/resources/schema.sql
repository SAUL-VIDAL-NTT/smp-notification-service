CREATE TABLE IF NOT EXISTS notifications (
    id          VARCHAR(50)  NOT NULL PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    message     TEXT         NOT NULL,
    recipient_id VARCHAR(100) NOT NULL,
    type        VARCHAR(100) NOT NULL,
    created_at  TIMESTAMP    NOT NULL
);
