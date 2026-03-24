package cz.upce.fei;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class JsonManager {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void saveTasksToJson(List<Task> tasks) {
        try {
            String json = gson.toJson(tasks);
            Files.writeString(Paths.get("tasks.json"), json);
            System.out.println("json file created");
        } catch (Exception e) {
            System.out.println("json error " + e.getMessage());
        }
    }
}