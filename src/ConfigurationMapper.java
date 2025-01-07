import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigurationMapper {
    private String mode;
    private String filepath;
    private long stopTime;
    private double coolingRate;

    public ConfigurationMapper(String url) throws IOException {
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
                    case "stopTime":
                        this.stopTime = Long.parseLong(value);
                        break;
                    case "coolingRate":
                        this.coolingRate = Double.parseDouble(value);
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

    public long getStopTime() {
        return stopTime;
    }

    public double getCoolingRate() {
        return coolingRate;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public void setStopTime(long stopTime) {
        this.stopTime = stopTime;
    }

    public void setCoolingRate(double coolingRate) {
        this.coolingRate = coolingRate;
    }
}
