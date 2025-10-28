--Sample data
CREATE TABLE sessions (
                          id INTEGER NOT NULL PRIMARY KEY,
                          userId INTEGER NOT NULL,
                          duration DECIMAL NOT NULL
);

INSERT INTO sessions(id, userId, duration) VALUES(1, 1, 10);
INSERT INTO sessions(id, userId, duration) VALUES(2, 2, 18);
INSERT INTO sessions(id, userId, duration) VALUES(3, 1, 14);

--Sql query
SELECT userId, AVG(duration) AS AverageDuration
FROM sessions
GROUP BY userId
HAVING COUNT(id) > 1;


--Expected Output
UserId  AverageDuration
------------------------
1       12
