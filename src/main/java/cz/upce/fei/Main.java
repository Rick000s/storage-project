// for JAVA 15+
package cz.upce.fei;

// import for sql database
import java.sql.*;

// import for file
import java.io.IOException;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.APPEND;
import java.nio.file.Files;
import java.nio.file.Paths;
//import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("----MENU----");
        System.out.println("Write 1)Insert to Database \n" + "Write 2)Read from Database \n" + "Write 3)Insert to Document \n" + "Write 4)Read from Document \n" + "Write 5)Syns Document to DataBase \n" + "Write 6)Syns DataBase to Document \n" + "Write 7)Clear Document \n" + "Write 8)Clear DataBase \n" + "Write 0)Exit");
        char choice = sc.next().charAt(0);
        switch (choice) {
            case '0':
                System.out.println("Вихід...");
                System.exit(0);
                break;
            case '1':
                simpleDataBase();
                break;
            case '2':
                readFromSimpleDataBase();
                break;
            case '3':
                 writeToTextFile();
                 break;
            case '4':
                 readFromTextFile();
                 break;
            case '5':
                synsFileToDatabase();
                break;
            case '6':
                syncDatabaseToFile();
                break;
            case '7':
                clearFile();
                break;
            case '8':
                clearDatabase();
                break;
        }
    }

    public static void simpleDataBase() {
        System.out.println("----------WRITING DATABASE-----------");
        // database

        String url = "jdbc:sqlite:my_database.db"; // створення файлу та збереження його адресату

        try (Connection conn = DriverManager.getConnection(url)) { // тут підключення зєднання до (url)
            if (conn != null) {
                System.out.println("Підключено до бази!\n");

                java.sql.Statement statement = conn.createStatement(); // створення курьєра

                // sql запит на створення таблиці з колонками
                statement.execute(""" 
                    CREATE TABLE IF NOT EXISTS tasks(todo TEXT UNIQUE)
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

    public static void writeToTextFile() {
        System.out.println("------------WRITING DATA-------------");
        // data

        Scanner scanner = new Scanner(System.in);
        String fileName = "my_notes.txt"; // створення файлу з даними
        System.out.println("Файл створено!\n");

        System.out.println("Введи справу для запису в файл: ");
        String userText = scanner.nextLine();

        try {
//            java.nio.file.Files.write(     // довгий спосіб але детальний
//                    java.nio.file.Paths.get(fileName), // бере шлях зрозумілий для OS
//                    (userText + System.lineSeparator()).getBytes(), // саме наповнення тут повідомлення + перестрибування, ну і перетворення на біти
//                    java.nio.file.StandardOpenOption.CREATE, // створює документ якщо нема хоча є зверху створення але перестраховка
//                    java.nio.file.StandardOpenOption.APPEND // що б не перезаписувало і закидувало в кінець FIFO
//            );

            // найкращий варіант хоч і заплутаний все вибрано на зовні
//            byte[] bytes = (userText + System.lineSeparator()).getBytes(); // створення байтів це для багаторазового користування
//            Files.write(Paths.get(fileName), bytes, CREATE, APPEND);
            Files.write(Paths.get(fileName),
                    (userText + System.lineSeparator()
                    ).getBytes(), CREATE, APPEND);

            System.out.println("Готово! Записано у файл.");
        } catch (Exception e) {
            System.out.println("Помилка файлу: " + e.getMessage());
        }
        }

    public static void readFromTextFile() {
        System.out.println("----------READING FILE-----------");
        String fileName = "my_notes.txt";

        try {
            if (Files.exists(Paths.get(fileName))) { // провірка чи існує файл
                    List<String> lines = Files.readAllLines(Paths.get(fileName)); // лист котрий заповнюється з документа

                for (String line : lines) {
                    System.out.println(line);
                }
            } else {
                System.out.println("Помилка файлу: ");
            }
            System.out.println("Готово! Зчитано з файлу.");
        } catch (Exception e) {
            System.out.println("Помилка файлу: " + e.getMessage());
        }
    }

    public static void synsFileToDatabase() {
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

    public static void clearFile() {
        String fileName = "my_notes.txt";
        try {
            // Ми просто записуємо порожній список рядків
            Files.write(Paths.get(fileName), new ArrayList<String>());
            System.out.println("--- Файл успішно очищено! ---");
        } catch (IOException e) {
            System.out.println("Помилка при очищенні файлу: " + e.getMessage());
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
}