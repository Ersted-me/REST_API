create table events
(
    id          serial,
    customer_id bigint unsigned not null,
    file_id     bigint unsigned not null,
    date        datetime        not null,
    action      varchar(30)     not null,

    primary key (id),
    foreign key (customer_id) references customer (id),
    foreign key (file_id) references file (id)
);