-- id: 1, username: admin, password: admin
insert into users (username, email, password, enabled) values
    ('admin', 'admin@admin', '$2a$10$2Gi3G9XaKalERoIa74OYruEHZyqSUqn10uSiOzk4PvOgL49vejna.', true);

insert into authorities (username, authority) values ('admin', 'ROLE_ADMIN');
