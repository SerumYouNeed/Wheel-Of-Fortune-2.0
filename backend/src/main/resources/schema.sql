DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS puzzles;

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    nickname VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(100) NOT NULL,
    guest BOOLEAN NOT NULL
);

CREATE TABLE puzzles (
    ID BIGSERIAL PRIMARY KEY,
    puzzle VARCHAR(50) NOT NULL UNIQUE,
    category VARCHAR(50) NOT NULL
);

CREATE TABLE game_state (
    id SERIAL PRIMARY KEY,
        user_id INT,
        puzzle_id INT,
        masked TEXT,
        solved BOOLEAN DEFAULT FALSE
);