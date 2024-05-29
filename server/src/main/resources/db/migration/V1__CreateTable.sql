CREATE TABLE IF NOT EXISTS tamagotchi_tb (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    birthday TEXT,
    isSleeping INTEGER,
    food INTEGER,
    happy INTEGER,
    energy INTEGER,
    startedSleeping TEXT
);

CREATE TABLE IF NOT EXISTS history_action_tb (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL,
    date_time TEXT NOT NULL,
    tamagotchi_id INT NOT NULL,
    action TEXT NOT NULL
)