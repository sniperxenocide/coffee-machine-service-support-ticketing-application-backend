ALTER TABLE status_wise_data
    MODIFY field_data VARCHAR(5000) not null ;

ALTER TABLE status_wise_data
    MODIFY field_name VARCHAR(500) not null ;

ALTER TABLE status_wise_data
    MODIFY id bigint auto_increment ;