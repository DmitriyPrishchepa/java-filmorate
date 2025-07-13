# java-filmorate
Template repository for Filmorate project.

![Database diagram](https://github.com/DmitriyPrishchepa/java-filmorate/blob/main/src/main/resources/images/db_diagram.png)

## Операции с пользователями

### Запрос на добавление пользователя
(* подразумевается, что id автоинкремент; значения friend_id - id друзей и подтверждения дружбы friendship_confirmed пока будут заданы по умолчанию либо null *)
INSERT INTO users(name, email, login, birthday)
VALUES('UserName', 'us@mail.ru', 'user10', '11.07.2000')

### Запрос на апдейт пользователя
UPDATE users 
SET name = 'new_Name', email = 'new email', login = 'new login', bitrhday = '1994-20-10' 
WHERE user_id = 10;

### Запрос на удаление пользователя
DELETE FROM users WHERE user_id = 10;

### Запрос на получение всех данных пользователя по id
SELECT * 
FROM users
WHERE user_id = 10;

### Запрос на получение всех пользователей
SELECT * 
FROM users;

### Запрос на добавление в друзья
UPDATE users 
SET friend_id = 11, friendship_confirmed = 'true'
WHERE user_id = 10;

### Запрос на получение друзей пользователя
SELECT *
FROM users as u
JOIN users as fr ON users.user_id = fr.friend_id
WHERE fr.user_id = u.user_id 
AND fr.friendship_confirmed = true; 

### Запрос на удаление из друзей
UPDATE users
SET friend_id = NULL, friendship_confirmed = false
WHERE user_id = 10;

## Операции с фильмами

### Запрос на добавление фильма 
(* Жанры хранятся в отдельной таблице genre *)
INSERT INTO films (name, description, release_date, duration, likes, mpa_id)
VALUES ('Green book', 'Some description', '2018-09-11', '130', 3);

INSERT INTO genre (filmd_id, genre_id)
VALUES (1, 2), (1, 3); (* фильм получает 2 жанра *)

### Запрос на апдейт фильма
UPDATE films
SET name = 'Star Trek'
WHERE film_id = 5;

### Запрос на удаление фильма
DELETE FROM films 
WHERE film_id = 5;

### Запрос на получение всех данных фильм по id
SELECT * 
FROM films
WHERE film_id = 5;

### Запрос на получение всех фильмов
SELECT *
FROM films;

### Запрос на like фильма
UPDATE films 
SET likes = likes + 1 
WHERE film_id = 5;

### Запрос на unlike фильма
UPDATE films
SET likes = likes - 1 
WHERE film_id = 5;

### Запрос на 10 самых популярных фильмов по количеству лайков
SELECT name, 
       likes
FROM films
GROUP BY name
ORDER BY likes DESC
LIMIT 10;
    
