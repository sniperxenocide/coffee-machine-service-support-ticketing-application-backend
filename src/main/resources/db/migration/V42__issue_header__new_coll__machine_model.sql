ALTER TABLE issue_header
    ADD machine_model VARCHAR(255) NULL;

update uom set name='Pcs' where id=1;