import java.util.*;

public class BFS_TSP {
    private int[][] distanceMatrix;
    private int n; // liczba miast
    private int startingPoint;

    public BFS_TSP(int[][] matrix, int startingPoint) {
        this.distanceMatrix = matrix;
        this.n = matrix.length;
        this.startingPoint = startingPoint;
    }

    public int[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    public void setDistanceMatrix(int[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(int startingPoint) {
        this.startingPoint = startingPoint;
    }

    public BFS_TSP() {
    }

    public int bfs() {
        Queue<List<Integer>> queue = new LinkedList<>();
        queue.add(Collections.singletonList(startingPoint));

        int shortestPath = Integer.MAX_VALUE;

        while (!queue.isEmpty()) {
            List<Integer> path = queue.poll();

            if (path.size() == n) { // Sprawdzamy, czy odwiedziliśmy wszystkie miasta
                int pathCost = calculatePathCost(path);
                if (pathCost < shortestPath) {
                    shortestPath = pathCost;
                }
            } else {
                for (int i = 0; i < n; i++) {
                    if (!path.contains(i)) {
                        List<Integer> newPath = new ArrayList<>(path);
                        newPath.add(i);
                        queue.add(newPath);
                    }
                }
            }
        }

        return shortestPath;
    }

    private int calculatePathCost(List<Integer> path) {
        int cost = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            int from = path.get(i);
            int to = path.get(i + 1);
            int distance = distanceMatrix[from][to];
            if (distance == -1) return Integer.MAX_VALUE; // nie można przejść
            cost += distance;
        }
        int lastDistance = distanceMatrix[path.get(path.size() - 1)][path.get(0)];
        return (lastDistance == -1) ? Integer.MAX_VALUE : cost + lastDistance;
    }
}