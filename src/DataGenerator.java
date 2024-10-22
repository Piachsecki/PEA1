import java.util.Random;

public class DataGenerator {
    private ConfigurationMapper configMapper;
    private Random random;

    public DataGenerator(ConfigurationMapper configMapper) {
        this.configMapper = configMapper;
        this.random = new Random();
    }

    public Matrix generateMatrix(int size) {
        Matrix matrix = new Matrix(size); // Inicjalizowanie nowej macierzy
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    matrix.setDistance(i, j, -1); // Ustaw odległość do siebie samego na -1
                } else {
                    int distance = random.nextInt(100) + 1; // Generowanie losowej odległości w zakresie 1-100
                    if ("symetric".equals(configMapper.getMatrixType())) {
                        matrix.setDistance(i, j, distance);
                        matrix.setDistance(j, i, distance); // Ustawienie odległości symetrycznej
                    } else {
                        matrix.setDistance(i, j, distance);
                        matrix.setDistance(j, i, random.nextInt(100) + 1); // Ustawienie różnej odległości dla niesymetrycznej
                    }
                }
            }
        }
        return matrix;
    }
}
