## 🗂️ Project Structure

```bash
BloodDonorManagement/
├── Donor.java              # Donor entity class
├── DatabaseHandler.java    # DB operations using JDBC
├── BloodDonorApp.java      # Main application (menu-driven)
├── README.md               # Project summary (this file)
└── blood_donor_db.db       # SQLite DB file (or schema.sql for MySQL)




# Blood Donor Management System - Database Setup

## Prerequisites
- Install MySQL Community Server
- Install Java JDK 8+ and VS Code
- Download MySQL Connector/J (JAR file)

## Database Setup
1. Open MySQL command line or MySQL Workbench.
2. Run the following commands:
```sql
CREATE DATABASE IF NOT EXISTS bdms;
USE bdms;
CREATE TABLE IF NOT EXISTS donors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(10),
    blood_group VARCHAR(5) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    city VARCHAR(50),
    last_donation_date DATE NULL
);
```

## Project Structure
```
JAVA PJT/
│
├── src/
│   └── com/
│       └── bdms/
│           └── util/
│               └── DBConnection.java
├── lib/
│   └── mysql-connector-j.jar
└── README.md
```

## How to Run
1. Place `mysql-connector-j.jar` inside `lib/`
2. Open VS Code, select Java extension.
3. Compile DBConnection.java:
   ```bash
   javac -cp lib/mysql-connector-j.jar src/com/bdms/util/DBConnection.java
   ```
4. Run the test (Windows):
   ```bash
   java -cp "src;lib/mysql-connector-j.jar" com.bdms.util.DBConnection
   ```
   Run the test (macOS/Linux):
   ```bash
   java -cp "src:lib/mysql-connector-j.jar" com.bdms.util.DBConnection
   ```
