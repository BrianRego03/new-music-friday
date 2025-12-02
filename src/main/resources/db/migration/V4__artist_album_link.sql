CREATE TABLE artist_albums(
    id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    create_time DATE,
    artist_id INT REFERENCES artists(id),
    album_id INT REFERENCES albums(id)
);
