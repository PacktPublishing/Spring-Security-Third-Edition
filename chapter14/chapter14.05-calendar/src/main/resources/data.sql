-- In Spring Boot using Spring Data, we use ~/src/main/resources/data.sql to seed data.
-- We no longer have to use ~/src/main/resources/database/h2/calendar-data.sql to seed data.

-- New Calendar Users --

-- Password for user1 was 'user1'
insert into calendar_users(id,email,password,first_name,last_name) values (0, 'user1@example.com','$2a$04$qr7RWyqOnWWC1nwotUW1nOe1RD5.mKJVHK16WZy6v49pymu1WDHmi','User','1');
-- Password for admin was 'admin'
insert into calendar_users(id,email,password,first_name,last_name) values (1,'admin1@example.com','$2a$04$0CF/Gsquxlel3fWq5Ic/ZOGDCaXbMfXYiXsviTNMQofWRXhvJH3IK','Admin','1');
-- Password for user2 was 'user2'
insert into calendar_users(id,email,password,first_name,last_name) values (2,'user2@example.com','$2a$04$PiVhNPAxunf0Q4IMbVeNIuH4M4ecySWHihyrclxW..PLArjLbg8CC','User2','2');


-- ROLES --
insert into role(id, name) values (0, 'ROLE_USER');
insert into role(id, name) values (1, 'ROLE_ADMIN');


-- User have one role
insert into user_role(user_id,role_id) values (0, 0);

-- Admin has two roles
insert into user_role(user_id,role_id) values (1, 0);
insert into user_role(user_id,role_id) values (1, 1);

-- Events --
insert into events (id,when,summary,description,owner,attendee) values (100,'2017-07-03 20:30:00','Birthday Party','This is going to be a great birthday',0,1);
insert into events (id,when,summary,description,owner,attendee) values (101,'2017-12-23 13:00:00','Conference Call','Call with the client',2,0);
insert into events (id,when,summary,description,owner,attendee) values (102,'2017-09-14 11:30:00','Vacation','Paragliding in Greece',1,2);


-- the end --
