ALTER TABLE issue_header
    ADD issue_type_id BIGINT NULL;

ALTER TABLE issue_header
    ADD CONSTRAINT FK_ISSUE_HEADER_ON_ISSUE_TYPE FOREIGN KEY (issue_type_id) REFERENCES issue_type (id);