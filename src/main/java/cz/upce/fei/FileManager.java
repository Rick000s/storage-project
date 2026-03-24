package cz.upce.fei;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class FileManager {

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
}
