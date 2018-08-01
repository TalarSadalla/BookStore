insert into book (id, title, authors, status) values (1, 'First book', 'Jan Kowalski', 'FREE');
insert into book (id, title, authors, status) values (2, 'Second book', 'Zbigniew Nowak', 'FREE');
insert into book (id, title, authors, status) values (3, 'Third book', 'Janusz Jankowski', 'FREE');
insert into book (id, title, authors, status) values (4, 'Starter kit book', 'Kacper Ossoliński', 'FREE');
insert into book (id, title, authors, status) values (5, 'Z kamerą wśród programistów', 'Krystyna Czubówna', 'MISSING');

insert into user (id, user_name, password, enable, role) values (1, 'admin', '$2a$10$f04U6pWJjihARQEmV8I.y.Gxzhi5nEENkApQrT4hPTLS9veCAdI1C',true, 'ROLE_ADMIN');
insert into user (id, user_name, password, enable, role) values (2, 'user', '$2a$10$o7HSF8Wlamo9PXUs1vDUuuoOx6tBWeL4CrbSt.JKtUAj7d45arRMe',true, 'ROLE_USER');
insert into user (id, user_name, password, enable, role) values (3, 'talar', '$2a$10$DUCPhwr7xrjdBSF88S8.QOFBWu0nZkw7RCtUoOY7RKDlxFwX/3H/O',true, 'ROLE_USER');
insert into user (id, user_name, password, enable, role) values (4, 'marcin', '$2a$10$Ibui68uvFHgBsbDmQHnp0eqn4aLQaWr2JxRr10XhpvReAN.foxMEa',true, 'ROLE_USER');
insert into user (id, user_name, password, enable, role) values (5, 'adam', '$2a$10$qL86D75ChCWNks1pZL8KO.dqkEXjvVNI8wn3GMTn1fP4SsX.v.10K!',true, 'ROLE_USER');