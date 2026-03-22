# Storage Project 📦

This project is a hands-on practice focused on mastering various data persistence methods in Java. I am exploring file handling, database management, and building a clean application architecture.

## 🛠 Technologies
* **Java 17+**
* **Maven** (Dependency management)
* **SQLite / JDBC** (Relational database operations)
* **Java NIO** (File system operations)

---

## 🚀 Key Features

The application provides a console-based menu to manage data across different storages:

1. **SQL Database**:
    * Save notes into the `tasks` table.
    * Fetch and display all records from the database.
    * Clear the entire table with a single command.
2. **File System (TXT)**:
    * Write strings to `my_notes.txt`.
    * Read and display file content.
    * Clear the file without deleting it.
3. **Two-way Synchronization**:
    * **File ➡️ DB**: Transfer all unique lines from the file to the database (using `Batch Processing` for high performance).
    * **DB ➡️ File**: Refresh the text file with the latest data from the SQL database.

---

## 📋 How to Run
1. Clone the repository:
   ```bash
   git clone [https://github.com/Rick000s/storage-project.git](https://github.com/Rick000s/storage-project.git)