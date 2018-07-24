create table lucky_numbers (
    id int auto_increment primary key,
    user_id int not null,
    number int not null check (number >= 0 && number < 10),
    foreign key (user_id) references users (id),
    unique (user_id, number)
)
