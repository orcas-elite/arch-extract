package de.rss.arch_extract.input_output;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.rss.arch_extract.resources.arch_model.Architecture;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class OutputParser {

    private Gson gson;

    public OutputParser() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void writeArchitectureModel(Architecture architecture) {
        try {
            String json = gson.toJson(architecture);
            Files.write(Paths.get("./architecture_model.json"), json.getBytes());
        } catch (IOException e) {
            System.out.println("Couldn't write architecture model file.");
        }
    }
}
