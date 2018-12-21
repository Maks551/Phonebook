DELETE FROM users;
ALTER TABLE users AUTO_INCREMENT = 1;
ALTER TABLE phone_book AUTO_INCREMENT = 1;

INSERT INTO users (login, password, name) VALUES
  ('login1', '{noop}password1', 'name1'),
  ('login2', '{noop}password2', 'name2'),
  ('login3', '{noop}password3', 'name3'),
  ('admin', '{noop}admin', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('USER', 1),
  ('USER', 2),
  ('USER', 3),
  ('USER', 4),
  ('ADMIN', 4);

INSERT INTO phone_book
(user_id, last_name, first_name, surname, mobile_phone_number, home_phone_number, address, email) VALUES
  (1, 'LastName1', 'FirstName1', 'Surname1', '+380339833231', '' , 'Kiev', 'email1@gmail.com'),
  (1, 'LastName2', 'FirstName2', 'Surname2', '+380339833232', '' , 'Odessa', 'email2@gmail.com'),
  (1, 'LastName3', 'FirstName3', 'Surname3', '+380339833233', '' , '' , 'email3@gmail.com'),
  (1, 'LastName4', 'FirstName4', 'Surname4', '+380339833234', '0443515' , 'Kiev', '' ),
  (2, 'LastName5', 'FirstName5', 'Surname5', '+380339833235', '0443598' , '' , 'email5@gmail.com'),
  (2, 'LastName6', 'FirstName6', 'Surname6', '+380339833236', '' , 'Kiev', 'email6@gmail.com'),
  (3, 'LastName7', 'FirstName7', 'Surname7', '+380339833237', '0435576' , 'Odessa', 'email7@gmail.com'),
  (3, 'LastName8', 'FirstName8', 'Surname8', '+380339833238', '' , '' , '' ),
  (3, 'LastName9', 'FirstName9', 'Surname9', '+380339833239', '0443576' , 'Kiev', 'email9@gmail.com');