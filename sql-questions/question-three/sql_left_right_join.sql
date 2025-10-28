-- LEFT JOIN Query:
-- This query selects all games and the corresponding country where the games took place.
SELECT g.yr, g.city, g.country
FROM games g
LEFT JOIN city c ON g.city = c.city;

-- RIGHT JOIN Query:
-- This query selects all cities and the corresponding games year and country.
SELECT c.city, g.yr, g.country
FROM city c
RIGHT JOIN games g ON c.city = g.city;
