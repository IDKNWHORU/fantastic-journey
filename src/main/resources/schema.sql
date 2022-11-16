create table client(
    id varchar(50) not null,
    passwd varchar(80) not null,
    phone_number char(11) not null,
    birth datetime not null default current_timestamp(),
    portrait_hot varchar(80) null,
    create_at datetime not null default current_timestamp(),
    expired_at datetime not null default current_timestamp(),
    primary key (id),
    constraint unq_user_phone_number unique(phone_number)
);
--insert into client values('jh9341', '1234', '01000000000', '2002-');