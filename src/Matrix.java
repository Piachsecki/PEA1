import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Matrix {
    private List<List<Integer>> matrix;

    // Konstruktor z rozmiarem
    public Matrix(int size) {
        this.matrix = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                row.add(Integer.MAX_VALUE); // Ustawienie wartości nieskończoności na początku
            }
            matrix.add(row);
        }
    }

    public Matrix() {
    }

    public void setDistance(int i, int j, int distance) {
        matrix.get(i).set(j, distance);
    }

    // Konstruktor wczytuje macierz z pliku, gdzie pierwsza linia to rozmiar macierzy
    public void readMatrixFromFile(String filename) throws IOException {
        matrix = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;

        // Pierwsza linia zawiera rozmiar macierzy
        line = reader.readLine();
        int size = Integer.parseInt(line.trim());

        // Zainicjuj macierz o podanym rozmiarze
        for (int i = 0; i < size; i++) {
            List<Integer> row = new ArrayList<>();
            matrix.add(row);
        }

        // Odczytuj linia po linii kolejne wiersze macierzy
        int rowIndex = 0;
        while ((line = reader.readLine()) != null) {
            // Podziel linię na wartości liczbowe po spacji lub innych białych znakach
            String[] values = line.trim().split("\\s+");

            // Przetwarzanie każdej wartości
            for (int colIndex = 0; colIndex < values.length; colIndex++) {
                int intValue = Integer.parseInt(values[colIndex]);

                // Zamień -1 na dużą wartość (np. nieskończoność)
                if (intValue == -1) {
                    intValue = Integer.MAX_VALUE; // Wartość "nieskończoności" dla problemu komiwojażera
                }

                // Dodaj przetworzoną wartość do odpowiedniego miejsca w macierzy
                matrix.get(rowIndex).add(intValue);
            }
            rowIndex++;
        }

        reader.close();
    }

    // Zwraca rozmiar macierzy
    public int getSize() {
        return matrix.size();
    }

    // Zwraca dystans między dwoma miastami
    public int getDistance(int from, int to) {
        return matrix.get(from).get(to);
    }
}
