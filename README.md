# Blood-donation-csea
# 🩸 Blood Donor Management System

## 📌 Overview

A Java-based console application to register, manage, and search for blood donors using a database. This project is designed with object-oriented programming principles and integrates JDBC for persistent storage. It aims to support safe and efficient blood donation practices in local communities.

---

## 🎯 Objectives

- Provide a searchable database of donors by blood group and city
- Check donation eligibility (≥90 days since last donation)
- Enable donor record creation, viewing, updating, and deletion
- Store data securely using an SQL database via JDBC

---

## 🛠️ Features

- Donor Registration with validation
- Search Donors by blood group and city
- View All Donors
- Update/Delete Donors by ID
- Donation Eligibility Checker
- Persistent storage using SQLite/MySQL database
- Modular design using Java OOP

---

## 🧩 Technologies Used

| Component        | Description             |
|------------------|--------------------------|
| Language         | Java (OOP, Console App)  |
| Database         | SQLite / MySQL (JDBC)    |
| IDE              | VS Code / IntelliJ IDEA  |
| Version Control  | Git & GitHub             |

---

## 🗂️ Project Structure

```bash
BloodDonorManagement/
├── Donor.java              # Donor entity class
├── DatabaseHandler.java    # DB operations using JDBC
├── BloodDonorApp.java      # Main application (menu-driven)
├── README.md               # Project summary (this file)
└── blood_donor_db.db       # SQLite DB file (or schema.sql for MySQL)
