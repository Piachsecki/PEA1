import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlgorithmsSolver {
    private Matrix matrix;
    private ConfigurationMapper configurationMapperClass;  // Zmienna przechowująca konfigurację

    // Konstruktor przyjmujący macierz i obiekt konfiguracji
    public AlgorithmsSolver(Matrix matrix, ConfigurationMapper configurationMapperClass) {
        this.matrix = matrix;
        this.configurationMapperClass = configurationMapperClass;
    }

    // Getter i setter dla obiektu konfiguracji
    public ConfigurationMapper getConfigClass() {
        return configurationMapperClass;
    }

    public AlgorithmsSolver() {
    }

    public void setConfigClass(ConfigurationMapper configurationMapperClass) {
        this.configurationMapperClass = configurationMapperClass;
    }

    //     Przegląd zupełny - brute force
    public int solveBruteForce() {
        List<Integer> cities = generateInitialPath();
        int bestCost = Integer.MAX_VALUE;
        boolean showProgress = configurationMapperClass.isShowProgress();

        for (List<Integer> perm : generatePermutations(cities)) {
            int currentCost = calculatePathCost(perm);
            if (currentCost < bestCost) {
                bestCost = currentCost;
            }

            if (showProgress) {
                System.out.println("Current path: " + perm + " | Cost: " + currentCost);
            }
        }

        return bestCost;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    // Algorytm najbliższego sąsiada
    public int solveNearestNeighbor() {
        int startingPoint = (int) configurationMapperClass.getStartingPoint();
        boolean showProgress = configurationMapperClass.isShowProgress();
        List<Integer> path = new ArrayList<>();
        List<Integer> unvisited = generateInitialPath();
        path.add(startingPoint);
        unvisited.remove(Integer.valueOf(startingPoint));

        while (!unvisited.isEmpty()) {
            int lastCity = path.getLast();
            int nearestCity = findNearestCity(lastCity, unvisited);
            path.add(nearestCity);
            unvisited.remove(Integer.valueOf(nearestCity));

            if (showProgress) {
                System.out.println("Added city: " + nearestCity + " | Current path: " + path);
            }
        }

        return calculatePathCost(path);
    }

    // Algorytm losowy
    public int solveRandom() {
        int bestCost = Integer.MAX_VALUE;
        int iterations = (int) configurationMapperClass.getRandomIterationsValue();
        boolean showProgress = configurationMapperClass.isShowProgress();

        for (int i = 0; i < iterations; i++) {
            List<Integer> path = generateInitialPath();
            Collections.shuffle(path);

            int currentCost = calculatePathCost(path);
            if (currentCost < bestCost) {
                bestCost = currentCost;
            }

            if (showProgress) {
                System.out.println("Iteration: " + (i + 1) + " | Current path: " + path + " | Cost: " + currentCost);
            }
        }

        return bestCost;
    }

    // Metoda do generowania inicjalnej ścieżki
    private List<Integer> generateInitialPath() {
        List<Integer> cities = new ArrayList<>();
        for (int i = 0; i < matrix.getSize(); i++) {
            cities.add(i);
        }
        return cities;
    }

    // Generowanie permutacji
    private List<List<Integer>> generatePermutations(List<Integer> cities) {
        List<List<Integer>> permutations = new ArrayList<>();
        permute(cities, 0, permutations);
        return permutations;
    }

    private void permute(List<Integer> cities, int start, List<List<Integer>> result) {
        if (start == cities.size() - 1) {
            result.add(new ArrayList<>(cities));
        } else {
            for (int i = start; i < cities.size(); i++) {
                Collections.swap(cities, start, i);
                permute(cities, start + 1, result);
                Collections.swap(cities, start, i);
            }
        }
    }

    // Obliczanie kosztu ścieżki
    private int calculatePathCost(List<Integer> path) {
        int cost = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            cost += matrix.getDistance(path.get(i), path.get(i + 1));
        }
        cost += matrix.getDistance(path.get(path.size() - 1), path.get(0)); // Powrót do startu
        return cost;
    }

    // Znalezienie najbliższego sąsiada
    private int findNearestCity(int currentCity, List<Integer> unvisited) {
        int nearestCity = -1;
        int shortestDistance = Integer.MAX_VALUE;

        for (int city : unvisited) {
            int distance = matrix.getDistance(currentCity, city);
            if (distance < shortestDistance) {
                shortestDistance = distance;
                nearestCity = city;
            }
        }

        return nearestCity;
    }
}
