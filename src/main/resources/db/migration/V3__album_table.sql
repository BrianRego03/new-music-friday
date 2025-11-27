CREATE TABLE albums(
    id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    create_time DATE,
    name VARCHAR(255),
    spotify_id VARCHAR(255),
    img_small VARCHAR(512),
    img_medium VARCHAR(512),
    img_large VARCHAR(512),
    track_length INT,
    release_date DATE,
    album_type VARCHAR(20)
);