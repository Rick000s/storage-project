package cz.upce.fei;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String menu = "menu: 1 insert db, 2 read db, 3 write file, 4 read file, 5 sync to db, 6 sync to file, 7 clear file, 8 clear db, j json, 0 exit";

        while (true) {
            System.out.println(menu);
            char choice = sc.next().charAt(0);
        switch (choice) {
            case '0':
                System.out.println("Вихід...");
                System.exit(0);
                break;
            case '1':
                DatabaseManager.simpleDataBase();
                break;
            case '2':
                DatabaseManager.readFromSimpleDataBase();
                break;
            case '3':
                FileManager.writeToTextFile();
                break;
            case '4':
                FileManager.readFromTextFile();
                break;
            case '5':
                SyncManager.syncFileToDatabase();
                break;
            case '6':
                SyncManager.syncDatabaseToFile();
                break;
            case '7':
                FileManager.clearFile();
                break;
            case '8':
                DatabaseManager.clearDatabase();
                break;
            case 'j':
                List<Task> currentTasks = DatabaseManager.getAllTasks(); // Дістали з бази
                JsonManager.saveTasksToJson(currentTasks);               // Зберегли в JSON
                break;
                default:
                    System.out.println("ERROR");
        }
        }
    }
}