create table auth_user
(
    id int primary key not null,
    name varchar(30) null,
    age int null,
    email varchar(50) null
);
insert into auth_user(id, name, age, email)
values
    (1, 'Jone', 18, 'yunhu1@gmail.com'),
    (2, 'Jack', 20, 'yunhu2@gmail.com'),
    (3, 'Tom', 28, 'yunhu3@gmail.com'),
    (4, 'Sandy', 21, 'yunhu4@gmail.com'),
    (5, 'Billie', 24, 'yunhu5@gmail.com');