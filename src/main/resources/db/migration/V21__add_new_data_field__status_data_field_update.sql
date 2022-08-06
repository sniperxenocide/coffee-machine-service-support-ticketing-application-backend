insert into data_field(id, name, type) VALUE (9,'Spare Parts Selection','list');

truncate table status_wise_data_field;

insert into status_wise_data_field(status_id,data_field_id) values
 (2,1),(2,2),(2,3),(2,9), (5,1), (5,2),(5,3), (5,9),(6,1),(6,2),
 (6,3),(6,9),(7,1),(7,2),(7,3),(7,9),(9,1), (9,2),(9,3),(9,9),
  (10,1),(10,2),(10,3),(10,9),(15,1), (15,2),(15,3), (15,9),(3,4),
 (4,8), (8,5),(14,5),(12,6), (13,7);
