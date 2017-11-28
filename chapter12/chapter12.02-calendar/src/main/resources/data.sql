-- In Spring Boot using Spring Data, we use ~/src/main/resources/data.sql to seed data.
-- We no longer have to use ~/src/main/resources/database/h2/calendar-data.sql to seed data.

-- New Calendar Users ---------------------------

-- Password for user1 was 'user1'
insert into calendar_users(id,email,password,first_name,last_name) values (0, 'user1@example.com','$2a$04$qr7RWyqOnWWC1nwotUW1nOe1RD5.mKJVHK16WZy6v49pymu1WDHmi','User','1');
-- Password for admin was 'admin'
insert into calendar_users(id,email,password,first_name,last_name) values (1,'admin1@example.com','$2a$04$0CF/Gsquxlel3fWq5Ic/ZOGDCaXbMfXYiXsviTNMQofWRXhvJH3IK','Admin','1');
-- Password for user2 was 'user2'
insert into calendar_users(id,email,password,first_name,last_name) values (2,'user2@example.com','$2a$04$pShXhqV.wTehbffbD51tk.SPfPxIZvICyLn9WvQ8YhlXcYqWtW2Mm','User2','2');


-- ROLES ----------------------------------------
insert into role(id, name) values (0, 'ROLE_USER');
insert into role(id, name) values (1, 'ROLE_ADMIN');


-- User's have one role
insert into user_role(user_id,role_id) values (0, 0);
insert into user_role(user_id,role_id) values (2, 0);

-- Admin has two roles
insert into user_role(user_id,role_id) values (1, 0);
insert into user_role(user_id,role_id) values (1, 1);


-- Events ---------------------------------------
insert into events (id,when,summary,description,owner,attendee) values (100,'2017-07-03 20:30:00','Birthday Party','This is going to be a great birthday',0,1);
insert into events (id,when,summary,description,owner,attendee) values (101,'2017-12-23 13:00:00','Conference Call','Call with the client',2,0);
insert into events (id,when,summary,description,owner,attendee) values (102,'2017-09-14 11:30:00','Vacation','Paragliding in Greece',1,2);


--- ACLs ----------------------------------------
-- Event Class to protect:
insert into acl_class (id, class) values (10, 'com.packtpub.springsecurity.domain.Event');

-- SIDs
-- User specific:
insert into acl_sid (id, principal, sid) values (20, true, 'user2@example.com');

-- Role specific:
insert into acl_sid (id, principal, sid) values (21, false, 'ROLE_USER');
insert into acl_sid (id, principal, sid) values (22, false, 'ROLE_ADMIN');


-- object identity
-- Event entry for user2 SID
insert into acl_object_identity (id,object_id_identity,object_id_class,parent_object,owner_sid,entries_inheriting)
values (30,100, 10, null, 20, false);

-- Event entry for ROLE_USER SID
insert into acl_object_identity (id,object_id_identity,object_id_class,parent_object,owner_sid,entries_inheriting)
values (31,101, 10, null, 21, false);

-- Event entry for ROLE_ADMIN SID
insert into acl_object_identity (id,object_id_identity,object_id_class,parent_object,owner_sid,entries_inheriting)
values (32,102, 10, null, 21, false);


-- ACEntry list ---------------------------------
-- mask == R
-- READ Entry for Event entry for user2 SID
-- insert into acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure)
-- values(30, 1, 20, 1, true, true, true);

-- READ / WRITE Entry for Event entry for user2 SID
insert into acl_entry
(acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure)
values(30, 1, 20, 3, true, true, true);

-- the end --
