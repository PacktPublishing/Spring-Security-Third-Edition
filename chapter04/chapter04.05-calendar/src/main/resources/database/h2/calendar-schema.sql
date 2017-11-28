-- @see schema.sql
create table calendar_users (
    id bigint identity,
    email varchar(256) not null unique,
    password varchar(256) not null,
    first_name varchar(256) not null,
    last_name varchar(256) not null
);

create table events (
    id bigint identity,
    when timestamp not null,
    summary varchar(256) not null,
    description varchar(500) not null,
    owner bigint not null,
    attendee bigint not null,
    FOREIGN KEY(owner) REFERENCES calendar_users(id),
    FOREIGN KEY(attendee) REFERENCES calendar_users(id)
);