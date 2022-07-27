CREATE TABLE customer
(
    id       serial,
    login    varchar(30) not null unique,
    primary key (id)
);
