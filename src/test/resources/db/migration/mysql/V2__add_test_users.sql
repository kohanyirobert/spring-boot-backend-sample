-- Inserts 'admin' user with 'admin' password with 'ROLE_ADMIN' authority.
insert into users (username, password, enabled) values ('admin', '$2a$10$2Gi3G9XaKalERoIa74OYruEHZyqSUqn10uSiOzk4PvOgL49vejna.', true);
insert into authorities (username, authority) values ('admin', 'ROLE_ADMIN');

-- Inserts 'user' user with 'user' password with 'ROLE_USER' authority.
insert into users (username, password, enabled) values ('user', '$2y$12$eX731ZU7bI7O3bnizjX6ouDh6KQZm8yvAnhLpuKV63IHo2LTslI3W', true);
insert into authorities (username, authority) values ('user', 'ROLE_USER');
