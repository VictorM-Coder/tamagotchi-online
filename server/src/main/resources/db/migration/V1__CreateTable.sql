CREATE TABLE IF NOT EXISTS tamagotchi_tb (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    birthday TEXT,
    isleeping INTEGER,
    food INTEGER,
    happy INTEGER,
    energy INTEGER
);
