CREATE TABLE user_artists(
    id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    create_time DATE,
    user_id INT REFERENCES users(id),
    artist_id INT REFERENCES artists(id)
);