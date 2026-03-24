package cz.upce.fei;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    public static void simpleDataBase() {
        System.out.println("----------WRITING DATABASE-----------");
        // database

        String url = "jdbc:sqlite:my_database.db"; // створення файлу та збереження його адресату

        try (Connection conn = DriverManager.getConnection(url)) { // тут підключення зєднання до (url)
            if (conn != null) {
                System.out.println("Підключено до бази!\n");

                java.sql.Statement statement = conn.createStatement(); // створення курьєра

                // sql запит на створення таблиці з колонками та автоматичним ІД
                statement.execute(""" 
    CREATE TABLE IF NOT EXISTS tasks (
        id INTEGER PRIMARY KEY AUTOINCREMENT, 
        todo TEXT NOT NULL
    )
    """);
                System.out.println("Таблиця створена!\n");

//                statement.execute("DELETE FROM tasks"); // очистка перед додаванням нових

                statement.execute("INSERT INTO tasks (todo) VALUES ('First')");
                statement.execute("INSERT INTO tasks (todo) VALUES ('Second')");
            } else {
                System.out.println("Не підключено до бази!");
            }

        } catch (SQLException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    public static void readFromSimpleDataBase() {
        System.out.println("----------READING DATABASE-----------");
        String url = "jdbc:sqlite:my_database.db";

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Підключено до бази!");

                java.sql.Statement statement = conn.createStatement();
                java.sql.ResultSet resultSet = statement.executeQuery("SELECT * FROM tasks");

                while (resultSet.next()) {
                    String taskText = resultSet.getString("todo");
                    System.out.println(taskText);
                }

            } else {
                System.out.println("Не підключено до бази!");
            }
        } catch (SQLException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    public static void clearDatabase() {
        String url = "jdbc:sqlite:my_database.db";
        String sql = "DELETE FROM tasks"; // Видаляє всі записи з таблиці tasks

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            int rowsDeleted = stmt.executeUpdate(sql);
            System.out.println("--- Базу очищено! Видалено рядків: " + rowsDeleted + " ---");

        } catch (SQLException e) {
            System.out.println("Помилка при очищенні бази: " + e.getMessage());
        }
    }

    public static List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        String sql = "SELECT * FROM tasks"; // таблиця
        String url = "jdbc:sqlite:my_database.db";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // Ось вона! Твоя "цеглина" Task у дії:
                Task task = new Task(rs.getInt("id"), rs.getString("todo"));
                taskList.add(task);
            }
        } catch (SQLException e) {
            System.out.println("Помилка БД: " + e.getMessage());
        }
        return taskList; // Повертаємо цілий "кошик" із задачами
    }
}
