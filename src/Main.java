import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // Wczytaj konfigurację
            ConfigurationMapper configuration = new ConfigurationMapper("./src/config.json");
            DataGenerator dataGenerator = new DataGenerator(configuration);
            // Utwórz logger do czasu wykonania
            String csvFile = "execution_times.csv";
            ExecutionTimeLogger logger = new ExecutionTimeLogger(csvFile);
            DFS_TSP dfsTsp = new DFS_TSP();
            BFS_TSP bfsTsp = new BFS_TSP();

            // W zależności od trybu uruchom odpowiedni algorytm
            if (configuration.getMode().equals("test")) {
                // Wczytaj macierz z pliku
                dataGenerator.readMatrixFromFile(configuration.getFilepath());
                bfsTsp.setDistanceMatrix(dataGenerator.getMatrix());
                bfsTsp.setStartingPoint((int)configuration.getStartingPoint());
                bfsTsp.setN(dataGenerator.getMatrix().length - 1);


                dfsTsp.setDistanceMatrix(dataGenerator.getMatrix());
                dfsTsp.setStartingPoint((int)configuration.getStartingPoint());
                dfsTsp.setN(dataGenerator.getMatrix().length - 1);


                for (int i = 0; i < configuration.getIterations(); i++) {
                    System.out.println(bfsTsp.bfs());
                    System.out.println(dfsTsp.dfs());
                }

            } else if (configuration.getMode().equals("simulation")) {

            } else {
                throw new RuntimeException("Wrong mode");
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

//    public static void measureAndLog(String algorithmName, AlgorithmsSolver solver, int iteration, ExecutionTimeLogger logger) {
//        long startTime, endTime, executionTime;
//        int result = 0;
//
//        // Mierzenie czasu dla konkretnego algorytmu
//        switch (algorithmName) {
//            case "BruteForce":
//                startTime = System.nanoTime();
//
//                endTime = System.nanoTime();
//                break;
//            case "NearestNeighbor":
//                startTime = System.nanoTime();
//                endTime = System.nanoTime();
//                break;
//            case "Random":
//                startTime = System.nanoTime();
//                endTime = System.nanoTime();
//                break;
//            default:
//                throw new IllegalArgumentException("Unknown algorithm: " + algorithmName);
//        }
//
//        executionTime = endTime - startTime;
//
//        // Logowanie wyniku do pliku CSV
//        logger.logExecutionTime(algorithmName, iteration, executionTime);
//
////        System.out.println("Best path for " + algorithmName + ": " + result);
////        System.out.println(algorithmName + " Time: " + executionTime + " nanoseconds");
//    }
}
