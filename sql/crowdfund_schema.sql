CREATE DATABASE IF NOT EXISTS crowdfund_db;
USE crowdfund_db;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(100),
    role VARCHAR(50)
);

CREATE TABLE campaigns (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200),
    description TEXT,
    goal_amount DOUBLE,
    current_amount DOUBLE,
    creator_id INT,
    FOREIGN KEY (creator_id) REFERENCES users(id)
);
