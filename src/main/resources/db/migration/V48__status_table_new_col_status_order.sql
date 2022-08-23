ALTER TABLE status
    ADD status_order BIGINT NULL;

update status set status_order=1 where id=1;
update status set status_order=2 where id=2;
update status set status_order=3 where id=5;
update status set status_order=4 where id=6;
update status set status_order=5 where id=7;
update status set status_order=6 where id=9;
update status set status_order=7 where id=10;
update status set status_order=8 where id=15;
update status set status_order=9 where id=3;
update status set status_order=10 where id=4;
update status set status_order=11 where id=8;
update status set status_order=12 where id=11;
update status set status_order=13 where id=13;
update status set status_order=14 where id=14;
update status set status_order=15 where id=12;