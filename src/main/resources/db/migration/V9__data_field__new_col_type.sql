ALTER TABLE data_field
    ADD type VARCHAR(255) NULL;

UPDATE data_field
SET type = 'string' where id in (1,2,3,5,6);

UPDATE data_field
SET type = 'date' where id in (4,7);