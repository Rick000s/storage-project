package cz.upce.fei;

// import for sql database
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// import for file
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.APPEND;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

//        simpleDataBase();
//        readFromSimpleDataBase();
//        writeToTextFile();
//        readFromTextFile();

    }

    public static void simpleDataBase() {
        System.out.println("----------WRITING DATABASE-----------");
        // database

        String url = "jdbc:sqlite:my_database.db"; // створення файлу та збереження його адресату

        try (Connection conn = DriverManager.getConnection(url)) { // тут підключення зєднання до (url)
            if (conn != null) {
                System.out.println("Підключено до бази!\n");

                java.sql.Statement statement = conn.createStatement(); // створення курьєра
                statement.execute("CREATE TABLE IF NOT EXISTS tasks(todo TEXT)"); // sql запит на створення таблиці з колонками
                System.out.println("Таблиця створена!\n");

                statement.execute("DELETE FROM tasks"); // очистка перед додаванням нових

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

                    for (int i = 0; i < lines.size(); i++) {
                        System.out.println(lines.get(i));
                    }
            } else {
                System.out.println("Помилка файлу: ");
            }
            System.out.println("Готово! Зчитано з файлу.");
        } catch (Exception e) {
            System.out.println("Помилка файлу: " + e.getMessage());
        }
    }
}