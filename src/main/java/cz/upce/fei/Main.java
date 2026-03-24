package cz.upce.fei;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("menu: 1 insert db, 2 read db, 3 write file, 4 read file, 5 sync to db, 6 sync to file, 7 clear file, 8 clear db, j json, 0 exit");

        char choice = sc.next().charAt(0);
        switch (choice) {
            case '0' -> System.exit(0);
            case '1' -> DatabaseManager.simpleDataBase();
            case '2' -> DatabaseManager.readFromSimpleDataBase();
            case '3' -> FileManager.writeToTextFile();
            case '4' -> FileManager.readFromTextFile();
            case '5' -> SyncManager.syncFileToDatabase();
            case '6' -> SyncManager.syncDatabaseToFile();
            case '7' -> FileManager.clearFile();
            case '8' -> DatabaseManager.clearDatabase();
            case 'j' -> {
                List<Task> tasks = DatabaseManager.getAllTasks();
                JsonManager.saveTasksToJson(tasks);
            }
        }
    }
}