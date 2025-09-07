ALTER TABLE users DROP COLUMN id;
ALTER TABLE users ADD COLUMN id INT AUTO_INCREMENT PRIMARY KEY;

ALTER TABLE films DROP COLUMN id;
ALTER TABLE films ADD COLUMN id INT AUTO_INCREMENT PRIMARY KEY;

INSERT INTO mpa(name) VALUES('G');
INSERT INTO mpa(name) VALUES('PG');
INSERT INTO mpa(name) VALUES('PG-13');
INSERT INTO mpa(name) VALUES('R');
INSERT INTO mpa(name) VALUES('NC-17');

INSERT INTO genres(name) VALUES('Комедия');
INSERT INTO genres(name) VALUES('Драма');
INSERT INTO genres(name) VALUES('Мультфильм');
INSERT INTO genres(name) VALUES('Триллер');
INSERT INTO genres(name) VALUES('Документальный');
INSERT INTO genres(name) VALUES('Боевик');