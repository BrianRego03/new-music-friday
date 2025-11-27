CREATE TABLE artists(
    id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    create_time DATE,
    name VARCHAR(255) NOT NULL,
    spotify_id VARCHAR(255) NOT NULL UNIQUE,
    img_small VARCHAR(512),
    img_medium VARCHAR(512),
    img_large VARCHAR(512)
);
