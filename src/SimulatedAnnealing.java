import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class SimulatedAnnealing {
    private int[][] distanceMatrix;
    private double initialTemperature;
    private double coolingRate;
    private long stopTime;
    public Random random = new Random();

    public SimulatedAnnealing(int[][] distanceMatrix, double initialTemperature, double coolingRate, long stopTime) {
        this.distanceMatrix = distanceMatrix;
        this.initialTemperature = initialTemperature;
        this.coolingRate = coolingRate;
        this.stopTime = stopTime;
    }

    public int[] run() {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + stopTime * 1000;

        int[] currentSolution = generateGreedySolution();
        int[] bestSolution = currentSolution.clone();
        int bestCost = calculateCost(bestSolution);
        double temperature = initialTemperature;

        while (System.currentTimeMillis() < endTime) {
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
                }
            }

            // Schładzanie
            temperature *= coolingRate;

            // Debugowanie
            System.out.println("Current cost: " + currentCost);
            System.out.println("Best cost: " + bestCost);
            System.out.println("Temperature: " + temperature);
        }

        return bestSolution;
    }

    private int[] generateGreedySolution() {
        int n = distanceMatrix.length;
        int[] path = new int[n];
        boolean[] visited = new boolean[n];
        path[0] = 0;
        visited[0] = true;

        for (int i = 1; i < n; i++) {
            int lastCity = path[i - 1];
            int nextCity = -1;
            int shortestDistance = Integer.MAX_VALUE;

            for (int j = 0; j < n; j++) {
                if (!visited[j] && distanceMatrix[lastCity][j] < shortestDistance) {
                    shortestDistance = distanceMatrix[lastCity][j];
                    nextCity = j;
                }
            }

            path[i] = nextCity;
            visited[nextCity] = true;
        }

        return path;
    }

    private int[] generateNeighbor(int[] currentSolution) {
        int n = currentSolution.length;
        int[] neighbor = currentSolution.clone();

        int i = random.nextInt(n);
        int j = random.nextInt(n);

        // Zamiana dwóch losowych miast
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
        cost += distanceMatrix[path[path.length - 1]][path[0]]; // Powrót do startu
        return cost;
    }
}
