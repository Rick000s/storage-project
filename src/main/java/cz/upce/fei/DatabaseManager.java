package cz.upce.fei;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:my_database.db";

    public static void simpleDataBase() {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS tasks (id INTEGER PRIMARY KEY AUTOINCREMENT, todo TEXT NOT NULL)");

            String sql = "INSERT INTO tasks (todo) VALUES (?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, "First task");
                pstmt.executeUpdate();
                pstmt.setString(1, "Second task");
                pstmt.executeUpdate();
            }
            System.out.println("db ready and tasks inserted");
        } catch (SQLException e) {
            System.out.println("db error " + e.getMessage());
        }
    }

    public static void readFromSimpleDataBase() {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT todo FROM tasks")) {
            while (rs.next()) {
                System.out.println(rs.getString("todo"));
            }
        } catch (SQLException e) {
            System.out.println("db error " + e.getMessage());
        }
    }

    public static void clearDatabase() {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            int rows = stmt.executeUpdate("DELETE FROM tasks");
            System.out.println("db cleared rows deleted " + rows);
        } catch (SQLException e) {
            System.out.println("db error " + e.getMessage());
        }
    }

    public static List<Task> getAllTasks() {
        List<Task> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM tasks")) {
            while (rs.next()) {
                list.add(new Task(rs.getInt("id"), rs.getString("todo")));
            }
        } catch (SQLException e) {
            System.out.println("db error " + e.getMessage());
        }
        return list;
    }
}