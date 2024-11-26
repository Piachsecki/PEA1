import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // Wczytaj konfigurację
            ConfigurationMapper configuration = new ConfigurationMapper("./src/config.json");
            DataGenerator dataGenerator = new DataGenerator(configuration);
            // Utwórz logger do czasu wykonania
            String csvFile = "execution_times.csv";
            ExecutionTimeLogger logger = new ExecutionTimeLogger(csvFile);
            DFS_TSP dfsTsp = new DFS_TSP();
            BFS_TSP bfsTsp = new BFS_TSP();
            LOW_COST_TSP lowCostTsp = new LOW_COST_TSP();

            // W zależności od trybu uruchom odpowiedni algorytm
            if (configuration.getMode().equals("test")) {
                // Wczytaj macierz z pliku
                dataGenerator.readMatrixFromFile(configuration.getFilepath());
                bfsTsp.setDistanceMatrix(dataGenerator.getMatrix());
                bfsTsp.setStartingPoint((int) configuration.getStartingPoint());
                bfsTsp.setN(dataGenerator.getMatrix().length - 1);


                lowCostTsp.setDistanceMatrix(dataGenerator.getMatrix());
                lowCostTsp.setStartingPoint((int) configuration.getStartingPoint());
                lowCostTsp.setN(dataGenerator.getMatrix().length - 1);


                dfsTsp.setDistanceMatrix(dataGenerator.getMatrix());
                dfsTsp.setStartingPoint((int) configuration.getStartingPoint());
                dfsTsp.setN(dataGenerator.getMatrix().length - 1);


                for (int i = 0; i < configuration.getIterations(); i++) {
                    // BFS - pomiar czasu w mikrosekundach
                    long start = System.nanoTime();
                    int costBFS = bfsTsp.bfs(); // Zakładamy, że BFS_TSP ma metodę bfs()
                    long end = System.nanoTime();
                    long durationUsBFS = (end - start) / 1_000; // Konwersja na mikrosekundy
                    logger.logExecutionTime("bfs", bfsTsp.getN() + 1, durationUsBFS);

                    // DFS - pomiar czasu w mikrosekundach
                    start = System.nanoTime();
                    int costDFS = dfsTsp.dfs(); // Zakładamy, że DFS_TSP ma metodę dfs()
                    end = System.nanoTime();
                    long durationUsDFS = (end - start) / 1_000; // Konwersja na mikrosekundy
                    logger.logExecutionTime("dfs",dfsTsp.getN() + 1, durationUsDFS);


                    //lowcost
                    start = System.nanoTime();
                    int costLowCost = lowCostTsp.lc();
                    end = System.nanoTime();
                    long duriationUsLowCost = (end - start) / 1_000; // Konwersja na mikrosekundy
                    logger.logExecutionTime("lc", lowCostTsp.getN() + 1, duriationUsLowCost);


                }


            } else if (configuration.getMode().equals("simulation")) {

                for (int i = 0; i < configuration.getIterations(); i++) {

                    dataGenerator.generateMatrix(configuration.getMatrixSizes().getFirst().intValue(), configuration.getMatrixType());

                    bfsTsp.setDistanceMatrix(dataGenerator.getMatrix());
                    bfsTsp.setStartingPoint((int) configuration.getStartingPoint());
                    bfsTsp.setN(dataGenerator.getMatrix().length - 1);


                    lowCostTsp.setDistanceMatrix(dataGenerator.getMatrix());
                    lowCostTsp.setStartingPoint((int) configuration.getStartingPoint());
                    lowCostTsp.setN(dataGenerator.getMatrix().length - 1);


                    dfsTsp.setDistanceMatrix(dataGenerator.getMatrix());
                    dfsTsp.setStartingPoint((int) configuration.getStartingPoint());
                    dfsTsp.setN(dataGenerator.getMatrix().length - 1);


                    // BFS - pomiar czasu w mikrosekundach
                    long start = System.nanoTime();
                    int costBFS = bfsTsp.bfs(); // Zakładamy, że BFS_TSP ma metodę bfs()
                    long end = System.nanoTime();
                    long durationUsBFS = (end - start) / 1_000; // Konwersja na mikrosekundy
                    logger.logExecutionTime("bfs", bfsTsp.getN() + 1, durationUsBFS);

//                     DFS - pomiar czasu w mikrosekundach
                    start = System.nanoTime();
                    int costDFS = dfsTsp.dfs(); // Zakładamy, że DFS_TSP ma metodę dfs()
                    end = System.nanoTime();
                    long durationUsDFS = (end - start) / 1_000; // Konwersja na mikrosekundy
                    logger.logExecutionTime("dfs", dfsTsp.getN() + 1, durationUsDFS);


//                    lowcost
                    start = System.nanoTime();
                    int costLowCost = lowCostTsp.lc(); // Zakładamy, że DFS_TSP ma metodę dfs()
                    end = System.nanoTime();
                    long duriationUsLowCost = (end - start) / 1_000; // Konwersja na mikrosekundy
                    logger.logExecutionTime("lc", lowCostTsp.getN() + 1, duriationUsLowCost);

                }

            } else {
                throw new RuntimeException("Wrong mode");
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }


}
