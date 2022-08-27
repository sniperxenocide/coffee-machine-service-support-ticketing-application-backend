ALTER TABLE issue_header
    ADD creation_to_closing_time_min BIGINT NULL;

ALTER TABLE issue_header
    ADD creation_to_resolution_time_min BIGINT NULL;

ALTER TABLE issue_header
    ADD creation_to_response_time_min BIGINT NULL;

ALTER TABLE issue_header
    ADD resolution_to_closing_time_min BIGINT NULL;

ALTER TABLE issue_header
    ADD response_to_resolution_time_min BIGINT NULL;


ALTER TABLE issue_header
    ADD distributor_name VARCHAR(1000) NULL;

ALTER TABLE issue_header
    ADD distributor_oracle_code VARCHAR(50) NULL;

ALTER TABLE issue_header
    ADD division VARCHAR(500) NULL;

ALTER TABLE issue_header
    ADD region VARCHAR(500) NULL;

ALTER TABLE issue_header
    ADD territory VARCHAR(500) NULL;
