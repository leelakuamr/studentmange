DROP TABLE IF EXISTS students;

CREATE TABLE IF NOT EXISTS students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    course VARCHAR(50) NOT NULL,
    age INT NOT NULL CHECK (age > 0 AND age <= 150),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO students (name, email, course, age) VALUES
('John Doe', 'john.doe@example.com', 'Computer Science', 20),
('Jane Smith', 'jane.smith@example.com', 'Electrical Engineering', 22),
('Bob Johnson', 'bob.johnson@example.com', 'Mechanical Engineering', 21),
('Alice Williams', 'alice.williams@example.com', 'Computer Science', 19);

SELECT * FROM students;

