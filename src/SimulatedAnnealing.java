import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class SimulatedAnnealing {
    private int[][] distanceMatrix;
    private double initialTemperature;
    private double coolingRate;
    private long stopTime;
    private int optimalCost;
    public Random random = new Random();

    public SimulatedAnnealing(int[][] distanceMatrix, double initialTemperature, double coolingRate, long stopTime, int optimalCost) {
        this.distanceMatrix = distanceMatrix;
        this.initialTemperature = initialTemperature;
        this.coolingRate = coolingRate;
        this.stopTime = stopTime;
        this.optimalCost = optimalCost;
    }

    public int[] run() {

        // Otwieramy plik CSV do zapisu wyników
        try (FileWriter fileWriter = new FileWriter("wyniki.csv");
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            // Zapis nagłówka: czas (w s), koszt, błąd procentowy
            printWriter.println("czas,koszt,blad_procentowy");

            long startTimeMillis = System.currentTimeMillis();
            long endTimeMillis = startTimeMillis + stopTime * 1000;

            long startNano = System.nanoTime();

            int[] currentSolution = generateGreedySolution();
            int[] bestSolution = currentSolution.clone();
            int bestCost = calculateCost(bestSolution);
            double temperature = initialTemperature;
            long lastImprovementNano = System.nanoTime();

            while (System.currentTimeMillis() < endTimeMillis) {

                int[] neighbor = generateNeighbor(currentSolution);
                int currentCost = calculateCost(currentSolution);
                int neighborCost = calculateCost(neighbor);
                int costDifference = neighborCost - currentCost;

                if (costDifference <= 0 || Math.random() < Math.exp(-costDifference / temperature)) {
                    currentSolution = neighbor;
                    currentCost = neighborCost;

                    if (currentCost < bestCost) {
                        bestSolution = currentSolution.clone();
                        bestCost = currentCost;

                        int bladProcentowy = 0;
                        if (optimalCost != 0) {
                            double bladDouble = ((double) bestCost - optimalCost) / optimalCost * 100.0;

                            bladProcentowy = (int) Math.round(bladDouble);
                        }

                        double timeSinceLastImprovement = (System.nanoTime() - lastImprovementNano) / 1_000_000_000.0;
                        lastImprovementNano = System.nanoTime();
                        printWriter.printf("%.6f,%d,%d%%%n",
                                timeSinceLastImprovement,
                                bestCost,
                                bladProcentowy
                        );
                    }
                }

                // Schładzamy temperaturę
                temperature *= coolingRate;
            }

            return bestSolution;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private int[] generateRandomSolution() {
        int n = distanceMatrix.length;
        // Tworzymy listę miast
        List<Integer> cities = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            cities.add(i);
        }

        // Tasujemy listę losowo
        Collections.shuffle(cities, random);

        // Kopiujemy miasta do tablicy wynikowej
        int[] path = new int[n];
        for (int i = 0; i < n; i++) {
            path[i] = cities.get(i);
        }

        return path;
    }


    private int[] generateGreedySolution() {
        int n = distanceMatrix.length;
        boolean[] visited = new boolean[n];
        int[] path = new int[n];

        int currentCity = random.nextInt(n);
        path[0] = currentCity;
        visited[currentCity] = true;

        for (int i = 1; i < n; i++) {
            int nextCity = -1;
            int shortestDistance = Integer.MAX_VALUE;

            for (int j = 0; j < n; j++) {
                if (!visited[j] && distanceMatrix[currentCity][j] < shortestDistance) {
                    nextCity = j;
                    shortestDistance = distanceMatrix[currentCity][j];
                }
            }

            currentCity = nextCity;
            path[i] = currentCity;
            visited[currentCity] = true;
        }

        return path;
    }


    private int[] generateNeighbor(int[] currentSolution) {
        int n = currentSolution.length;
        int[] neighbor = currentSolution.clone();

        int i = random.nextInt(n);
        int j = random.nextInt(n);

        // Zamiana dwóch losowych miast w ścieżce
        int temp = neighbor[i];
        neighbor[i] = neighbor[j];
        neighbor[j] = temp;

        return neighbor;
    }

    int calculateCost(int[] path) {
        int cost = 0;
        for (int i = 0; i < path.length - 1; i++) {
            cost += distanceMatrix[path[i]][path[i + 1]];
        }
        // Powrót do miasta startowego
        cost += distanceMatrix[path[path.length - 1]][path[0]];
        return cost;
    }
}
