import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataParser {

    private int[][] distanceMatrix;
    private int dimension;

    public void displayMatrix() {
        if (distanceMatrix == null) {
            System.out.println("Matrix is not initialized.");
            return;
        }

        for (int[] row : distanceMatrix) {
            for (int value : row) {
                // Jeśli wartość to Integer.MAX_VALUE, wyświetl "INF" dla nieskończoności
                if (value == Integer.MAX_VALUE) {
                    System.out.print("INF ");
                } else {
                    System.out.print(value + " ");
                }
            }
            System.out.println(); // Przejdź do nowej linii po każdym wierszu
        }
    }


    public void parseFile(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean parsingMatrix = false;
            int matrixRow = 0;
            int matrixColumn = 0;

            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("DIMENSION")) {
                    String[] parts = line.split(":");
                    dimension = Integer.parseInt(parts[1].trim());
                    distanceMatrix = new int[dimension][dimension];
                } else if (line.startsWith("EDGE_WEIGHT_SECTION")) {
                    parsingMatrix = true;
                } else if (parsingMatrix) {
                    if (line.equals("EOF")) {
                        break;
                    }

                    String[] values = line.split("\\s+");
                    for (String valueStr : values) {
                        if (matrixColumn == dimension) {
                            matrixColumn = 0;
                            matrixRow++;
                        }
                        if (matrixRow == dimension) {
                            break;
                        }
                        int value = Integer.parseInt(valueStr);
                        distanceMatrix[matrixRow][matrixColumn] = value == 100000000 ? Integer.MAX_VALUE : value;
                        matrixColumn++;
                    }
                }
            }
        }
    }


    public int[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    public int getDimension() {
        return dimension;
    }

    public static void main(String[] args) {
        try {
            DataParser parser = new DataParser();
            parser.parseFile("./src/resources/ftv47.atsp");

            int[][] matrix = parser.getDistanceMatrix();
            System.out.println("Parsed matrix with dimension: " + parser.getDimension());
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
