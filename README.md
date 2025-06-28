Smart Student Management System
A Java-based console application for managing student records with MySQL database integration.
Table of Contents

Overview
Features
Prerequisites
Installation
Database Setup
Usage
Project Structure
Dependencies
Contributing
License

Overview
The Smart Student Management System is a console-based application designed to manage student records. It provides administrative functionalities such as adding, updating, deleting, and searching student records, as well as generating statistics. The application uses MySQL for persistent storage and follows a layered architecture with DAO (Data Access Object) and service layers.
Features

Admin Authentication: Secure login with predefined admin credentials.
CRUD Operations:
Add new students with validation for unique roll numbers and marks range (0-100).
View all students or retrieve specific students by ID or roll number.
Update existing student records.
Delete students with confirmation.


Search Functionality:
Search students by name (partial match), department, or marks range.


Statistics:
Display total number of students.
Show highest and lowest marks.
Provide department-wise student count.


User Interface: Interactive console-based UI with clear menus and input validation.

Prerequisites

Java Development Kit (JDK) 8 or higher
MySQL Server 5.7 or higher
MySQL Connector/J (JDBC driver for MySQL)
Maven or Gradle (optional, for dependency management)

Installation

Clone the Repository:
git clone https://github.com/AdityaBabar08/SmartStudent.git
cd smart-student-management-system


Add MySQL Connector/J:

Download the MySQL Connector/J from MySQL official site.
Add the JAR file to your project's classpath or include it in your build tool configuration (e.g., Maven/Gradle).


Build the Project:

If using Maven, add the following dependency to pom.xml:<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>


Run mvn clean install to build the project.


Compile and Run:

Compile the Java files:javac *.java


Run the application:java Main





Database Setup

Install MySQL:

Ensure MySQL Server is installed and running.


Create Database:

Log in to MySQL:mysql -u root -p


Create the database and table:CREATE DATABASE student_db;
USE student_db;
CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    roll_no VARCHAR(20) NOT NULL UNIQUE,
    department VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(15),
    marks DOUBLE NOT NULL
);




Configure Database Connection:

Update the DatabaseConnection.java file with your MySQL credentials:private static final String URL = "jdbc:mysql://localhost:3306/student_db";
private static final String USERNAME = "your-username";
private static final String PASSWORD = "your-password";





Usage

Start the Application:

Run the Main class to launch the application.
Log in with the default admin credentials:
Username: admin
Password: admin123




Main Menu Options:

Add a new student with details like name, roll number, department, email, phone, and marks.
View all students in a tabulated format.
Update student details by roll number.
Delete a student by roll number with confirmation.
Search students by roll number, name, department, or marks range.
View statistics like total students, highest/lowest marks, and department-wise counts.
Exit the application.


Example Interaction:
🎓 Welcome to Smart Student Management System
========== ADMIN LOGIN ==========
Username: admin
Password: admin123
Login successful!

========== MAIN MENU ==========
1. Add Student
2. View All Students
3. Update Student
4. Delete Student
5. Search Students
6. Statistics
7. Exit
===============================



Project Structure
smart-student-management-system/
├── DatabaseConnection.java  # Handles database connection
├── Student.java            # Student entity class
├── StudentDAO.java         # Data Access Object for database operations
├── AdminService.java       # Business logic and service layer
├── UI.java                # Console-based user interface
├── Main.java              # Application entry point
└── README.md              # Project documentation

Dependencies

Java: Version 8 or higher
MySQL Connector/J: For database connectivity
MySQL Server: For persistent storage

Contributing
Contributions are welcome! Please follow these steps:

Fork the repository.
Create a new branch (git checkout -b feature-branch).
Make your changes and commit (git commit -m 'Add new feature').
Push to the branch (git push origin feature-branch).
Create a Pull Request.

License
This project is licensed under the MIT License.