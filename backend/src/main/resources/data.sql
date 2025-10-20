-- ========== USERS ==========
INSERT INTO users (id, nickname, password_hash, guest)
VALUES
    (1, 'Alice', '$2a$10$fakeHashForExample1', false),
    (2, 'Bob',   '$2a$10$fakeHashForExample2', false),
    (3, 'John',  '$2a$10$fakeHashForExample3', false);

-- ========== PUZZLES ==========
INSERT INTO puzzles (id, puzzle, category)
VALUES
    (1, 'The Godfather', 'MOVIE'),
    (2, 'Break a leg', 'PROVERB'),
    (3, 'Pulp Fiction', 'MOVIE'),
    (4, 'Better late than never', 'PROVERB');
