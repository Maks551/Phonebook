DELETE FROM users;
ALTER TABLE users AUTO_INCREMENT = 1;
ALTER TABLE phone_book AUTO_INCREMENT = 1;

INSERT INTO users (login, password, name) VALUES
  ('login1', 'password1', 'name1'),
  ('login2', 'password2', 'name2'),
  ('login3', 'password3', 'name3');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 1),
  ('ROLE_USER', 2),
  ('ROLE_USER', 3);

INSERT INTO phone_book
(user_id, last_name, first_name, surname, mobile_phone_number, home_phone_number, address, email) VALUES
  (1, 'LastName1', 'FirstName1', 'Surname1', '+380339833231', null , 'Kiev', 'email1@gmail.com'),
  (1, 'LastName2', 'FirstName2', 'Surname2', '+380339833232', null , 'Odessa', 'email2@gmail.com'),
  (1, 'LastName3', 'FirstName3', 'Surname3', '+380339833233', null , null , 'email3@gmail.com'),
  (1, 'LastName4', 'FirstName4', 'Surname4', '+380339833234', '0443515' , 'Kiev', null ),
  (2, 'LastName5', 'FirstName5', 'Surname5', '+380339833235', '0443598' , null , 'email5@gmail.com'),
  (2, 'LastName6', 'FirstName6', 'Surname6', '+380339833236', null , 'Kiev', 'email6@gmail.com'),
  (3, 'LastName7', 'FirstName7', 'Surname7', '+380339833237', '0435576' , 'Odessa', 'email7@gmail.com'),
  (3, 'LastName8', 'FirstName8', 'Surname8', '+380339833238', null , null , null ),
  (3, 'LastName9', 'FirstName9', 'Surname9', '+380339833239', '0443576' , 'Kiev', 'email9@gmail.com');