create table users (
    id int auto_increment primary key,
    username varchar(50) not null unique,
    -- binary(60) is used because https://stackoverflow.com/a/16605381/433835
    password binary(60) not null,
    enabled boolean not null
);

create table authorities (
    username varchar(50) not null,
    authority varchar(50) not null,
    foreign key (username) references users (username),
    unique (username, authority)
);
