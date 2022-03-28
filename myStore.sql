create database myStore;
use myStore;

CREATE TABLE IF NOT EXISTS users (
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
name VARCHAR(40) NOT NULL, 
email VARCHAR(100) NOT NULL UNIQUE, 
phone VARCHAR(20), 
address VARCHAR(200), 
password VARCHAR(20) NOT NULL
) ;