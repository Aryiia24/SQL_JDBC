DROP TABLE IF EXISTS students_courses;
DROP TABLE IF EXISTS groups CASCADE;
DROP TABLE IF EXISTS students CASCADE;
DROP TABLE IF EXISTS courses;


CREATE TABLE groups (
id INT PRIMARY KEY,
group_name VARCHAR(5),
size INT DEFAULT 0 
);

CREATE TABLE students (
id INT PRIMARY KEY,
first_name VARCHAR(20) NOT NULL,
last_name VARCHAR(50) NOT NULL,
group_id INT
);

CREATE TABLE courses (
id INT PRIMARY KEY,
course_name VARCHAR(50),
course_description VARCHAR(255)
);

CREATE TABLE students_courses (
student_id int REFERENCES students (id) ON UPDATE CASCADE ON DELETE CASCADE,
course_id int REFERENCES courses (id) ON UPDATE CASCADE ON DELETE CASCADE
);