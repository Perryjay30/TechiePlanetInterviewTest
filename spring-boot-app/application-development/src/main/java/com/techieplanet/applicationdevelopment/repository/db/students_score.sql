BEGIN;

CREATE TABLE IF NOT EXISTS students_scores (
    id SERIAL PRIMARY KEY,
    student_id INT NOT NULL,
    subject_1_score NUMERIC(5,2) NOT NULL CHECK (subject_1_score BETWEEN 0 AND 100),
    subject_2_score NUMERIC(5,2) NOT NULL CHECK (subject_2_score BETWEEN 0 AND 100),
    subject_3_score NUMERIC(5,2) NOT NULL CHECK (subject_3_score BETWEEN 0 AND 100),
    subject_4_score NUMERIC(5,2) NOT NULL CHECK (subject_4_score BETWEEN 0 AND 100),
    subject_5_score NUMERIC(5,2) NOT NULL CHECK (subject_5_score BETWEEN 0 AND 100)
);

TRUNCATE students_scores RESTART IDENTITY;

INSERT INTO students_scores(student_id, subject_1_score, subject_2_score, subject_3_score, subject_4_score, subject_5_score) VALUES
(1, 80, 90, 70, 60, 85),
(1, 76, 92, 84, 88, 79),
(2, 88, 76, 92, 81, 75),
(3, 54, 67, 80, 85, 90);

COMMIT;
