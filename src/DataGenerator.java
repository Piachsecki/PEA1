import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class DataGenerator {
    private ConfigurationMapper configMapper;
    private Random random;
    private int[][] matrix;

    public DataGenerator(ConfigurationMapper configMapper) {
        this.configMapper = configMapper;
        this.random = new Random();
    }

    // Metoda generująca macierz i przypisująca ją do pola matrix
    public void generateMatrix(int size, String matrixType) {
        matrix = new int[size][size];
        boolean isSymmetric = "symetric".equals(matrixType);



        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    matrix[i][j] = -1; // Odległość do siebie samego
                } else {
                    int distance = random.nextInt(100) + 1; // Generowanie losowej odległości w zakresie 1-100
                    if (isSymmetric) {
                        matrix[i][j] = distance;
                        matrix[j][i] = distance; // Ustawienie odległości symetrycznej
                    } else {
                        matrix[i][j] = distance;
                        matrix[j][i] = random.nextInt(100) + 1; // Różna odległość dla niesymetrycznej macierzy
                    }
                }
            }
        }
    }

    // Metoda wczytująca macierz z pliku i przypisująca ją do pola matrix
    public void readMatrixFromFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            // Wczytaj rozmiar macierzy z pierwszej linii
            int size = Integer.parseInt(reader.readLine().trim());

            // Zainicjalizuj macierz o podanym rozmiarze
            matrix = new int[size][size];

            // Wczytaj kolejne wiersze macierzy
            for (int i = 0; i < size; i++) {
                String[] values = reader.readLine().trim().split("\\s+");

                for (int j = 0; j < size; j++) {
                    int intValue = Integer.parseInt(values[j]);

                    // Zamień -1 na Integer.MAX_VALUE, by oznaczyć "nieskończoność"
                    if (intValue == -1) {
                        intValue = Integer.MAX_VALUE;
                    }

                    // Ustawienie wartości w macierzy
                    matrix[i][j] = intValue;
                }
            }
        }
    }

    // Getter dla macierzy
    public int[][] getMatrix() {
        return matrix;
    }
}
