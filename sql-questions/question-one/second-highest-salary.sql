-- Demo data
DROP TABLE IF EXISTS emp;
CREATE TABLE emp (
                     id INT PRIMARY KEY,
                     name VARCHAR(30) NOT NULL,
                     salary DECIMAL(18,2)
);

INSERT INTO emp (id, name, salary) VALUES
                                       (1, 'A', 100),
                                       (2, 'B', 80),
                                       (3, 'C', 100);

-- Correct Query 1
-- Distinct salaries, sort desc, take second
SELECT DISTINCT salary FROM emp ORDER BY salary DESC LIMIT 1 OFFSET 1;

-- Correct Query 2
-- Max salary strictly less than overall max
SELECT MAX(salary) FROM emp WHERE salary < (SELECT MAX(salary) FROM emp);

-- Correct Query 3
-- Take top two distinct, then smallest of those two
SELECT salary
FROM (SELECT DISTINCT salary FROM emp ORDER BY salary DESC LIMIT 2) AS emp
ORDER BY salary
    LIMIT 1;

-- Expected result for demo data: 80
