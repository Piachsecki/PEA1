import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigurationMapper {
    private String mode;
    private String filepath;
    private long iterations;
    private String matrixType;
    private long startingPoint;
    private long randomIterationsValue;
    private boolean showProgress;
    private List<Long> matrixSizes;

    public ConfigurationMapper(String url) throws IOException {
        this.matrixSizes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(url))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":", 2); // Rozdziel klucz i wartość
                if (parts.length != 2) continue;
                String key = parts[0].trim();
                String value = parts[1].trim();

                switch (key) {
                    case "mode":
                        this.mode = value;
                        break;
                    case "filepath":
                        this.filepath = value;
                        break;
                    case "iterations":
                        this.iterations = Long.parseLong(value);
                        break;
                    case "startingPoint":
                        this.startingPoint = Long.parseLong(value);
                        break;
                    case "randomIterationsValue":
                        this.randomIterationsValue = Long.parseLong(value);
                        break;
                    case "showProgress":
                        this.showProgress = Boolean.parseBoolean(value);
                        break;
                    case "matrixType":
                        this.matrixType = value;
                        break;
                    case "matrixSizes":
                        for (String size : value.split(",")) {
                            this.matrixSizes.add(Long.parseLong(size.trim()));
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("Nieznany klucz w konfiguracji: " + key);
                }
            }
        }
    }

    public String getMode() {
        return mode;
    }

    public String getFilepath() {
        return filepath;
    }

    public long getIterations() {
        return iterations;
    }

    public String getMatrixType() {
        return matrixType;
    }

    public long getStartingPoint() {
        return startingPoint;
    }

    public long getRandomIterationsValue() {
        return randomIterationsValue;
    }

    public boolean isShowProgress() {
        return showProgress;
    }

    public List<Long> getMatrixSizes() {
        return matrixSizes;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public void setIterations(long iterations) {
        this.iterations = iterations;
    }

    public void setMatrixType(String matrixType) {
        this.matrixType = matrixType;
    }

    public void setStartingPoint(long startingPoint) {
        this.startingPoint = startingPoint;
    }

    public void setRandomIterationsValue(long randomIterationsValue) {
        this.randomIterationsValue = randomIterationsValue;
    }

    public void setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
    }

    public void setMatrixSizes(List<Long> matrixSizes) {
        this.matrixSizes = matrixSizes;
    }
}
