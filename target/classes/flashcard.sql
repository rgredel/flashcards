CREATE TABLE Users(
    id SERIAL PRIMARY KEY,
    login VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);
CREATE TABLE Deck(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    user_id INTEGER NOT NULL,
    FOREIGN KEY (user_id)
        REFERENCES Users (id)
);
CREATE TABLE Category(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);
CREATE TABLE Flashcard(
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    question VARCHAR(500) NOT NULL,
    answer VARCHAR(500) NOT NULL,
    level INTEGER NOT NULL,
    is_public BOOLEAN  NOT NULL,
    user_id INTEGER NOT NULL,
    category_id INTEGER NOT NULL,
    FOREIGN KEY (user_id)
    REFERENCES Users (id),
    FOREIGN KEY (category_id)
    REFERENCES Category (id)
);
CREATE TABLE Deck_Flashcard (
    flashcard_id INT NOT NULL,
    deck_id INT NOT NULL,

    PRIMARY KEY (flashcard_id, deck_id),
    FOREIGN KEY (deck_id)
        REFERENCES Deck (id),
    FOREIGN KEY (flashcard_id)
        REFERENCES Flashcard (id)
);

INSERT INTO Users(login, password, email) VALUES ('admin', 'pwd', 'email');
INSERT INTO Users(login, password, email) VALUES ('test', 'test', 'test@epam.com');

INSERT INTO Deck(name, user_id) VALUES ('First Deck', 1);
INSERT INTO Deck(name, user_id) VALUES ('Test Deck', 2);

INSERT INTO Category(name) VALUES ('Test Category');
INSERT INTO Category(name) VALUES ('Math');

INSERT INTO Flashcard(title, question, answer, level, is_public, user_id, category_id) VALUES ('Calculate', '2+2=', '4',0 ,true , 1, 2);
INSERT INTO Flashcard(title, question, answer, level, is_public, user_id, category_id) VALUES ('Calculate', '2+2x2=', '6',0 ,false , 2, 2);

INSERT INTO Deck_Flashcard(flashcard_id, deck_id) VALUES (1, 1);
INSERT INTO Deck_Flashcard(flashcard_id, deck_id) VALUES (2, 1);


