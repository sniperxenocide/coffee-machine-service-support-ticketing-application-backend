CREATE TABLE item_category
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255)          NOT NULL,
    `description` VARCHAR(500)          NULL,
    CONSTRAINT pk_item_category PRIMARY KEY (id)
);

CREATE TABLE item_group
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255)          NOT NULL,
    `description` VARCHAR(500)          NULL,
    CONSTRAINT pk_item_group PRIMARY KEY (id)
);

CREATE TABLE machine_model
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    name       VARCHAR(255)          NOT NULL,
    brand_id   BIGINT                NOT NULL,
    brand_name VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_machine_model PRIMARY KEY (id)
);

CREATE TABLE uom
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_uom PRIMARY KEY (id)
);

ALTER TABLE item_category
    ADD CONSTRAINT uc_item_category_name UNIQUE (name);

ALTER TABLE item_group
    ADD CONSTRAINT uc_item_group_name UNIQUE (name);

ALTER TABLE uom
    ADD CONSTRAINT uc_uom_name UNIQUE (name);