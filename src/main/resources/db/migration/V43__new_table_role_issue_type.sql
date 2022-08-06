CREATE TABLE role_issue_type
(
    role_id       BIGINT NOT NULL,
    issue_type_id BIGINT NOT NULL
);

ALTER TABLE role_issue_type
    ADD CONSTRAINT fk_rolisstyp_on_issue_type FOREIGN KEY (issue_type_id) REFERENCES issue_type (id);

ALTER TABLE role_issue_type
    ADD CONSTRAINT fk_rolisstyp_on_role FOREIGN KEY (role_id) REFERENCES `role` (id);


insert into role_issue_type(role_id, issue_type_id) VALUES
(1,1),(1,2),(2,1),(2,2),(3,1),(4,1),(4,2);