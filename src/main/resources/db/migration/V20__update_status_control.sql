update status_control set current_status_id=1, next_status_id=2 where id=1;
update status_control set current_status_id=1, next_status_id=3 where id=2;
update status_control set current_status_id=2, next_status_id=12 where id=3;
update status_control set current_status_id=2, next_status_id=13 where id=4;
update status_control set current_status_id=3, next_status_id=4 where id=5;
update status_control set current_status_id=3, next_status_id=5 where id=6;
update status_control set current_status_id=3, next_status_id=6 where id=7;
update status_control set current_status_id=3, next_status_id=7 where id=8;
update status_control set current_status_id=3, next_status_id=8 where id=9;
update status_control set current_status_id=4, next_status_id=7 where id=10;
update status_control set current_status_id=8, next_status_id=9 where id=11;
update status_control set current_status_id=8, next_status_id=10 where id=12;
update status_control set current_status_id=8, next_status_id=11 where id=13;
update status_control set current_status_id=11, next_status_id=14 where id=14;
update status_control set current_status_id=14, next_status_id=15 where id=15;
update status_control set current_status_id=5, next_status_id=12 where id=16;
update status_control set current_status_id=6, next_status_id=12 where id=17;
update status_control set current_status_id=7, next_status_id=12 where id=18;
update status_control set current_status_id=9, next_status_id=12 where id=19;
update status_control set current_status_id=10, next_status_id=12 where id=20;
update status_control set current_status_id=15, next_status_id=12 where id=21;
update status_control set current_status_id=5, next_status_id=13 where id=22;
update status_control set current_status_id=6, next_status_id=13 where id=23;
update status_control set current_status_id=7, next_status_id=13 where id=24;
update status_control set current_status_id=9, next_status_id=13 where id=25;
update status_control set current_status_id=10, next_status_id=13 where id=26;
update status_control set current_status_id=15, next_status_id=13 where id=27;
update status_control set current_status_id=13, next_status_id=2 where id=28;

insert into status_control(id, current_status_id, next_status_id) VALUE (29,13,3);
