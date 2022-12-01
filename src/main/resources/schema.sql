create table client (
	id uuid not null,
	name varchar(50) not null,
	phone_number char(11),
	birth char(8),
	member_id varchar(10),
	constraint constraint_client_uk unique (member_id),
	constraint constraint_client_pk primary key (id,name)
);

create table product (
	name varchar(50) not null,
	term smallint not null,
	constraint  constraint_product_pk primary key (name, term)
);


create table cabinet (
	id smallint not null,
	start_at char(8) not null,
	expire_at char(8) not null,
	constraint constraint_cabinet_pk primary key (id)
);

create table member (
	client uuid not null,
	cabinet smallint,
	constraint constraint_member_uk unique (cabinet),
	constraint constraint_member_pk primary key(client)
);

create table member_product (
	client uuid not null,
	product varchar(50) not null,
	start_at char(8) not null,
	expire_at char(8) not null,
	constraint constraint_member_product primary key (client, product, start_at, expire_at)
);

create table counsel (
    comment varchar(1000) not null,
    create_at timestamp default current_timestamp not null,
    client uuid not null,
    constraint constraint_counsel primary key(comment, create_at)
);