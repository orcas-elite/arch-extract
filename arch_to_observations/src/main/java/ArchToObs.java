import csv.CsvUtility;
import resources.Dependency;
import resources.Microservice;
import resources.Operation;

import java.util.ArrayList;
import java.util.List;

public class ArchToObs {

    private static String archFile = null;
    private static String nextExpFile = null;
    private static Microservice[] microservices;

    private static void findDependencies(Operation operation) {
        for (Microservice service: microservices) {
            for (Operation curOperation: service.getOperations()) {
                for (Dependency dependency: curOperation.getDependencies()) {
                    if (dependency.getOperation().equals(operation.getName())) {
                        operation.increaseDependingOperationCount();
                    }
                }
            }
        }
    }

    private static void readArgs(String[] args) {
        if (args.length > 0) {
            if (args[0] != null && !args[0].equals(" ")) {
                archFile = args[0];
            }
            if (args.length > 1) {
                if (args[1] != null && !args[1].equals(" ")) {
                    nextExpFile = args[1];
                }
            }
        }
    }

    public static void main(String[] args) {

        readArgs(args);

        ArchParser archParser = new ArchParser();

        if (archFile == null) {
            System.out.println("Program arguments didn't include a filename");
            System.exit(1);
        }

        System.out.println("Reading architecture model ...");
        microservices = archParser.readArchitectureModel(archFile);

        if (nextExpFile == null) {
            // No nextExperiment file -> Transform the architecture model to a observations file
            System.out.println("Transforming model ...");

            List<List<String>> data = new ArrayList<List<String>>();
            int count = 0;

            // Find depending operations for each operation
            for (Microservice service : microservices) {
                for (Operation operation : service.getOperations()) {
                    findDependencies(operation);

                    List<String> opData = new ArrayList<String>();
                    opData.add(String.valueOf(count));
                    opData.add(String.valueOf(operation.getDependingOperationCount()));

                    String pa = (operation.getCircuitBreaker() == null) ? "0" : "1";

                    opData.add(pa);
                    opData.add("0");

                    data.add(opData);

                    count++;
                }
            }

            System.out.print("Writing observations file ...");

            CsvUtility csvUtility = new CsvUtility();
            csvUtility.writeOperations(data);
        } else {
            // nextExperiment file -> Search architectural model for nextExperiment operations
            NextExpParser parser = new NextExpParser(nextExpFile);
            int depCount = parser.getDependencyCount();
            boolean hasPattern = parser.isHasPattern();

            List<String> operationNames = new ArrayList<String>();

            for (Microservice service : microservices) {
                for (Operation operation : service.getOperations()) {
                    findDependencies(operation);

                    if (operation.getDependingOperationCount() == depCount && operation.hasCircuitBreaker() == hasPattern) {
                        operationNames.add("Service " + service.getName() + " Operation " + operation.getName());
                    }
                }
            }

            operationNames.forEach((operation) -> {
                System.out.println(operation);
            });
        }
    }

}
