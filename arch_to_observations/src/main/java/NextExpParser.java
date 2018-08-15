import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class NextExpParser {

    private int dependencyCount = -1;
    private boolean hasPattern = false;

    public NextExpParser(String filename) {
        try {
            List<String> content = Files.readAllLines(Paths.get("./" + filename));

            for (String line : content) {
                int indexDepColon = line.indexOf(":");
                int indexSemicolon = line.indexOf(";");
                int indexPatColon = line.lastIndexOf(":");
                int indexQuoteMark = line.lastIndexOf("'");

                if (indexDepColon > 0 && indexSemicolon > 0) {
                    String depCount = line.substring(indexDepColon + 1, indexSemicolon);
                    dependencyCount = Integer.parseInt(depCount);
                } else {
                    throw new IOException();
                }

                if (indexPatColon > 0 && indexQuoteMark > 0) {
                    String pattern = line.substring(indexPatColon + 1, indexQuoteMark);
                    hasPattern = pattern.equals("0") ? false : true;
                } else {
                    throw new IOException();
                }

            }
        } catch (IOException e) {
            System.out.println("Couldn't read nextExperiment file");
        }
    }

    public int getDependencyCount() {
        return dependencyCount;
    }

    public void setDependencyCount(int dependencyCount) {
        this.dependencyCount = dependencyCount;
    }

    public boolean isHasPattern() {
        return hasPattern;
    }

    public void setHasPattern(boolean hasPattern) {
        this.hasPattern = hasPattern;
    }
}
