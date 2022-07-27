CREATE TABLE file
(
    id          serial,
    customer_id bigint unsigned not null,
    name        varchar(255)    not null,
    path        text            not null,
    status      varchar(20)     not null,

    primary key (id),
    foreign key (customer_id) references customer (id)
);


