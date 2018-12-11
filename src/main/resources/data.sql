DELETE FROM users;
ALTER TABLE users AUTO_INCREMENT = 1;
ALTER TABLE phone_book AUTO_INCREMENT = 1;

INSERT INTO users (login, password, name) VALUES
  ('login1', 'password', 'My name1'),
  ('login2', 'password', 'My name2'),
  ('login3', 'password', 'My name3');

INSERT INTO phone_book
(user_id, last_name, first_name, surname, mobile_phone_number, home_phone_number, address, email) VALUES
  (1, 'LastName', 'Max', 'Surname', +380339833233, null , 'Kiev', 'email@gmail.com'),
  (1, 'LastName1', 'Den', 'Surname1', +380339833245, null , 'Odessa', 'email1@gmail.com'),
  (1, 'LastName2', 'Batman', 'Surname2', +380339836734, null , null , 'email2@gmail.com'),
  (1, 'LastName3', 'Dracula', 'Surname3', +380339833890, 0443515 , 'Kiev', 'email3@gmail.com'),
  (2, 'LastName4', 'Kirilo', 'Surname4', +380339888912, 0443515 , null , 'email4@gmail.com'),
  (2, 'LastName5', 'Golem', 'Surname5', +380339833781, null , 'Kiev', 'email5@gmail.com'),
  (3, 'LastName1', 'Den', 'Surname1', +380339833245, null , 'Odessa', 'email6@gmail.com'),
  (3, 'LastName2', 'Batman', 'Surname2', +380339836734, null , null , null ),
  (3, 'LastName3', 'Dracula', 'Surname3', +380339833890, 0443515 , 'Kiev', 'email8@gmail.com');