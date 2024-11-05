import java.util.*;
import java.io.*;

public class DFS_TSP {
    private int[][] distanceMatrix;
    private int n; // liczba miast
    private int startingPoint;

    public DFS_TSP(int[][] matrix, int startingPoint) {
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

    public DFS_TSP() {
    }

    public int dfs() {
        int minCost = Integer.MAX_VALUE;

        // Stos przechowuje stan (ścieżkę i aktualny koszt)
        Stack<State> stack = new Stack<>();
        stack.push(new State(new ArrayList<>(Collections.singletonList(startingPoint)), 0));

        while (!stack.isEmpty()) {
            State current = stack.pop();
            List<Integer> path = current.path;
            int currentCost = current.cost;

            if (path.size() == n) {
                int returnCost = distanceMatrix[path.get(path.size() - 1)][startingPoint];
                if (returnCost != -1) {
                    minCost = Math.min(minCost, currentCost + returnCost);
                }
            } else {
                int lastCity = path.get(path.size() - 1);

                for (int i = 0; i < n; i++) {
                    if (!path.contains(i) && distanceMatrix[lastCity][i] != -1) {
                        List<Integer> newPath = new ArrayList<>(path);
                        newPath.add(i);
                        stack.push(new State(newPath, currentCost + distanceMatrix[lastCity][i]));
                    }
                }
            }
        }

        return minCost;
    }

    // Klasa pomocnicza do przechowywania stanu (ścieżka i koszt)
    private static class State {
        List<Integer> path;
        int cost;

        State(List<Integer> path, int cost) {
            this.path = path;
            this.cost = cost;
        }
    }
}