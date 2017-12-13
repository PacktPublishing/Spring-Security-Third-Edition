create table calendar_user_authorities (
    id bigint identity,
    calendar_user bigint not null,
    authority varchar(256) not null,
);

-- user1@example.com
insert into calendar_user_authorities(calendar_user, authority) select id,'ROLE_USER' from calendar_users where email='user1@example.com';

-- admin1@example.com
insert into calendar_user_authorities(calendar_user, authority) select id,'ROLE_ADMIN' from calendar_users where email='admin1@example.com';
insert into calendar_user_authorities(calendar_user, authority) select id,'ROLE_USER' from calendar_users where email='admin1@example.com';

-- user2@example.com
insert into calendar_user_authorities(calendar_user, authority) select id,'ROLE_USER' from calendar_users where email='user2@example.com';
