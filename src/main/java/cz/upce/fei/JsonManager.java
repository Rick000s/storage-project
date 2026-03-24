package cz.upce.fei;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JsonManager {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void saveTasksToJson(List<Task> tasks) {
        try (FileWriter writer = new FileWriter("tasks.json")) {
            gson.toJson(tasks, writer);
            System.out.println("🚀 JSON файл створено успішно!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}