import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import resources.Microservice;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class ArchParser {

    Microservice[] microservices;

    public Microservice[] readArchitectureModel(String filename) {
        Gson gson = new Gson();

        try {
            JsonObject root = gson.fromJson(new JsonReader(new FileReader(filename)), JsonObject.class);
            microservices = gson.fromJson(root.get("microservices"), Microservice[].class);
        } catch(FileNotFoundException ex) {
            System.out.println("File " + filename + " not found");
            System.exit(1);
        }

        return this.microservices;
    }

}
