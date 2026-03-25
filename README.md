# Storage Project 📦

[![Java Version](https://img.shields.io/badge/Java-17%2B-blue)](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

This project is a hands-on practice focused on mastering various data persistence methods in Java. It explores file handling, database management, and building a clean, scalable application architecture.

---

## 🚀 Key Features

The application provides a console-based menu to manage data across different storages:

### 1. SQL Database Management
* **Insert/Read**: Save notes into the `tasks` table and fetch them via JDBC.
* **Maintenance**: Clear the entire table with a single command.
* **Performance**: Optimized SQL queries for data handling.

### 2. File System Operations (TXT/NIO)
* **I/O Operations**: Write strings to `my_notes.txt` and read them using Java NIO.
* **Cleanup**: Clear file content without deleting the file itself.

### 3. Smart Synchronization
* **File ➡️ DB**: Transfer unique lines from the file to the database using `Batch Processing`.
* **DB ➡️ File**: Refresh the text file with the latest data from the SQL database.

---

## 🛠 Technologies & Tools
* **Language**: Java 17+
* **Build Tool**: Maven
* **Database**: SQLite (JDBC)
* **JSON Support**: Google GSON
* **Exec**: Launch4j (for Windows .exe wrapper)

---

## 📥 Download & Quick Start

You don't need to build the project yourself. You can download ready-to-use versions from the [**Releases**](https://github.com/Rick000s/storage-project/releases) section.

* **For Windows:** Download `Storage.exe` and run it.
* **For macOS / Linux:** Download the `.jar` file and run it via terminal:
  ```bash
  java -jar DatabaseLearning-1.0-SNAPSHOT.jar