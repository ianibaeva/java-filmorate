DROP TABLE IF EXISTS mpa_rating CASCADE ;
DROP TABLE IF EXISTS films CASCADE ;
DROP TABLE IF EXISTS genres CASCADE ;
DROP TABLE IF EXISTS film_genre CASCADE ;
DROP TABLE IF EXISTS users CASCADE ;
DROP TABLE IF EXISTS film_likes CASCADE ;
DROP TABLE IF EXISTS friends CASCADE ;


CREATE TABLE IF NOT EXISTS mpa_rating
(
    mpa_id integer,
    description VARCHAR,
    CONSTRAINT mpa_id PRIMARY KEY (mpa_id)
);

CREATE TABLE IF NOT EXISTS films
(
    film_id integer generated by default as identity primary key,
    name varchar NOT NULL,
    description varchar(200),
    release_date date,
    duration integer,
    rate integer,
    mpa integer REFERENCES mpa_rating (mpa_id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS genres
(
    genre_id integer,
    description varchar,
    CONSTRAINT id PRIMARY KEY (genre_id)
);

CREATE TABLE IF NOT EXISTS film_genre
(
    film_id integer REFERENCES films (film_id) ON DELETE CASCADE,
    genre_id integer REFERENCES genres (genre_id) ON DELETE CASCADE,
    constraint pair unique (film_id, genre_id)
);

CREATE TABLE IF NOT EXISTS users
(
    user_id integer generated by default as identity primary key,
    e_mail varchar,
    login varchar NOT NULL,
    name varchar,
    birthday date
);

CREATE UNIQUE INDEX idx_users_email_login ON users (
  e_mail,
  login
);

CREATE TABLE IF NOT EXISTS film_likes
(
    film_id integer REFERENCES films (film_id) ON DELETE CASCADE,
    user_id integer REFERENCES users (user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS friends
(
    user_id integer REFERENCES users (user_id) ON DELETE CASCADE,
    friend_id integer REFERENCES users (user_id) ON DELETE CASCADE
);
