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
            double initialTemperature = computeInitialTemperature(distanceMatrix, 0.8, 20);
//            double initialTemperature = 250;
            double coolingRate = configuration.getCoolingRate(); // Pobranie współczynnika zmiany temperatury z konfiguracji
            long stopTime = configuration.getStopTime(); // Kryterium stopu (w sekundach)

            // Uruchomienie algorytmu Symulowanego Wyżarzania
            SimulatedAnnealing sa = new SimulatedAnnealing(distanceMatrix, initialTemperature, coolingRate, stopTime);
            int[] bestSolution = sa.run();
            int bestCost = sa.calculateCost(bestSolution);

            // Wyświetlenie wyników
            System.out.println("Najlepsze znalezione rozwiązanie:");
            System.out.println("Koszt: " + bestCost);
            System.out.println("Ścieżka: " + Arrays.toString(bestSolution));

            // Logowanie czasu wykonania (opcjonalne)
            ExecutionTimeLogger logger = new ExecutionTimeLogger("execution_times.csv");
            logger.logExecutionTime("SimulatedAnnealing", distanceMatrix.length, stopTime);
        } catch (IOException e) {
            System.err.println("Błąd podczas wczytywania pliku: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("inny " + e.getMessage());
        }
    }

    public static double computeInitialTemperature(int[][] costMatrix, double acceptanceProbability, int sampleSize) {
        Random rand = new Random();
        int n = costMatrix.length;

        double sumDelta = 0.0;

        // Wykonujemy "sampleSize" prób, aby uśrednić ΔE
        for (int i = 0; i < sampleSize; i++) {
            // (permutacja miast)
            int[] route = generateRandomRoute(n, rand);
            double cost1 = routeCost(route, costMatrix);

            // 2. Stwórz sąsiada (np. przez zamianę dwóch miast w trasie)
            int[] neighbor = route.clone();
            swapTwoCities(neighbor, rand);
            double cost2 = routeCost(neighbor, costMatrix);

            // 3. Zapisz różnicę kosztów
            sumDelta += Math.abs(cost2 - cost1);
        }

        //  ΔE
        double avgDelta = sumDelta / sampleSize;

        //  =>  T0 = -ΔE / ln(p0)
        double T0 = -avgDelta / Math.log(acceptanceProbability);
        return T0;
    }

    public static double routeCost(int[] route, int[][] costMatrix) {
        double totalCost = 0.0;
        for (int i = 0; i < route.length - 1; i++) {
            totalCost += costMatrix[route[i]][route[i + 1]];
        }
        // Powrót do miasta startowego
        totalCost += costMatrix[route[route.length - 1]][route[0]];
        return totalCost;
    }


    public static int[] generateRandomRoute(int n, Random rand) {
        int[] route = new int[n];
        // Inicjalizacja tablicy sekwencją [0, 1, 2, ..., n-1]
        for (int i = 0; i < n; i++) {
            route[i] = i;
        }
        // Prosty algorytm Fisher-Yates do tasowania
        for (int i = n - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            // Zamiana
            int temp = route[i];
            route[i] = route[j];
            route[j] = temp;
        }
        return route;
    }


    public static void swapTwoCities(int[] route, Random rand) {
        int n = route.length;
        int i = rand.nextInt(n);
        int j = rand.nextInt(n);
        // Zamieniamy w tablicy miejscami
        int temp = route[i];
        route[i] = route[j];
        route[j] = temp;
    }
}

