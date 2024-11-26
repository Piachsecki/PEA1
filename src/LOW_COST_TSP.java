import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class LOW_COST_TSP {
    private int[][] distanceMatrix; // Macierz odległości
    private int n; // Liczba miast
    private int startingPoint; // Punkt startowy

    public LOW_COST_TSP(int[][] matrix, int startingPoint) {
        this.distanceMatrix = matrix;
        this.n = matrix.length;
        this.startingPoint = startingPoint;
    }

    public int lc() {
        int minCost = Integer.MAX_VALUE;

        // Kolejka priorytetowa przechowuje stan (sciezke, koszt i odwiedzone miasta)
        PriorityQueue<State> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.cost, b.cost));
        pq.offer(new State(new ArrayList<>(Collections.singletonList(startingPoint)), 0, new boolean[n]));

        while (!pq.isEmpty()) {
            State current = pq.poll();
            List<Integer> path = current.path;
            int currentCost = current.cost;
            boolean[] visited = current.visited;

            if (path.size() == n) {
                int returnCost = distanceMatrix[path.get(path.size() - 1)][startingPoint];
                if (returnCost != -1) {
                    minCost = Math.min(minCost, currentCost + returnCost);
                }
            } else {
                int bound = calculateBound(distanceMatrix, visited);
                if (bound + currentCost >= minCost) {
                    continue;
                }

                int lastCity = path.get(path.size() - 1);

                for (int i = 0; i < n; i++) {
                    if (!visited[i] && distanceMatrix[lastCity][i] != -1) {
                        List<Integer> newPath = new ArrayList<>(path);
                        newPath.add(i);
                        boolean[] newVisited = visited.clone();
                        newVisited[i] = true;

                        pq.offer(new State(newPath, currentCost + distanceMatrix[lastCity][i], newVisited));
                    }
                }
            }
        }

        return minCost;
    }

    private int calculateBound(int[][] matrix, boolean[] visited) {
        int bound = 0;

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                int minIn = Integer.MAX_VALUE, minOut = Integer.MAX_VALUE;

                for (int j = 0; j < n; j++) {
                    if (i != j) {
                        if (matrix[j][i] != -1) {
                            minIn = Math.min(minIn, matrix[j][i]);
                        }
                        if (matrix[i][j] != -1) {
                            minOut = Math.min(minOut, matrix[i][j]);
                        }
                    }
                }

                if (minIn != Integer.MAX_VALUE && minOut != Integer.MAX_VALUE) {
                    bound += (minIn + minOut) / 2;
                }
            }
        }

        return bound;
    }

    public LOW_COST_TSP() {
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

    // Klasa pomocnicza do przechowywania stanu (ścieżka, koszt, odwiedzone miasta)
    private static class State {
        List<Integer> path;
        int cost;
        boolean[] visited;

        State(List<Integer> path, int cost, boolean[] visited) {
            this.path = path;
            this.cost = cost;
            this.visited = visited;
        }
    }
}
