MERGE INTO GENRES (GENRE_ID, DESCRIPTION)
    VALUES (1, 'Комедия'),
           (2, 'Драма'),
           (3, 'Мультфильм'),
           (4, 'Триллер'),
           (5, 'Документальный'),
           (6, 'Боевик');

MERGE INTO MPA_RATING (MPA_ID, DESCRIPTION)
    VALUES (1, 'G'),
           (2, 'PG'),
           (3, 'PG-13'),
           (4, 'R'),
           (5, 'NC-17');

INSERT INTO FILMS (NAME, DESCRIPTION, RELEASE_DATE, DURATION, RATE, MPA)
    VALUES ('Большой куш', 'Противодействие цыган', '2000-08-23', 104, 6, 4),
           ('Shrek', 'Its Big!', '2001-04-22', 90, 2, 2),
           ('Shrek 2', 'Not so far, far away...', '2004-05-08', 93, 3, 1),
           ('Shrek the Third', 'Да приидет царствие его', '2007-05-06', 93, 5, 5),
           ('Shrek Forever After', 'Последняя глава', '2010-04-21', 90, 3, 5);

INSERT INTO FILM_GENRE (FILM_ID, GENRE_ID)
    VALUES (1, 6),
           (1, 2),
           (2, 4),
           (2, 5),
           (2, 2),
           (3, 5),
           (3, 1),
           (4, 4),
           (5, 2),
           (5, 6);

INSERT INTO USERS (E_MAIL, LOGIN, NAME, BIRTHDAY)
    VALUES ('qwe@mail.ru', 'Leonardo', 'Blue', '2004-10-11'),
           ('rty@mail.ru', 'Michelangelo', 'Orange', '2004-10-11'),
           ('uio@mail.ru', 'Donatello', 'Purple', '2004-10-11'),
           ('asd@mail.ru', 'Raphael', 'Red', '2004-10-11'),
           ('fgh@mail.ru', 'Splinter', 'Master', '1960-10-11');

INSERT INTO FRIENDS (USER_ID, FRIEND_ID)
    VALUES (2, 3),
           (2, 4),
           (2, 1),
           (4, 2),
           (4, 3),
           (1, 4);