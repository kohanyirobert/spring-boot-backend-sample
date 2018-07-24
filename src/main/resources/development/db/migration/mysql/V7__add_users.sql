insert into users (username, password, enabled) values
    -- id: 2, username: user1, password: user1
    ('user1', '$2a$10$P4xz/9ZMPE6jNoh8s8oWe.flCmmyU9wrFVi/269V4NDzn4IjZpx5S', true),
    -- id: 3, username: user2, password: user2
    ('user2', '$2a$10$WJ10QFqGcDJlZt3jkQh1Q.hl.B3XNyWt36V2gRUPm6J/A/y3e3y9W', true),
    -- id: 4, username: user3, password: user3
    ('user3', '$2a$10$kR7uwAUduMcVFfq7XI7owO6eZ2kSahpZhgprXg6pNCa7qmn/uu2G.', true),
    -- id: 5, username: user4, password: user4
    ('user4', '$2a$10$xlv.CVVcmkOjtYxdTlwxiOIjHAG0lMGQDNkyCIbhpWra11hlOq9RS', true),
    -- id: 6, username: user5, password: user5
    ('user5', '$2a$10$J7Y.VxzQvBt0ShVcDHTc9OEFpE3T7IE9nrxqivhcmnW35fbc0QtH.', true),
    -- id: 7, username: user6, password: user6
    ('user6', '$2a$10$537VvLu9KTRB8YBu91aATemszvZzXtCtOF5Busn5BfRAnQDFrSnyK', true),
    -- id: 8, username: user7, password: user7
    ('user7', '$2a$10$ePlbbxkx.BIxjJgQnq20H..HZZ1.FqSnbjADDT0uI7h4ipR1oEQ5S', true),
    -- id: 9, username: user8, password: user8
    ('user8', '$2a$10$Dp6hdSKIDIVYQW4qE18x9eakZdXGqX2IFey7KHJBrfr.0kHELL7nO', true),
    -- id: 10, username: user9, password: user9
    ('user9', '$2a$10$gy5MVQs2rspucT/pag9P2Oiw7n.FUWdIK0IJD4AU7rfQMrcqBkKra', true);

insert into authorities (username, authority) values
    ('user1', 'ROLE_USER'),
    ('user2', 'ROLE_USER'),
    ('user3', 'ROLE_USER'),
    ('user4', 'ROLE_USER'),
    ('user5', 'ROLE_USER'),
    ('user6', 'ROLE_USER'),
    ('user7', 'ROLE_USER'),
    ('user8', 'ROLE_USER'),
    ('user9', 'ROLE_USER');
