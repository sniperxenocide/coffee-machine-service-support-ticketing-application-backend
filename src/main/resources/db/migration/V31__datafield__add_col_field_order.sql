ALTER TABLE data_field
    ADD field_order numeric DEFAULT 1 NOT NULL;

update data_field set field_order=1 where id=9;
update data_field set field_order=2 where id=8;
update data_field set field_order=3 where id=7;
update data_field set field_order=4 where id=6;
update data_field set field_order=5 where id=5;
update data_field set field_order=6 where id=4;
update data_field set field_order=9 where id=3;
update data_field set field_order=8 where id=2;
update data_field set field_order=7 where id=1;

update data_field set name='Spare Parts List' where id=9;
update data_field set name='Parts Replacement Date' where id=8;