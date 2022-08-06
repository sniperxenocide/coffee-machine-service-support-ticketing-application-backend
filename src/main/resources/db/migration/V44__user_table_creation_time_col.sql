ALTER TABLE user
    ADD creation_time datetime DEFAULT CURRENT_TIMESTAMP NULL;

ALTER TABLE user
    ADD last_login_time datetime NULL;
