## Filmorate Database ER-diagram

![ER-diagram](https://github.com/ianibaeva/java-filmorate/blob/main/QuickDBD-export%20(3).png)

### Примеры запросов:

#### 1. Получение пользователя:
SELECT *
FROM users
WHERE user_id = 1;

#### 2. Получение фильма:
SELECT *
FROM film 
WHERE film_id = 1;

#### 3. Получение списка фильмов определенного жанра:
SELECT film.name 
FROM film 
INNER JOIN film_genre ON film.film_id = film_genre.film_id 
INNER JOIN genre ON film_genre.genre_id = genre.genre_id 
WHERE genre.description = 'action';

#### 4. Получение списка фильмов, которые лайкнул конкретный пользователь:
SELECT film.name 
FROM film 
INNER JOIN film_likes ON film.film_id = film_likes.film_id 
INNER JOIN users ON film_likes.user_id = users.user_id 
WHERE users.user_id = 1;

#### 5. Получение списка пользователей, лайкнувших фильм:
SELECT users.name,
users.login,  
users.email  
FROM film_likes  
LEFT OUTER JOIN users ON film.user_id = users.user_id  
WHERE film.film_id = 1;

#### 6. Удаление лайка фильма от пользователя:
DELETE FROM film_likes 
WHERE film_id = 1 AND user_id = 2;

#### 7. Получение списка друзей:
SELECT users.name,  
users.login,  
users.email  
FROM friends  
LEFT OUTER JOIN users ON friends.friends_id = users.user_id  
WHERE friends.user_id = 1; 

#### 8.Получение статуса заявки в друзья:
SELECT accepted  
FROM friends   
WHERE user_id = 1 AND friend_id = 2;  