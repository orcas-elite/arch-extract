package csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CsvUtility {

    private static final String CSV_File = "./observations.csv";
    private CSVPrinter csvPrinter;

    public CsvUtility() {
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(CSV_File));

            csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("id", "d", "pa", "h"));
        } catch (IOException e) {
            System.out.println("Exception while writing observations.csv");
        }
    }

    public void writeOperations(List<List<String>> data) {
        try {
            for (List<String> operation : data) {
                csvPrinter.printRecord(operation);
            }
            csvPrinter.flush();
        } catch (IOException e) {
            System.out.println("Exception while writing observations.csv");
        }
    }
}
