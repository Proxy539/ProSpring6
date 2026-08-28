INSERT INTO SINGER (id, first_name, last_name, pseudonym, genre, birth_date) VALUES
    (1, 'John', 'Mayer', NULL, 'Blues Rock', '1977-10-16'),
    (2, 'Farrokh', 'Bulsara', 'Freddie Mercury', 'Rock', '1946-09-05'),
    (3, 'Beyonce', 'Knowles-Carter', 'Beyonce', 'R&B', '1981-09-04');

INSERT INTO INSTRUMENT (instrument_id) VALUES
    ('GUITAR'),
    ('VOCALS'),
    ('PIANO'),
    ('BASS'),
    ('DRUMS');

INSERT INTO SINGER_INSTRUMENT (singer_id, instrument_id) VALUES
    (1, 'GUITAR'),
    (1, 'VOCALS'),
    (2, 'VOCALS'),
    (2, 'PIANO'),
    (3, 'VOCALS');

INSERT INTO AWARD (id, singer_id, `year`, type, item_name, award_name) VALUES
    (1, 1, 2003, 'Best Male Pop Vocal Performance', 'Your Body Is a Wonderland', 'Grammy Award'),
    (2, 1, 2007, 'Best Pop Collaboration with Vocals', 'What Say You', 'Grammy Award'),
    (3, 2, 1990, 'Outstanding British Group', 'Queen', 'Brit Award'),
    (4, 3, 2010, 'Album of the Year', 'I Am... Sasha Fierce', 'Grammy Award'),
    (5, 3, 2017, 'Best Urban Contemporary Album', 'Lemonade', 'Grammy Award');
