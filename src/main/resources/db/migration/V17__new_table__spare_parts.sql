CREATE TABLE spare_parts
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255)          NOT NULL,
    code          VARCHAR(255)          NOT NULL,
    type          VARCHAR(255)          NULL,
    `description` VARCHAR(255)          NULL,
    CONSTRAINT pk_spare_parts PRIMARY KEY (id)
);

ALTER TABLE spare_parts
    ADD CONSTRAINT uc_spare_parts_code UNIQUE (code);

ALTER TABLE spare_parts
    ADD CONSTRAINT uc_spare_parts_name UNIQUE (name);