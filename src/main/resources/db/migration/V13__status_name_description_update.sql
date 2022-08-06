update status set name='Request Initiated',
                  description='Ticket Request Initiated from Shop' where id=1;

update status set name='Solved Over Phone',
                  description='Ticket Solved over Phone by MSO' where id=2;

update status set name='Shop Visit Plan',
                  description='Shop Visit Plan Created by MSO' where id=3;

update status set name='Solved After Visit',
                  description='Issue Solved after Visit by MSO' where id=4;

update status set name='Withdrawn for Inspection',
                  description='Machine Withdrawn for Inspection' where id=5;

update status set name='Replacement Requested',
                  description='Machine Replacement Requested by MSO' where id=6;

update status set name='Returned after Maintenance',
                  description='Machine Returned to Shop after Maintenance' where id=7;

update status set name='Returned after Repair',
                  description='Machine Returned to Shop after Repair' where id=8;

update status set name='Returned after Repair & Maint.',
                  description='Machine Returned to Shop after Repair & Maintenance' where id=9;

update status set name='Withdrawn after Replacement',
                  description='Machine Withdrawn for Replacement' where id=10;

update status set name='Returned Replacement',
                  description='Replaced Machine Returned to Shop' where id=11;

update status set name='Ticket Closed',
                  description='Ticket Closed' where id=12;

update status set name='Ticket Re-Opened',
                  description='Ticket Re-Opened' where id=13;