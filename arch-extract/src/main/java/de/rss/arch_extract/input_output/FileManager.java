package de.rss.arch_extract.input_output;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import de.rss.arch_extract.resources.trace.Trace;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileManager implements InputManager {

    public List<String> getServices() {
        String filename = "./example/services.json";
        try {
            JsonReader reader = new JsonReader(new FileReader(filename));
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            InputParser parser = new InputParser();
            return parser.getServiceNames(jsonObject.toString());
        } catch (FileNotFoundException e) {
            System.out.println("Didn't find file " + filename);
            return null;
        }
    }

    public List<String> getOperations(String service) {
        String filename = "./example/" + service + "_operations.json";
        try {
            JsonReader reader = new JsonReader(new FileReader(filename));
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            InputParser parser = new InputParser();
            return parser.getOperationNames(jsonObject.toString(), service);
        } catch (FileNotFoundException e) {
            System.out.println("Didn't find file " + filename);
            return null;
        }
    }

    public List<Trace> getTraces(String serviceName) {
        String filename = "./example/" + serviceName + "_traces.json";
        try {
            JsonReader reader = new JsonReader(new FileReader(filename));
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            InputParser parser = new InputParser();
            return parser.getTraces(jsonObject.toString(), serviceName);
        } catch (FileNotFoundException e) {
            System.out.println("Didn't find file " + filename);
            return null;
        }
    }

    public void writeServiceFile(String json) {
        // Check if directory exists
        String dirPath = "./example";
        Path dir = Paths.get(dirPath);
        if (!Files.exists(dir)) {
            try {
                Files.createDirectory(dir);
            } catch (IOException e) {
                System.out.println("Couldn't create example directory");
            }

        }

        // Write string to file
        String path = "./example/services.json";
        try {
            Files.write(Paths.get(path), json.getBytes());
        } catch (IOException e) {
            System.out.println("Couldn't create backup of services");
        }
    }

    public void writeOperationsFile(String service, String json) {
        String path = "./example/" + service + "_operations.json";
        try {
            Files.write(Paths.get(path), json.getBytes());
        } catch (IOException e) {
            System.out.println("Couldn't create a backup of the operations of service " + service);
        }
    }

    public void writeTraceFile(String service, String json) {
        String path = "./example/" + service + "_traces.json";
        try {
            Files.write(Paths.get(path), json.getBytes());
        } catch (IOException e) {
            System.out.println("Couldn't create a backup of the traces of service " + service);
        }
    }


}
