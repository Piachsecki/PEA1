import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // Wczytaj konfigurację
            ConfigurationMapper configuration = new ConfigurationMapper("C:\\Users\\krpia\\Desktop\\PEA1\\src\\config.json");

            // Inicjalizacja ProblemSolvera
            AlgorithmsSolver solver = new AlgorithmsSolver();
            solver.setConfigClass(configuration);

            // Utwórz logger do czasu wykonania
            String csvFile = "execution_times.csv";
            ExecutionTimeLogger logger = new ExecutionTimeLogger(csvFile);

            // W zależności od trybu uruchom odpowiedni algorytm
            if (configuration.getMode().equals("test")) {
                // Wczytaj macierz z pliku
                Matrix matrix = new Matrix();
                matrix.readMatrixFromFile(configuration.getFilepath());
                solver.setMatrix(matrix);

                for (int i = 0; i < configuration.getIterations(); i++) {
                    // Mierzenie czasu dla wszystkich algorytmów
//                    measureAndLog("BruteForce",solver, i, logger);
                    measureAndLog("NearestNeighbor",solver, i, logger);
                    measureAndLog("Random",solver, i, logger);
                }

            } else if (configuration.getMode().equals("simulation")) {
                DataGenerator dataGenerator = new DataGenerator(configuration);
                List<Matrix> matricesToIterateThrough = new ArrayList<>();
                for (Long matrixSize : configuration.getMatrixSizes()) {
                    matricesToIterateThrough.add(dataGenerator.generateMatrix(matrixSize.intValue()));
                }

                for (Matrix matrix : matricesToIterateThrough) {
                    solver.setMatrix(matrix);
                    for (int i = 0; i < configuration.getIterations(); i++) {
                        // Mierzenie czasu dla wszystkich algorytmów
                        measureAndLog("BruteForce",solver, i, logger);
                        measureAndLog("NearestNeighbor",solver, i, logger);
                        measureAndLog("Random",solver, i, logger);
                    }
                }
            } else {
                throw new RuntimeException("Wrong mode");
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static void measureAndLog(String algorithmName, AlgorithmsSolver solver, int iteration, ExecutionTimeLogger logger) {
        long startTime, endTime, executionTime;
        int result = 0;

        // Mierzenie czasu dla konkretnego algorytmu
        switch (algorithmName) {
            case "BruteForce":
                startTime = System.nanoTime();
                result = solver.solveBruteForce();
                endTime = System.nanoTime();
                break;
            case "NearestNeighbor":
                startTime = System.nanoTime();
                result = solver.solveNearestNeighbor();
                endTime = System.nanoTime();
                break;
            case "Random":
                startTime = System.nanoTime();
                result = solver.solveRandom();
                endTime = System.nanoTime();
                break;
            default:
                throw new IllegalArgumentException("Unknown algorithm: " + algorithmName);
        }

        executionTime = endTime - startTime;

        // Logowanie wyniku do pliku CSV
        logger.logExecutionTime(algorithmName, iteration, executionTime);

        // Możesz też odkomentować poniższe, aby wyświetlać wyniki w konsoli
//        System.out.println("Best path for " + algorithmName + ": " + result);
//        System.out.println(algorithmName + " Time: " + executionTime + " nanoseconds");
    }
}
