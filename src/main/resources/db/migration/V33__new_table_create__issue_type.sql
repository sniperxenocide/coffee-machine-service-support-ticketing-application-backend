CREATE TABLE issue_type
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255)          NOT NULL,
    `description` VARCHAR(500)          NULL,
    CONSTRAINT pk_issue_type PRIMARY KEY (id)
);

ALTER TABLE issue_type
    ADD CONSTRAINT uc_issue_type_name UNIQUE (name);