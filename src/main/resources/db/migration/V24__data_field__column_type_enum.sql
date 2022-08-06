ALTER TABLE data_field
    MODIFY type ENUM ('problem_statement', 'root_cause', 'solution', 'string', 'date', 'spare_parts') NOT NULL default 'string';
