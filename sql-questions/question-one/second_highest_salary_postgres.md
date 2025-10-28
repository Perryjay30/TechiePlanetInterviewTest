
# Second-Highest Salary (PostgreSQL)

This README explains and **demonstrates PostgreSQL-dialect queries** that correctly return the **second-highest distinct salary** from table `emp`, even when the highest salary appears multiple times.

> Example: salaries = `{100, 80, 100}` → second-highest = **80**.

---

## Schema

```sql
CREATE TABLE IF NOT EXISTS emp (
  id     INT PRIMARY KEY,
  name   VARCHAR(30) NOT NULL,
  salary NUMERIC
);
```

---

## Correct PostgreSQL Queries

### Q1 — Distinct + ORDER BY + LIMIT/OFFSET
```sql
SELECT DISTINCT salary
FROM emp
ORDER BY salary DESC
LIMIT 1 OFFSET 1;
```
- Sorts **distinct** salaries descending, then picks the second row.

### Q2 — MAX of values strictly less than global MAX
```sql
SELECT MAX(salary)
FROM emp
WHERE salary < (SELECT MAX(salary) FROM emp);
```
- Finds the largest salary **strictly less** than the overall maximum.

### Q3 — Top 2 distinct, pick the smaller
```sql
SELECT salary
FROM (
  SELECT DISTINCT salary
  FROM emp
  ORDER BY salary DESC
  LIMIT 2
) AS emp
ORDER BY salary
LIMIT 1;
```
- Selects the top two **distinct** salaries, then returns the smaller → second-highest.

## Demo Data & Expected Result

```sql
TRUNCATE emp;
INSERT INTO emp (id, name, salary) VALUES
(1, 'A', 100),
(2, 'B', 80),
(3, 'C', 100);

-- Expected result of Q1/Q2/Q3:
-- 80
```
