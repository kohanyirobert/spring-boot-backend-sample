create table lucky_numbers (
    id int auto_increment primary key,
    user_id int not null,
    number int not null check (number >= 0 and number <= 9),
    foreign key (user_id) references users (id),
    unique (user_id, number)
);

-- MySQL ignores check constraints, but we can simulate it with triggers.
-- The 45000 SQLSTATE class is used to signal errors to the calling code.
delimiter $$

create procedure check_parts (in number int)
begin
    if number < 0 or number > 9
    then
        signal sqlstate '45000' set message_text = 'check constraint on lucky_numbers.number failed';
    end if;
end
$$

create trigger check_number_before_insert before insert on lucky_numbers
for each row
begin
    call check_parts(new.number);
end
$$

create trigger check_number_before_update before update on lucky_numbers
for each row
begin
    call check_number(new.number);
end
$$

delimiter ;
