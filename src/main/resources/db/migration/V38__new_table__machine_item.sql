CREATE TABLE machine_item
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    name             VARCHAR(500)          NOT NULL,
    code             VARCHAR(255)          NULL,
    oracle_item_code VARCHAR(255)          NULL,
    item_group_id    BIGINT                NULL,
    item_category_id BIGINT                NULL,
    machine_model_id BIGINT                NULL,
    uom_id           BIGINT                NULL,
    CONSTRAINT pk_machine_item PRIMARY KEY (id)
);

ALTER TABLE machine_item
    ADD CONSTRAINT FK_MACHINE_ITEM_ON_ITEM_CATEGORY FOREIGN KEY (item_category_id) REFERENCES item_category (id);

ALTER TABLE machine_item
    ADD CONSTRAINT FK_MACHINE_ITEM_ON_ITEM_GROUP FOREIGN KEY (item_group_id) REFERENCES item_group (id);

ALTER TABLE machine_item
    ADD CONSTRAINT FK_MACHINE_ITEM_ON_MACHINE_MODEL FOREIGN KEY (machine_model_id) REFERENCES machine_model (id);

ALTER TABLE machine_item
    ADD CONSTRAINT FK_MACHINE_ITEM_ON_UOM FOREIGN KEY (uom_id) REFERENCES uom (id);