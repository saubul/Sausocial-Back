delete from tokens;
delete from users_roles;
delete from users;

insert into users(id, name, surname, country, birthday, date_created, username, password, email, enabled)
values(1, 'Name', 'Surname', 'Russia', '2000-09-11', '1999-09-11', 'Saubul', '$2a$10$zcC39Smvy/VZwnitxAhBYessNX9Mxz.nLlzwHKof1RdrFEi..s1pG', 'email@mail.ru', FALSE);

alter sequence user_id_seq restart with 10;