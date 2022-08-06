insert into status(id, name, description, default_name) values
(14,'Pending for Spare Parts','Pending for Spare Parts','In-Progress'),
(15,'Repaired with Spare Parts','Machine Repaired with Spare Parts','In-Progress');

insert into status_control(id, current_status_id, next_status_id) values
(26,3,14),(27,14,15),(28,15,12);

insert into data_field(id, name, type) value (8,'Spare Replacement Date','date');

insert into status_wise_data_field(status_id,data_field_id) values
(14,8),(15,1),(15,2),(15,3);

insert into role_status(role_id, status_id) VALUES (2,14),(2,15);