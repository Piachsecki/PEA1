import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class Config {
    private String mode;
    private String filepath;
    private long iterations;
    private String matrixType;
    private long startingPoint;
    private long randomIterationsValue;
    private boolean showProgress;
    private List<Integer> matrixSizes;

    public Config(String url) throws IOException, ParseException {

        Object obj = new JSONParser().parse(new FileReader(url));

        JSONObject jo = (JSONObject) obj;
        this.mode = (String) jo.get("mode");


        if ("test".equals(mode)) {
            Map test = (Map) jo.get("test");
            this.filepath = (String) test.get("filepath");
            this.iterations = (Long) test.get("iterations"); // iterations jako Integer
            this.startingPoint = (Long) test.get("startingPoint"); // startingPoint jako Integer
            this.randomIterationsValue = (Long) test.get("randomIterationsValue"); // randomIterationsValue jako Integer
            this.matrixType = (String) test.get("matrixType"); // matrixType jako String
            this.showProgress = (Boolean) test.get("showProgress");

        } else if ("simulation".equals(mode)) {
            Map simulation = (Map) jo.get("simulation");

            this.matrixSizes = (List<Integer>) simulation.get("matrixSizes"); // iterations jako Integer
            this.iterations = (Long) simulation.get("iterations"); // iterations jako Integer
            this.startingPoint = (Long) simulation.get("startingPoint"); // startingPoint jako Integer
            this.randomIterationsValue = (Long) simulation.get("randomIterationsValue"); // randomIterationsValue jako Integer
            this.matrixType = (String) simulation.get("matrixType"); // matrixType jako String
            this.showProgress = (Boolean) simulation.get("showProgress");
        } else {
            throw new RuntimeException("Wrong mode specified in json config file");
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

    public List<Integer> getMatrixSizes() {
        return matrixSizes;
    }

    public void setMatrixSizes(List<Integer> matrixSizes) {
        this.matrixSizes = matrixSizes;
    }
}
