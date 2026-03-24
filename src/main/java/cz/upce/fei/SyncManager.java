package cz.upce.fei;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class SyncManager {
    private static final String DB_URL = "jdbc:sqlite:my_database.db";
    private static final String FILE_NAME = "my_notes.txt";

    public static void syncFileToDatabase() {
        try {
            if (!Files.exists(Paths.get(FILE_NAME))) return;
            List<String> lines = Files.readAllLines(Paths.get(FILE_NAME));

            try (Connection conn = DriverManager.getConnection(DB_URL)) {
                String sql = "INSERT OR IGNORE INTO tasks (todo) VALUES (?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    for (String line : lines) {
                        if (!line.isBlank()) {
                            pstmt.setString(1, line.trim());
                            pstmt.addBatch();
                        }
                    }
                    pstmt.executeBatch();
                }
            }
            System.out.println("sync file to db success");
        } catch (Exception e) {
            System.out.println("sync error " + e.getMessage());
        }
    }

    public static void syncDatabaseToFile() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT todo FROM tasks")) {

            List<String> data = new ArrayList<>();
            while (rs.next()) {
                data.add(rs.getString("todo"));
            }
            Files.write(Paths.get(FILE_NAME), data);
            System.out.println("sync db to file success lines " + data.size());
        } catch (Exception e) {
            System.out.println("sync error " + e.getMessage());
        }
    }
}