-- POSTER_TYPE
insert into poster_type(poster_type_id, type, width, height)
values (1, 'R1', 300, 250),
       (2, 'R2', 300, 516),
       (3, 'R3', 300, 782),
       (4, 'R4', 300, 1048),
       (5, 'R5', 300, 1314),
       (6, 'R6', 300, 1580);

-- USER
INSERT INTO gg_pigs_db.user (user_id, email, is_activated, is_authenticated, name, oauth_type, phone, role) VALUES (1, 'admin@gmail.com', 'Y', 'Y', null, null, null, 'ROLE_ADMIN');
INSERT INTO gg_pigs_db.user (user_id, email, is_activated, is_authenticated, name, oauth_type, phone, role) VALUES (2, 'user@gmail.com', 'Y', 'Y', null, null, null, 'ROLE_USER');

-- USER_SALT
INSERT INTO gg_pigs_db.user_salt (user_salt_id, digest, salt, user_id) VALUES (1, '$2a$10$bO1HrgoKLTnXIVKZ609dgeizNaNEAxPcH2EjDEveAvR5E2LdiQy4C', '$2a$10$bO1HrgoKLTnXIVKZ609dge', 1);
INSERT INTO gg_pigs_db.user_salt (user_salt_id, digest, salt, user_id) VALUES (2, '$2a$10$R67ep0rfzvYzaXGak470YugprSoc/eFjPZBxS/XS7tw1ZSu0gm4ky', '$2a$10$R67ep0rfzvYzaXGak470Yu', 2);

