package cz.upce.fei;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SyncManager {

    public static void syncFileToDatabase() {
        System.out.println("----------SYNS FILE TO DATABASE-----------");
        String fileName = "my_notes.txt";
        String url = "jdbc:sqlite:my_database.db";

        try {
            if (!Files.exists(Paths.get(fileName))) {
                System.out.println("Файл не знайдено, нічого синхронізувати.");
                return;
            }
            List<String> lines = Files.readAllLines(Paths.get(fileName));

            try (Connection conn = DriverManager.getConnection(url)) {
                String sql = "INSERT OR IGNORE INTO tasks (todo) VALUES (?)";  // шаблон для забиття знаком для заміни
                var pstmt = conn.prepareStatement(sql);

                for (String line : lines) {
                    if (!line.trim().isEmpty()) { // не додавати порожні рядки
                        pstmt.setString(1, line);  // міняє перший у лінії на тепершній обєкт
                        pstmt.addBatch(); // збираємо в пачку для швидкості
                    }
                }
                pstmt.executeBatch(); // "Пуш в датабазу запакованого"
                System.out.println("Синхронізація успішна!");
            }
        } catch (Exception e) {
            System.out.println("Помилка при синхронізації: " + e.getMessage());
        }
    }

    public static void syncDatabaseToFile() {
        System.out.println("----------SYNCING DATABASE TO FILE-----------");
        String fileName = "my_notes.txt";
        String url = "jdbc:sqlite:my_database.db";
        String sql = "SELECT todo FROM tasks";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) { // створення обєкта котрий стоїть перед і чекає запиту

            List<String> tasksFromDb = new ArrayList<>();

            while (rs.next()) {
                tasksFromDb.add(rs.getString("todo")); //перебор спочатку воно на 0 потім на 1
            }

            Files.write(Paths.get(fileName), tasksFromDb);

            System.out.println("Синхронізація завершена! У файл записано " + tasksFromDb.size() + " рядків.");
        } catch (Exception e) {
            System.out.println("Помилка при експорті з бази: " + e.getMessage());
        }
    }
}
