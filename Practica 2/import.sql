CREATE TABLE user_app
(
    id       INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    username VARCHAR(255),
    password VARCHAR(255),
    mail     VARCHAR(255),
    CONSTRAINT pk_user_app PRIMARY KEY (id)
);

ALTER TABLE user_app
    ADD CONSTRAINT uc_user_app_username UNIQUE (username);
