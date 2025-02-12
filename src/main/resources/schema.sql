DROP TABLE IF EXISTS authorization_codes CASCADE;
DROP TABLE IF EXISTS borrowing_book_transactions CASCADE;
DROP TABLE IF EXISTS members CASCADE;
DROP TABLE IF EXISTS genres_books CASCADE;
DROP TABLE IF EXISTS books CASCADE;
DROP TABLE IF EXISTS authors CASCADE;
DROP TABLE IF EXISTS genres CASCADE;

CREATE TABLE IF NOT EXISTS genres (
  genre_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  title VARCHAR(150) NOT NULL,
  description VARCHAR(1000),

  CONSTRAINT pk_genre PRIMARY KEY (genre_id)
);

CREATE TABLE IF NOT EXISTS authors (
  author_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  first_name VARCHAR(150) NOT NULL,
  last_name VARCHAR(150) NOT NULL,
  birth_date DATE,

  CONSTRAINT pk_author PRIMARY KEY (author_id)
);

CREATE TABLE IF NOT EXISTS books (
  book_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  title VARCHAR(150) NOT NULL,
  isbn VARCHAR(10) NOT NULL,
  author_id BIGINT NOT NULL,
  publication_date DATE,

  CONSTRAINT pk_book PRIMARY KEY (book_id),
  CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES authors ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS genres_books (
  genre_book_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  genre_id BIGINT NOT NULL,
  book_id BIGINT NOT NULL,

  CONSTRAINT pk_genre_book PRIMARY KEY (genre_book_id),
  CONSTRAINT fk_genre FOREIGN KEY (genre_id) REFERENCES genres ON DELETE CASCADE,
  CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES books ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS members (
  member_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  email VARCHAR(256) NOT NULL UNIQUE,
  first_name VARCHAR(256) NOT NULL,
  last_name VARCHAR(256) NOT NULL,
  phone_number VARCHAR(13) UNIQUE,

  CONSTRAINT pk_member PRIMARY KEY (member_id)
);

CREATE TABLE IF NOT EXISTS borrowing_book_transactions (
  borrowing_book_transaction_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  booking_date TIMESTAMP NOT NULL,
  planned_start_date DATE NOT NULL,
  planned_end_date DATE NOT NULL,
  actual_return_date TIMESTAMP,
  book_id BIGINT NOT NULL,
  member_id BIGINT NOT NULL,

  CONSTRAINT pk_borrowing_book_transaction PRIMARY KEY (borrowing_book_transaction_id),
  CONSTRAINT fk_member FOREIGN KEY (member_id) REFERENCES members ON DELETE CASCADE,
  CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES books ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS authorization_codes (
  authorization_code_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  random_code VARCHAR(7) NOT NULL,
  member_id BIGINT NOT NULL,

  CONSTRAINT pk_authorization_code PRIMARY KEY (authorization_code_id),
  CONSTRAINT fk_member FOREIGN KEY (member_id) REFERENCES members ON DELETE CASCADE
);