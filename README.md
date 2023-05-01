# attendanceSystem
This is a Java project that manages employee leave requests and attendance tracking for a company. The project uses a MySQL database to store and retrieve data related to employees, departments, attendance, leaves, and other related entities.

# To run this project, the following steps must be taken:

Clone the repository to your local machine.
Import the project into your IDE.
Create a MySQL database and update the database credentials in the DBConnection.java file.

# mysql code
CREATE DATABASE attendance;

USE attendance;

CREATE TABLE admins (
    id INT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    phone_number VARCHAR(50) NOT NULL
);

CREATE TABLE employees (
    id INT PRIMARY KEY,
    department_id INT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    phone_number VARCHAR(50) NOT NULL,
    FOREIGN KEY (department_id) REFERENCES departments(id)
);

CREATE TABLE heads (
    id INT PRIMARY KEY,
    department_id INT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    phone_number VARCHAR(50) NOT NULL,
    FOREIGN KEY (department_id) REFERENCES departments(id)
);

CREATE TABLE departments (
    id INT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    head_id INT,
    FOREIGN KEY (head_id) REFERENCES heads(id)
);

CREATE TABLE pending_employees (
    id INT PRIMARY KEY,
    department_id INT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    phone_number VARCHAR(50) NOT NULL,
    FOREIGN KEY (department_id) REFERENCES departments(id)
);

CREATE TABLE pending_heads (
    id INT PRIMARY KEY,
    department_id INT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    phone_number VARCHAR(50) NOT NULL,
    FOREIGN KEY (department_id) REFERENCES departments(id)
);

CREATE TABLE pending_leaves (
    id INT PRIMARY KEY,
    user_id INT,
    type INT NOT NULL,
    start DATE NOT NULL,
    end DATE NOT NULL,
    reason VARCHAR(200) NOT NULL,
    accepted BOOLEAN NOT NULL,
    FOREIGN KEY (user_id) REFERENCES employees(id)
);

CREATE TABLE attendances (
    id INT PRIMARY KEY,
    user_id INT,
    attendance_status BOOLEAN NOT NULL,
    date_time DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES employees(id)
);

Run the Main.java file to start the project.

# The project is structured in the following way:

The globals.java file contains global variables used throughout the project.
The DB.java file is used to establish a connection to the MySQL database.
The Main.java file contains the main function that starts the project.
The User.java file defines the User class and its properties.
The Admin.java file extends the User class and defines the properties and methods specific to Admins.
The Employee.java file extends the User class and defines the properties and methods specific to Employees.
The Department.java file defines the Department class and its properties.
The Leave.java file defines the Leave class and its properties.
The Attendance.java file defines the Attendance class and its properties.
The util package contains utility classes used throughout the project.

The project also includes JUnit test cases for the DAO classes in the test package.

For more information about the classes and methods used in this project, please contact me.
