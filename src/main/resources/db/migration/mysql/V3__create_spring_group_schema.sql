create table groups (
    id int auto_increment primary key,
    group_name varchar(50) not null
);

create table group_authorities (
    group_id int not null,
    authority varchar(50) not null,
    foreign key (group_id) references groups (id)
);

create table group_members (
    id int auto_increment primary key,
    username varchar(50) not null,
    group_id int not null,
    foreign key (group_id) references groups (id)
);
