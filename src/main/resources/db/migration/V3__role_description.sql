ALTER TABLE `role`
    ADD `description` VARCHAR(255) NULL;

ALTER TABLE `role`
    MODIFY `description` VARCHAR(255) NOT NULL;