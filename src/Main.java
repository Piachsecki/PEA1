import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        try {
            // Wczytanie konfiguracji z pliku
            ConfigurationMapper configuration = new ConfigurationMapper("./src/config.txt");

            // Wczytanie macierzy odległości z pliku ATSP za pomocą DataParser
            DataParser parser = new DataParser();
            parser.parseFile(configuration.getFilepath());
            int[][] distanceMatrix = parser.getDistanceMatrix();
            parser.displayMatrix();

            // Inicjalizacja parametrów algorytmu Symulowanego Wyżarzania
            double initialTemperature = computeInitialTemperature(distanceMatrix);
//            double initialTemperature = 1000;
            double coolingRate = configuration.getCoolingRate();
            long stopTime = configuration.getStopTime();
            int optimalCost = configuration.getOptimalCost();

            SimulatedAnnealing sa = new SimulatedAnnealing(distanceMatrix, initialTemperature, coolingRate, stopTime, optimalCost);
            int[] bestSolution = sa.run();
            int bestCost = sa.calculateCost(bestSolution);

            System.out.println("Najlepsze znalezione rozwiązanie:");
            System.out.println("Koszt: " + bestCost);
            System.out.println("Ścieżka: " + Arrays.toString(bestSolution));


        } catch (IOException e) {
            System.err.println("Błąd podczas wczytywania pliku: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("inny " + e.getMessage());
        }
    }


    private static double computeInitialTemperature(int[][] distanceMatrix) {
        int maxDifference = 0;
        for (int i = 0; i < distanceMatrix.length; i++) {
            for (int j = 0; j < distanceMatrix.length; j++) {
                if (i != j && distanceMatrix[i][j] != Integer.MAX_VALUE) {
                    maxDifference = Math.max(maxDifference, distanceMatrix[i][j]);
                }
            }
        }
        return maxDifference * 10;
    }


}

