CREATE TABLE attachments
(
    id            BIGINT       NOT NULL,
    message_id    BIGINT       NOT NULL,
    original_name VARCHAR(255) NOT NULL,
    url           VARCHAR(255) NOT NULL,
    size          BIGINT       NOT NULL,
    created_at    DATETIME(6)     NOT NULL,
    updated_at    DATETIME(6) NULL,
    PRIMARY KEY (id)
);

CREATE TABLE channel_members
(
    id                   BIGINT   NOT NULL,
    user_id              BIGINT   NOT NULL,
    channel_id           BIGINT   NOT NULL,
    last_read_message_id BIGINT NULL,
    created_at           DATETIME(6) NOT NULL,
    updated_at           DATETIME(6) NULL,
    PRIMARY KEY (id)
);

CREATE TABLE channels
(
    id           BIGINT      NOT NULL,
    space_id     BIGINT      NOT NULL,
    name         VARCHAR(50) NOT NULL,
    channel_type VARCHAR(50) NOT NULL,
    created_at   DATETIME(6)    NOT NULL,
    updated_at   DATETIME(6) NULL,
    deleted_at   DATETIME(6) NULL,
    PRIMARY KEY (id)
);

CREATE TABLE messages
(
    id              BIGINT        NOT NULL,
    user_id         BIGINT        NOT NULL,
    channel_id      BIGINT        NOT NULL,
    content         VARCHAR(1000) NOT NULL,
    idempotency_key BINARY(16)    NOT NULL,
    created_at      DATETIME(6)      NOT NULL,
    updated_at      DATETIME(6) NULL,
    deleted_at      DATETIME(6) NULL,
    PRIMARY KEY (id)
);

CREATE TABLE space_members
(
    id         BIGINT      NOT NULL,
    user_id    BIGINT      NOT NULL,
    space_id   BIGINT      NOT NULL,
    space_role VARCHAR(50) NOT NULL,
    created_at DATETIME(6)    NOT NULL,
    updated_at DATETIME(6) NULL,
    PRIMARY KEY (id)
);

CREATE TABLE spaces
(
    id         BIGINT      NOT NULL,
    name       VARCHAR(50) NOT NULL,
    created_at DATETIME(6)    NOT NULL,
    updated_at DATETIME(6) NULL,
    deleted_at DATETIME(6) NULL,
    PRIMARY KEY (id)
);

CREATE TABLE users
(
    id             BIGINT       NOT NULL,
    name           VARCHAR(50)  NOT NULL,
    email          VARCHAR(255) NOT NULL,
    password_hash  VARCHAR(255) NOT NULL,
    image_url      VARCHAR(255) NULL,
    status_message VARCHAR(255) NULL,
    last_login_at  DATETIME(6) NULL,
    created_at     DATETIME(6)     NOT NULL,
    updated_at     DATETIME(6) NULL,
    deleted_at     DATETIME(6) NULL,
    PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uk_users_email UNIQUE (email);

ALTER TABLE channel_members
    ADD CONSTRAINT uk_channel_members_user_channel UNIQUE (user_id, channel_id);

ALTER TABLE messages
    ADD CONSTRAINT uk_messages_user_idempotency_key UNIQUE (user_id, idempotency_key);

ALTER TABLE space_members
    ADD CONSTRAINT uk_space_members_user_space UNIQUE (user_id, space_id);

CREATE INDEX idx_messages_channel_message ON messages (channel_id, id);

ALTER TABLE attachments
    ADD CONSTRAINT fk_attachments_messages FOREIGN KEY (message_id) REFERENCES messages (id) ON DELETE NO ACTION;

CREATE INDEX fk_attachments_messages_idx ON attachments (message_id);

ALTER TABLE channel_members
    ADD CONSTRAINT fk_channel_members_channels FOREIGN KEY (channel_id) REFERENCES channels (id) ON DELETE NO ACTION;

CREATE INDEX fk_channel_members_channels_idx ON channel_members (channel_id);

ALTER TABLE channel_members
    ADD CONSTRAINT fk_channel_members_users FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE NO ACTION;

ALTER TABLE channels
    ADD CONSTRAINT fk_channels_spaces FOREIGN KEY (space_id) REFERENCES spaces (id) ON DELETE NO ACTION;

CREATE INDEX fk_channels_spaces_idx ON channels (space_id);

ALTER TABLE messages
    ADD CONSTRAINT fk_messages_channels FOREIGN KEY (channel_id) REFERENCES channels (id) ON DELETE NO ACTION;

ALTER TABLE messages
    ADD CONSTRAINT fk_messages_users FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE NO ACTION;

ALTER TABLE space_members
    ADD CONSTRAINT fk_space_members_spaces FOREIGN KEY (space_id) REFERENCES spaces (id) ON DELETE NO ACTION;

CREATE INDEX fk_space_members_spaces_idx ON space_members (space_id);

ALTER TABLE space_members
    ADD CONSTRAINT fk_space_members_users FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE NO ACTION;
