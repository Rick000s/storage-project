// for JAVA 17+
package cz.upce.fei;

import java.util.Scanner;

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
        }
    }

}