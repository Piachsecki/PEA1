import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ExecutionTimeLogger {
    private String csvFile;

    public ExecutionTimeLogger(String csvFile) {
        this.csvFile = csvFile;
    }

    public void logExecutionTime(String algorithm, int n, long executionTime) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFile, true))) {
            // Zapisz nagłówki kolumn, jeśli plik jest pusty
//            writer.println("Algorithm,Iteration,ExecutionTime(nanoseconds)");
            writer.println(algorithm + "," + n + "," + executionTime );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
