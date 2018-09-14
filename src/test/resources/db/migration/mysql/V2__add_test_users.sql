-- id: 1, username: admin, password: admin
insert into users (username, email, password, enabled) values
    ('admin', 'admin@admin', '$2a$10$2Gi3G9XaKalERoIa74OYruEHZyqSUqn10uSiOzk4PvOgL49vejna.', true);

insert into authorities (username, authority) values ('admin', 'ROLE_ADMIN');

-- id: 2, username: user1, password: user1
insert into users (username, email, password, enabled) values
    ('user1', 'user1@user1', '$2a$10$P4xz/9ZMPE6jNoh8s8oWe.flCmmyU9wrFVi/269V4NDzn4IjZpx5S', true);

insert into authorities (username, authority) values ('user1', 'ROLE_USER');
