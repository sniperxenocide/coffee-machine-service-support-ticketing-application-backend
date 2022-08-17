ALTER TABLE status
    ADD tag_description VARCHAR(500) NULL;

update status set tag_description='Ticket Initiated' where status_tag='START';
update status set tag_description='Awaiting Close' where status_tag='PRE_END';
update status set tag_description='Ticket Closed' where status_tag='END';
update status set tag_description='Ticket in Progress' where status_tag='COMMON';