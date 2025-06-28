CREATE DATABASE student_db;
USE student_db;
CREATE TABLE IF NOT EXISTS students (
                id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(100) NOT NULL,
                roll_no VARCHAR(20) UNIQUE NOT NULL,
                department VARCHAR(50) NOT NULL,
                email VARCHAR(100),
                phone VARCHAR(15),
                marks DECIMAL(5,2)
            )