package cz.upce.fei;

import java.nio.file.*;
import java.util.List;
import java.util.Scanner;
import static java.nio.file.StandardOpenOption.*;

public class FileManager {
    private static final String FILE_NAME = "my_notes.txt";

    public static void writeToTextFile() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter task text");
        String text = scanner.nextLine() + System.lineSeparator();
        try {
            Files.write(Paths.get(FILE_NAME), text.getBytes(), CREATE, APPEND);
            System.out.println("file write success");
        } catch (Exception e) {
            System.out.println("file error " + e.getMessage());
        }
    }

    public static void readFromTextFile() {
        try {
            if (!Files.exists(Paths.get(FILE_NAME))) return;
            List<String> lines = Files.readAllLines(Paths.get(FILE_NAME));
            lines.forEach(System.out::println);
            System.out.println("file read success");
        } catch (Exception e) {
            System.out.println("file error " + e.getMessage());
        }
    }

    public static void clearFile() {
        try {
            Files.write(Paths.get(FILE_NAME), new byte[0], TRUNCATE_EXISTING);
            System.out.println("file cleared");
        } catch (Exception e) {
            System.out.println("file error " + e.getMessage());
        }
    }
}