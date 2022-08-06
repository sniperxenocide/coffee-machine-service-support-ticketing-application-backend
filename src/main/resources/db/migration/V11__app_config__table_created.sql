CREATE TABLE app_config
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    config_key    VARCHAR(255)          NOT NULL,
    config_value  VARCHAR(1000)         NOT NULL,
    `description` VARCHAR(1000)         NULL,
    CONSTRAINT pk_app_config PRIMARY KEY (id)
);

ALTER TABLE app_config
    ADD CONSTRAINT uc_app_config_config_key UNIQUE (config_key);

INSERT INTO app_config (config_key, config_value)
VALUES ('start_status_id', '1'),('end_status_id', '12');