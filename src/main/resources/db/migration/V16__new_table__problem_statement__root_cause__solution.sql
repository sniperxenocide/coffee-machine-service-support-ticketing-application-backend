CREATE TABLE problem_statement
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255)          NOT NULL,
    `description` VARCHAR(255)          NULL,
    CONSTRAINT pk_problem_statement PRIMARY KEY (id)
);

CREATE TABLE root_cause
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255)          NOT NULL,
    `description` VARCHAR(255)          NULL,
    CONSTRAINT pk_root_cause PRIMARY KEY (id)
);

CREATE TABLE solution
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255)          NOT NULL,
    `description` VARCHAR(255)          NULL,
    CONSTRAINT pk_solution PRIMARY KEY (id)
);

ALTER TABLE problem_statement
    ADD CONSTRAINT uc_problem_statement_name UNIQUE (name);

ALTER TABLE root_cause
    ADD CONSTRAINT uc_root_cause_name UNIQUE (name);

ALTER TABLE solution
    ADD CONSTRAINT uc_solution_name UNIQUE (name);