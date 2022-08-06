INSERT INTO role_status(role_id,status_id)
VALUES (2,1),(2,2),(2,3), (2,4),(2,5),(2,6),(2,7),(2,8),
       (2,9),(2,10),(2,11),(3,1),(4,12),(4,13);

INSERT INTO data_field(id,name) values
    (1,'Problem Description'),    (2,'Root Cause'),
    (3,'Solution'),    (4,'Shop Visit Date'),
    (5,'Customer Feedback'),    (6,'Cause of Re-Open'),
    (7,'Machine Return Date');

INSERT INTO status_wise_data_field(status_id,data_field_id) values
    (2,1),    (2,2),    (2,3),    (3,4),    (4,1),    (4,2),
    (4,3),    (5,7),    (10,7),    (7,1),    (7,2),    (7,3),
    (8,1),    (8,2),    (8,3),    (9,1),    (9,2),    (9,3),
    (11,1),    (11,2),    (11,3),    (12,5),    (13,6);

INSERT INTO status_control(id, current_status_id, next_status_id) VALUES
 (1,1,2), (2,1,3), (3,3,4), (4,3,5), (5,3,6),  (6,5,6),
  (7,5,7), (8,5,8),  (9,5,9),  (10,6,10),  (11,10,11),
  (12,2,12),  (13,4,12),  (14,7,12),  (15,8,12),  (16,9,12),
  (17,11,12),  (18,2,13),  (19,4,13),  (20,7,13),  (21,8,13),
  (22,9,13),  (23,11,13), (24,13,2),  (25,13,2);