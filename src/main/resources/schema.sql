DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS phone_book;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  login VARCHAR(100) NOT NULL,
  password VARCHAR(100) NOT NULL,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  name VARCHAR(150) NOT NULL
);
CREATE UNIQUE INDEX users_unique_login_idx ON users (login);

CREATE TABLE user_roles
(
  user_id INT NOT NULL,
  role VARCHAR(50) NOT NULL,
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE phone_book (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  first_name VARCHAR(100) NOT NULL,
  surname VARCHAR(100) NOT NULL,
  mobile_phone_number VARCHAR(50) NOT NULL,
  home_phone_number VARCHAR(50),
  address VARCHAR(100),
  email VARCHAR(100),
  FOREIGN KEY phone_book_fk (user_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX phone_books_mobile_phone_number_idx ON phone_book (mobile_phone_number, user_id);