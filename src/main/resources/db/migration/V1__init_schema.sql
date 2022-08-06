CREATE TABLE data_field
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255)          NULL,
    CONSTRAINT pk_data_field PRIMARY KEY (id)
);

CREATE TABLE issue_header
(
    id                   BIGINT AUTO_INCREMENT  NOT NULL,
    request_token        VARCHAR(255)           NOT NULL,
    shop_user_id         BIGINT                 NOT NULL,
    shop_id              BIGINT                 NOT NULL,
    shop_name            VARCHAR(255)           NOT NULL,
    shop_owner_name      VARCHAR(255)           NOT NULL,
    shop_owner_phone     VARCHAR(45)            NOT NULL,
    shop_address         VARCHAR(255)           NULL,
    mso_user_id          BIGINT                 NOT NULL,
    mso_phone            VARCHAR(45)            NOT NULL,
    mso_location         VARCHAR(255)           NOT NULL,
    current_mso_user_id  BIGINT                 NOT NULL,
    current_mso_phone    VARCHAR(45)            NOT NULL,
    current_mso_location VARCHAR(255)           NOT NULL,
    current_status_id    BIGINT                 NOT NULL,
    creation_time        datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
    last_update_time     datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    machine_id           BIGINT                 NULL,
    machine_number       VARCHAR(255)           NULL,
    machine_brand        VARCHAR(100)           NULL,
    CONSTRAINT pk_issue_header PRIMARY KEY (id)
);

CREATE TABLE privilege
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(100)          NOT NULL,
    api           VARCHAR(100)          NOT NULL,
    method        VARCHAR(25)           NOT NULL,
    `description` VARCHAR(500)          NULL,
    CONSTRAINT pk_privilege PRIMARY KEY (id)
);

CREATE TABLE `role`
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(100)          NOT NULL,
    CONSTRAINT pk_role PRIMARY KEY (id)
);

CREATE TABLE role_privilege
(
    privilege_id BIGINT NOT NULL,
    role_id      BIGINT NOT NULL
);

CREATE TABLE role_status
(
    role_id   BIGINT NOT NULL,
    status_id BIGINT NOT NULL
);

CREATE TABLE status
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(50)           NOT NULL,
    `description` VARCHAR(255)          NULL,
    CONSTRAINT pk_status PRIMARY KEY (id)
);

CREATE TABLE status_control
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    current_status_id BIGINT                NOT NULL,
    next_status_id    BIGINT                NOT NULL,
    CONSTRAINT pk_status_control PRIMARY KEY (id)
);

CREATE TABLE status_track
(
    id                 BIGINT AUTO_INCREMENT  NOT NULL,
    issue_header_id    BIGINT                 NOT NULL,
    status_id          BIGINT                 NOT NULL,
    mso_user_id        BIGINT                 NOT NULL,
    mso_phone          VARCHAR(45)            NOT NULL,
    mso_location       VARCHAR(255)           NOT NULL,
    `description`      VARCHAR(500)           NULL,
    created_by_user_id BIGINT                 NOT NULL,
    creation_time      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT pk_status_track PRIMARY KEY (id)
);

CREATE TABLE status_wise_data
(
    id              BIGINT       NOT NULL,
    status_track_id BIGINT       NULL,
    field_data      VARCHAR(500) NOT NULL,
    field_name      VARCHAR(100) NOT NULL,
    CONSTRAINT pk_status_wise_data PRIMARY KEY (id)
);

CREATE TABLE status_wise_data_field
(
    data_field_id BIGINT NOT NULL,
    status_id     BIGINT NOT NULL
);

CREATE TABLE user
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    username     VARCHAR(50)           NOT NULL,
    password     VARCHAR(50)           NOT NULL,
    remote_id    BIGINT                NULL,
    remote_table VARCHAR(100)          NULL,
    location     VARCHAR(500)          NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE user_role
(
    role_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL
);

ALTER TABLE issue_header
    ADD CONSTRAINT uc_issue_header_request_token UNIQUE (request_token);

ALTER TABLE privilege
    ADD CONSTRAINT uc_privilege_api UNIQUE (api);

ALTER TABLE privilege
    ADD CONSTRAINT uc_privilege_name UNIQUE (name);

ALTER TABLE `role`
    ADD CONSTRAINT uc_role_name UNIQUE (name);

ALTER TABLE status
    ADD CONSTRAINT uc_status_name UNIQUE (name);

ALTER TABLE user
    ADD CONSTRAINT uc_user_username UNIQUE (username);

ALTER TABLE issue_header
    ADD CONSTRAINT FK_ISSUE_HEADER_ON_CURRENT_MSO_USER FOREIGN KEY (current_mso_user_id) REFERENCES user (id);

ALTER TABLE issue_header
    ADD CONSTRAINT FK_ISSUE_HEADER_ON_CURRENT_STATUS FOREIGN KEY (current_status_id) REFERENCES status (id);

ALTER TABLE issue_header
    ADD CONSTRAINT FK_ISSUE_HEADER_ON_MSO_USER FOREIGN KEY (mso_user_id) REFERENCES user (id);

ALTER TABLE issue_header
    ADD CONSTRAINT FK_ISSUE_HEADER_ON_SHOP_USER FOREIGN KEY (shop_user_id) REFERENCES user (id);

ALTER TABLE status_control
    ADD CONSTRAINT FK_STATUS_CONTROL_ON_CURRENT_STATUS FOREIGN KEY (current_status_id) REFERENCES status (id);

ALTER TABLE status_control
    ADD CONSTRAINT FK_STATUS_CONTROL_ON_NEXT_STATUS FOREIGN KEY (next_status_id) REFERENCES status (id);

ALTER TABLE status_track
    ADD CONSTRAINT FK_STATUS_TRACK_ON_CREATED_BY_USER FOREIGN KEY (created_by_user_id) REFERENCES user (id);

ALTER TABLE status_track
    ADD CONSTRAINT FK_STATUS_TRACK_ON_ISSUE_HEADER FOREIGN KEY (issue_header_id) REFERENCES issue_header (id);

ALTER TABLE status_track
    ADD CONSTRAINT FK_STATUS_TRACK_ON_MSO_USER FOREIGN KEY (mso_user_id) REFERENCES user (id);

ALTER TABLE status_track
    ADD CONSTRAINT FK_STATUS_TRACK_ON_STATUS FOREIGN KEY (status_id) REFERENCES status (id);

ALTER TABLE status_wise_data
    ADD CONSTRAINT FK_STATUS_WISE_DATA_ON_STATUS_TRACK FOREIGN KEY (status_track_id) REFERENCES status_track (id);

ALTER TABLE role_privilege
    ADD CONSTRAINT fk_rolpri_on_privilege FOREIGN KEY (privilege_id) REFERENCES privilege (id);

ALTER TABLE role_privilege
    ADD CONSTRAINT fk_rolpri_on_role FOREIGN KEY (role_id) REFERENCES `role` (id);

ALTER TABLE role_status
    ADD CONSTRAINT fk_rolsta_on_role FOREIGN KEY (role_id) REFERENCES `role` (id);

ALTER TABLE role_status
    ADD CONSTRAINT fk_rolsta_on_status FOREIGN KEY (status_id) REFERENCES status (id);

ALTER TABLE status_wise_data_field
    ADD CONSTRAINT fk_stawisdatfie_on_data_field FOREIGN KEY (data_field_id) REFERENCES data_field (id);

ALTER TABLE status_wise_data_field
    ADD CONSTRAINT fk_stawisdatfie_on_status FOREIGN KEY (status_id) REFERENCES status (id);

ALTER TABLE user_role
    ADD CONSTRAINT fk_user_role_on_role FOREIGN KEY (role_id) REFERENCES `role` (id);

ALTER TABLE user_role
    ADD CONSTRAINT fk_user_role_on_user FOREIGN KEY (user_id) REFERENCES user (id);