CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    name varchar(100) NOT NULL,
    email varchar(100) NOT NULL,
    login varchar(100) NOT NULL,
    birthday DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS friendship (
    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
    friend_id INTEGER REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS films (
    id SERIAL PRIMARY KEY,
    name varchar(100) NOT NULL,
    description varchar(200) NOT NULL,
    release_date DATE NOT NULL,
    duration integer NOT NULL,
    mpa_id integer
);

CREATE TABLE IF NOT EXISTS genres (
    id SERIAL PRIMARY KEY,
    name varchar(40) NOT NULL
);

CREATE TABLE IF NOT EXISTS film_genres (
    film_id INTEGER REFERENCES films(id) ON DELETE CASCADE,
    genre_id INTEGER REFERENCES genres(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS likes (
    film_id INTEGER REFERENCES films(id) ON DELETE CASCADE,
    user_id INTEGER REFERENCES users(id) ON DELETE RESTRICT
);


CREATE TABLE IF NOT EXISTS mpa (
    id SERIAL PRIMARY KEY,
    name varchar(40) NOT NULL
);